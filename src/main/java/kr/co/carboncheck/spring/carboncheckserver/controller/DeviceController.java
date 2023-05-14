package kr.co.carboncheck.spring.carboncheckserver.controller;

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

    @PostMapping("/register/homeserver")
    public ResponseEntity<RegisterHomeServerResponse> registerHomeServer(@RequestBody RegisterHomeServerRequest registerHomeServerRequest) {
        System.out.println("홈서버 등록 요청");
        String homeServerId = registerHomeServerRequest.getHomeServerId();
        String email = registerHomeServerRequest.getEmail();
        return ResponseEntity.ok().body(deviceService.createHomeServer(homeServerId, email));
    }
}
