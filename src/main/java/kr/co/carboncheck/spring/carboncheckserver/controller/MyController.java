package kr.co.carboncheck.spring.carboncheckserver.controller;

import kr.co.carboncheck.spring.carboncheckserver.dto.User;
import kr.co.carboncheck.spring.carboncheckserver.dto.UserData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    // /data로 post방식으로 온 요청
    // @RequestBody는 HTTP요청의 body를 JAVA객체로 변환하여 매개변수로 받는데 사용
    // 요청으로 받는 User 객체는, 안드로이드에서 요청 보냈던 객체와 동일한 구조여야 하고
    // 응답으로 보내는 UserData 객체는, 안드로이드에서 응답받는 객체와 동일한 구조
    @PostMapping("/data")
    public ResponseEntity<UserData> findUserData(@RequestBody User user) {
        System.out.println("post방식으로 왔다");
        System.out.println("유저 아이디는" + user.getUserId());
        UserData userData = new UserData(user.getUserId(), "this is usage", "this is time");
        return ResponseEntity.ok().body(userData);
    }
}