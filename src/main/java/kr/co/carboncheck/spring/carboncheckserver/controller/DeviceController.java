package kr.co.carboncheck.spring.carboncheckserver.controller;

import kr.co.carboncheck.spring.carboncheckserver.dto.device.RegisterHomeServerRequest;
import kr.co.carboncheck.spring.carboncheckserver.dto.device.RegisterHomeServerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DeviceController {
    @PostMapping("/register/homeserver")
    public ResponseEntity<RegisterHomeServerResponse> registerHomeServer(RegisterHomeServerRequest registerHomeServerRequest){

        return ResponseEntity.ok().body(new RegisterHomeServerResponse());
    }
}
