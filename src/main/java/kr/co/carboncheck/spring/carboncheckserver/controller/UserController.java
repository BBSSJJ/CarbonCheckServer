package kr.co.carboncheck.spring.carboncheckserver.controller;

import kr.co.carboncheck.spring.carboncheckserver.domain.User;
import kr.co.carboncheck.spring.carboncheckserver.dto.user.JoinRequest;
import kr.co.carboncheck.spring.carboncheckserver.dto.user.JoinResponse;
import kr.co.carboncheck.spring.carboncheckserver.dto.user.LoginRequest;
import kr.co.carboncheck.spring.carboncheckserver.dto.user.LoginResponse;
import kr.co.carboncheck.spring.carboncheckserver.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static java.sql.Types.NULL;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
}
