package kr.co.carboncheck.spring.carboncheckserver.controller;

import kr.co.carboncheck.spring.carboncheckserver.domain.User;
import kr.co.carboncheck.spring.carboncheckserver.dto.usage.GetUsageResponse;
import kr.co.carboncheck.spring.carboncheckserver.dto.user.*;
import kr.co.carboncheck.spring.carboncheckserver.service.user.UserService;
import kr.co.carboncheck.spring.carboncheckserver.sse.SseGroup;
import kr.co.carboncheck.spring.carboncheckserver.sse.SseGroupManager;
import kr.co.carboncheck.spring.carboncheckserver.sse.Subscriber;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.sql.Types.NULL;

@Controller
public class UserController {

    private UserService userService;
    private SseGroupManager sseGroupManager;

    @Autowired
    public UserController(UserService userService, SseGroupManager sseGroupManager) {
        this.userService = userService;
        this.sseGroupManager = sseGroupManager;
    }

    @PostMapping("/join")
    public ResponseEntity<JoinResponse> join(@RequestBody JoinRequest joinRequest) {
        User user = new User();
        user.setUserId(NULL);
//        user.setHomeServerId(String.valueOf(NULL));
        user.setEmail(joinRequest.getEmail());
        String hashedPassword = new BCryptPasswordEncoder().encode(joinRequest.getPassword());
        user.setPassword(hashedPassword);
        user.setName(joinRequest.getName());
        user.setAuthType(joinRequest.getAuthType());
        user.setTargetAmount(1000); //default

        return ResponseEntity.ok().body(userService.createUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        //        String hashedPassword = new BCryptPasswordEncoder().encode(loginRequestDTO.getPassword());
        // 비밀번호는 Controller에서 암호화 후 Service로 넘기는게 좋지만,
        // BcryptPasswordEncoder의 matches()함수는 raw password와 hashed password를 비교해야 해서
        // 암호화 하지 않고 넘겼다. 개선할 수 있으면 해보자
        return ResponseEntity.ok().body(userService.authenticateUser(email, password));
    }

    @GetMapping("/get/user_data")
    public ResponseEntity<GetUserDataResponse> getUserData(@RequestParam("email") String email) {
        System.out.println("int get Uset Data");
        Optional<User> userOptional = userService.getUserByEmail(email);
        GetUserDataResponse getUserDataResponse = new GetUserDataResponse("", "", "");

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            getUserDataResponse.setUserId(String.valueOf(user.getUserId()));
            if (user.getHomeServerId() != null) {
                getUserDataResponse.setHomeServerId(user.getHomeServerId());
            }
            getUserDataResponse.setName(user.getName());
        }
        return ResponseEntity.ok().body(getUserDataResponse);
    }


    @PostMapping("/face/register")
    public ResponseEntity<RegisterFaceResponse> registerFace(@RequestBody RegisterFaceRequest registerFaceRequest) {
        String homeServerId = registerFaceRequest.getHomeServerId();
        String userId = registerFaceRequest.getUserId();
        SseGroup sseGroup = sseGroupManager.findGroup(homeServerId);
        if (sseGroup != null) {
            Subscriber subscriber = sseGroup.findSubscriber(homeServerId);
            if (subscriber != null) {
                try {
                    SseEmitter emitter = subscriber.getEmitter();
                    // ADD USER와 user ID보내줘야함
                    emitter.send(SseEmitter.event().data(String.format("{\"msg\": \"%s\", \"id\": \"%s\", \"success\": %b }", "add", userId, true)));
                    return ResponseEntity.ok().body(new RegisterFaceResponse(true, "얼굴 등록 요청 완료"));
                } catch (IOException e) {
                    return ResponseEntity.ok().body(new RegisterFaceResponse(false, e.getMessage()));
                }
            }
        }
        return ResponseEntity.ok().body(new RegisterFaceResponse(false, "SSE 그룹에 홈서버가 없습니다."));
    }

    @PostMapping("/training_done")
    public ResponseEntity<Boolean> registerFaceFinish(@RequestBody RegisterFaceFinish registerFaceFinish) {
        String userId = registerFaceFinish.getUserId();
        boolean result = registerFaceFinish.getResult();
        String homeServerId = registerFaceFinish.getHomeServerId();

        SseGroup sseGroup = sseGroupManager.findGroup(homeServerId);
        System.out.println("in registerFaceFinish");

        if (sseGroup != null) {
            Subscriber subscriber = sseGroup.findSubscriber(userId);
            if (subscriber != null) {
                try {
                    SseEmitter emitter = subscriber.getEmitter();
                    if (result) {
                        System.out.println("사용자에게 얼굴 등록 끝남 메세지 보냄");
                        emitter.send(SseEmitter.event().data("face register finish"));
                    }
                    return ResponseEntity.ok().body(true);
                } catch (IOException e) {
                    return ResponseEntity.ok().body(false);
                }
            }
            Subscriber home_subscriber = sseGroup.findSubscriber(homeServerId);
            try {
                System.out.println("홈서버에게 얼굴 등록 끝남 메세지 보냄");
                SseEmitter emitter = home_subscriber.getEmitter();
                emitter.send(SseEmitter.event().data(String.format("{\"msg\": \"%s\", \"id\": %s, \"success\": %b }", "done", null, true)));

                return ResponseEntity.ok().body(true);
            } catch (IOException e) {
                return ResponseEntity.ok().body(false);
            }

        }
        return ResponseEntity.ok().body(false);
    }

    @GetMapping("/target_amount/group")
    public ResponseEntity<List<GetGroupTargetAmountResponse>> getGroupTargetAmount(@RequestParam("homeServerId") String homeServerId) {
        return ResponseEntity.ok().body(userService.getGroupTargetAmount(homeServerId));
    }
}
