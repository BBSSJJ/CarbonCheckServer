package kr.co.carboncheck.spring.carboncheckserver.controller;

import kr.co.carboncheck.spring.carboncheckserver.dto.device.JoinHomeServerRequest;
import kr.co.carboncheck.spring.carboncheckserver.dto.device.JoinHomeServerResponse;
import kr.co.carboncheck.spring.carboncheckserver.dto.device.RegisterHomeServerRequest;
import kr.co.carboncheck.spring.carboncheckserver.dto.device.RegisterHomeServerResponse;
import kr.co.carboncheck.spring.carboncheckserver.service.device.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class DeviceController {
    private DeviceService deviceService;

    @Autowired
    DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/homeserver/register")
    public ResponseEntity<RegisterHomeServerResponse> registerHomeServer(@RequestBody RegisterHomeServerRequest registerHomeServerRequest) {
        System.out.println("홈서버 등록 요청");
        String homeServerId = registerHomeServerRequest.getHomeServerId();
        String email = registerHomeServerRequest.getEmail();
        return ResponseEntity.ok().body(deviceService.createHomeServer(homeServerId, email));
    }

    @PostMapping("/homeserver/join")
    public ResponseEntity<JoinHomeServerResponse> joinHomeServer(@RequestBody JoinHomeServerRequest joinHomeServerRequest) {
        System.out.println("홈서버 등록 요청");
        String homeServerId = joinHomeServerRequest.getHomeServerId();
        String email = joinHomeServerRequest.getEmail();
        return ResponseEntity.ok().body(deviceService.joinHomeServer(homeServerId, email));
    }
}
