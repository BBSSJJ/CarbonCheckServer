package kr.co.carboncheck.spring.carboncheckserver.controller;

import kr.co.carboncheck.spring.carboncheckserver.domain.User;
import kr.co.carboncheck.spring.carboncheckserver.dto.*;
import kr.co.carboncheck.spring.carboncheckserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
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
        user.setPassword(joinRequestDTO.getPassword());
        user.setName(joinRequestDTO.getName());
        user.setAuthType(joinRequestDTO.getAuthType());

        return ResponseEntity.ok().body(userService.join(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        String email = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();

        return ResponseEntity.ok().body(userService.login(email, password));
    }
}
