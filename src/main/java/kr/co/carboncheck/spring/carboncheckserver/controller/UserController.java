package kr.co.carboncheck.spring.carboncheckserver.controller;

import kr.co.carboncheck.spring.carboncheckserver.domain.User;
import kr.co.carboncheck.spring.carboncheckserver.dto.*;
import kr.co.carboncheck.spring.carboncheckserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static java.sql.Types.NULL;

@Controller
@Component
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public ResponseEntity<JoinResponseDTO> create(@RequestBody JoinRequestDTO joinRequestDTO) {
        User user = new User();
        user.setUserId(NULL);
        user.setGroupId(NULL);
        user.setEmail(joinRequestDTO.getEmail());
        String hashedPassword = new BCryptPasswordEncoder().encode(joinRequestDTO.getPassword());
        user.setPassword(hashedPassword);
        user.setName(joinRequestDTO.getName());
        user.setAuthType(joinRequestDTO.getAuthType());

        return ResponseEntity.ok().body(userService.join(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        String email = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();
        //        String hashedPassword = new BCryptPasswordEncoder().encode(loginRequestDTO.getPassword());
        // 비밀번호는 Controller에서 암호화 후 Service로 넘기는게 좋지만,
        // BcryptPasswordEncoder의 matches()함수는 raw password와 hashed password를 비교해야 해서
        // 암호화 하지 않고 넘겼다. 개선할 수 있으면 해보자
        return ResponseEntity.ok().body(userService.login(email, password));
    }
}
