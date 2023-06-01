package kr.co.carboncheck.spring.carboncheckserver.controller;

import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;
import kr.co.carboncheck.spring.carboncheckserver.domain.WaterUsage;
import kr.co.carboncheck.spring.carboncheckserver.dto.usage.GetUsageResponse;
import kr.co.carboncheck.spring.carboncheckserver.dto.usage.InsertElectricityUsageRequest;
import kr.co.carboncheck.spring.carboncheckserver.dto.usage.InsertElectricityUsageResponse;
import kr.co.carboncheck.spring.carboncheckserver.dto.usage.InsertWaterUsageResponse;
import kr.co.carboncheck.spring.carboncheckserver.service.usage.UsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

import static java.sql.Types.NULL;

@Controller
public class UsageController {
    private final UsageService<WaterUsage> waterUsageService;
    private final UsageService<ElectricityUsage> electricityUsageService;

    @Autowired
    public UsageController(UsageService<WaterUsage> waterUsageService, UsageService<ElectricityUsage> electricityUsageService) {
        this.waterUsageService = waterUsageService;
        this.electricityUsageService = electricityUsageService;
    }

    @PostMapping("/waterusage/insert")
    public ResponseEntity<InsertWaterUsageResponse> insertWaterUsage(@RequestBody List<WaterUsage> waterUsageList) {
        for (WaterUsage waterUsage : waterUsageList) {
            if (waterUsage.getUserId() == NULL) continue;
            if (!waterUsageService.insertUsage(waterUsage))
                return ResponseEntity.ok().body(new InsertWaterUsageResponse(false));
        }
        return ResponseEntity.ok().body(new InsertWaterUsageResponse(true));

    }

    @PostMapping("/electricityusage/insert")
    public ResponseEntity<InsertElectricityUsageResponse> insertElectricityUsage(@RequestBody InsertElectricityUsageRequest insertElectricityUsageRequest) {
        ElectricityUsage electricityUsage = new ElectricityUsage();
        electricityUsage.setPlugId(insertElectricityUsageRequest.getPlugId());
        electricityUsage.setAmount(insertElectricityUsageRequest.getAmount());
        electricityUsage.setDate(LocalDateTime.now());
        return ResponseEntity.ok().body(new InsertElectricityUsageResponse(electricityUsageService.insertUsage(electricityUsage)));
    }

    @GetMapping("/waterusage/user")
    public ResponseEntity<GetUsageResponse> getUserWaterUsage(@RequestParam("userId") String userId) {
        return ResponseEntity.ok().body(waterUsageService.getTodayUserUsage(userId));
    }

    @GetMapping("/waterusage/group")
    public ResponseEntity<GetUsageResponse> getGroupWaterUsage(@RequestParam("homeServerId") String homeServerId) {
        return ResponseEntity.ok().body(waterUsageService.getTodayGroupUsage(homeServerId));
    }

    @GetMapping("/electricityusage/user")
    public ResponseEntity<GetUsageResponse> getUserElectricityUsage(@RequestParam("userId") String userId) {
        return ResponseEntity.ok().body(waterUsageService.getTodayUserUsage(userId));
    }

    @GetMapping("/electricityusage/group")
    public ResponseEntity<GetUsageResponse> getGroupElectricityUsage(@RequestParam("homeServerId") String homeServerId) {
        return ResponseEntity.ok().body(waterUsageService.getTodayGroupUsage(homeServerId));
    }


}
