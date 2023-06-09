package kr.co.carboncheck.spring.carboncheckserver.controller;

import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;
import kr.co.carboncheck.spring.carboncheckserver.domain.User;
import kr.co.carboncheck.spring.carboncheckserver.domain.WaterUsage;
import kr.co.carboncheck.spring.carboncheckserver.dto.usage.GetUsageResponse;
import kr.co.carboncheck.spring.carboncheckserver.dto.usage.InsertElectricityUsageRequest;
import kr.co.carboncheck.spring.carboncheckserver.dto.usage.InsertElectricityUsageResponse;
import kr.co.carboncheck.spring.carboncheckserver.dto.usage.InsertWaterUsageResponse;
import kr.co.carboncheck.spring.carboncheckserver.dto.user.RegisterFaceResponse;
import kr.co.carboncheck.spring.carboncheckserver.service.device.PlugService;
import kr.co.carboncheck.spring.carboncheckserver.service.usage.UsageService;
import kr.co.carboncheck.spring.carboncheckserver.service.user.UserService;
import kr.co.carboncheck.spring.carboncheckserver.sse.SseGroup;
import kr.co.carboncheck.spring.carboncheckserver.sse.SseGroupManager;
import kr.co.carboncheck.spring.carboncheckserver.sse.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.sql.Types.NULL;

@Controller
public class UsageController {
    private final UsageService<WaterUsage> waterUsageService;
    private final UsageService<ElectricityUsage> electricityUsageService;
    private final SseGroupManager sseGroupManager;
    private final UserService userService;
    private final PlugService plugService;

    @Autowired
    public UsageController(UsageService<WaterUsage> waterUsageService, UsageService<ElectricityUsage> electricityUsageService, SseGroupManager sseGroupManager, UserService userService, PlugService plugService) {
        this.waterUsageService = waterUsageService;
        this.electricityUsageService = electricityUsageService;
        this.sseGroupManager = sseGroupManager;
        this.userService = userService;
        this.plugService = plugService;

    }

    @PostMapping("/waterusage/insert")
    public ResponseEntity<InsertWaterUsageResponse> insertWaterUsage(@RequestBody List<WaterUsage> waterUsageList) {
        for (WaterUsage waterUsage : waterUsageList) {
            if (waterUsage.getUserId() == NULL) continue;
            if (!waterUsageService.insertUsage(waterUsage).isPresent())
                return ResponseEntity.ok().body(new InsertWaterUsageResponse(false));
        }
        //클라이언트에게 보내야 함

        String userId = String.valueOf(waterUsageList.get(0).getUserId());
        Optional<User> userOptional = userService.getUserById(Integer.parseInt(userId));
        if (!userOptional.isPresent()) return ResponseEntity.ok().body(new InsertWaterUsageResponse(false));
        String homeServerId = userOptional.get().getHomeServerId();

        //업데이트된 사용량 가져온다
        List<GetUsageResponse> insertedUsage = waterUsageService.getTodayUserUsage(userId);

        //SSE group에서 해당 userId를 찾아 보내준다
        SseGroup sseGroup = sseGroupManager.findGroup(homeServerId);
        if (sseGroup != null) {
            Subscriber subscriber = sseGroup.findSubscriber(userId);
            if (subscriber != null) {
                try {
                    SseEmitter emitter = subscriber.getEmitter();
                    for (GetUsageResponse waterUsage : insertedUsage) {
                        emitter.send(SseEmitter.event().id("update_usage").name("water_usage").data(String.format("{\"place\": \"%s\", \"amount\": \"%f\"}", waterUsage.getStr(), waterUsage.getAmount())));
                    }
                    return ResponseEntity.ok().body(new InsertWaterUsageResponse(true));
                } catch (IOException e) {
                    return ResponseEntity.ok().body(new InsertWaterUsageResponse(false));
                }
            }
        }

        return ResponseEntity.ok().body(new InsertWaterUsageResponse(false));

    }

    @PostMapping("/electricityusage/insert")
    public ResponseEntity<InsertElectricityUsageResponse> insertElectricityUsage(@RequestBody InsertElectricityUsageRequest insertElectricityUsageRequest) {
        ElectricityUsage electricityUsage = new ElectricityUsage();
        electricityUsage.setPlugId(insertElectricityUsageRequest.getPlugId());
        electricityUsage.setCumulativeAmount(insertElectricityUsageRequest.getCumulativeAmount());
        electricityUsage.setDate(LocalDateTime.now());
        Optional<ElectricityUsage> insertedUsageOptional = electricityUsageService.insertUsage(electricityUsage);
        //업데이트 될 때마다 클라이언트에게 보내는 과정

        //삽입 실패했을 때
        if (!insertedUsageOptional.isPresent()) {
            return ResponseEntity.ok().body(new InsertElectricityUsageResponse(false));
        }
        ElectricityUsage insertedUsage = insertedUsageOptional.get();
        //삽입 성공했을 때
        Optional<User> userOptional = plugService.findUserByPlugId(electricityUsage.getPlugId());
        if (!userOptional.isPresent()) {
            return ResponseEntity.ok().body(new InsertElectricityUsageResponse(false));
        }
        User user = userOptional.get();

        String homeServerId = user.getHomeServerId();
        String userId = String.valueOf(user.getUserId());
        float amount = insertedUsage.getAmount();

        //SSE에서 해당 userID 찾아서 사용량 보내준다
        SseGroup sseGroup = sseGroupManager.findGroup(homeServerId);
        if (sseGroup != null) {
            Subscriber subscriber = sseGroup.findSubscriber(userId);
            if (subscriber != null) {
                try {
                    SseEmitter emitter = subscriber.getEmitter();
                    emitter.send(SseEmitter.event().id("update_usage").name("electricity_usage").data(String.format("{\"plug_id\": \"%s\", \"amount\": \"%f\"}", electricityUsage.getPlugId(), amount)));
                    return ResponseEntity.ok().body(new InsertElectricityUsageResponse(true));
                } catch (IOException e) {
                    System.out.println("in exception");

                    return ResponseEntity.ok().body(new InsertElectricityUsageResponse(false));
                }
            }
        }
        System.out.println("in finish");

        return ResponseEntity.ok().body(new InsertElectricityUsageResponse(false));
    }

    @GetMapping("/waterusage/user")
    public ResponseEntity<List<GetUsageResponse>> getUserWaterUsage(@RequestParam("userId") String userId) {
        return ResponseEntity.ok().body(waterUsageService.getTodayUserUsage(userId));
    }

    @GetMapping("/waterusage/group")
    public ResponseEntity<List<GetUsageResponse>> getGroupWaterUsage(@RequestParam("homeServerId") String homeServerId) {
        return ResponseEntity.ok().body(waterUsageService.getTodayGroupUsage(homeServerId));
    }

    @GetMapping("/electricityusage/user")
    public ResponseEntity<List<GetUsageResponse>> getUserElectricityUsage(@RequestParam("userId") String userId) {
        System.out.println("유저 전기사용량 요청");
        System.out.println("userId = " + userId);
        return ResponseEntity.ok().body(electricityUsageService.getTodayUserUsage(userId));
    }

    @GetMapping("/electricityusage/group")
    public ResponseEntity<List<GetUsageResponse>> getGroupElectricityUsage(@RequestParam("homeServerId") String homeServerId) {
        return ResponseEntity.ok().body(electricityUsageService.getTodayGroupUsage(homeServerId));
    }

}
