package kr.co.carboncheck.spring.carboncheckserver.controller;

import kr.co.carboncheck.spring.carboncheckserver.domain.Plug;
import kr.co.carboncheck.spring.carboncheckserver.dto.device.*;
import kr.co.carboncheck.spring.carboncheckserver.service.device.HomeServerService;
import kr.co.carboncheck.spring.carboncheckserver.service.device.PlugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeviceController {
    private HomeServerService homeServerService;
    private PlugService plugService;

    @Autowired
    DeviceController(HomeServerService homeServerService, PlugService plugService) {
        this.homeServerService = homeServerService;
        this.plugService = plugService;
    }

    @PostMapping("/homeserver/register")
    public ResponseEntity<RegisterHomeServerResponse> registerHomeServer(@RequestBody RegisterHomeServerRequest registerHomeServerRequest) {
        System.out.println("홈서버 등록 요청");
        String homeServerId = registerHomeServerRequest.getHomeServerId();
        String email = registerHomeServerRequest.getEmail();
        return ResponseEntity.ok().body(homeServerService.createHomeServer(homeServerId, email));
    }

    @PostMapping("/homeserver/join")
    public ResponseEntity<JoinHomeServerResponse> joinHomeServer(@RequestBody JoinHomeServerRequest joinHomeServerRequest) {
        System.out.println("홈서버 참여 요청");
        String homeServerId = joinHomeServerRequest.getHomeServerId();
        String email = joinHomeServerRequest.getEmail();
        return ResponseEntity.ok().body(homeServerService.joinHomeServer(homeServerId, email));
    }

    @PostMapping("/plug/register")
    public ResponseEntity<RegisterPlugResponse> registerPlug(@RequestBody RegisterPlugRequest registerPlugRequest){
        System.out.println("플러그 등록");

        return ResponseEntity.ok().body(plugService.registerPlug(registerPlugRequest.getPlugId(), registerPlugRequest.getUserId()));
    }

    @DeleteMapping("/plug/delete")
    public ResponseEntity<Boolean> deletePlug(@RequestParam("plugId") String plugId){
        return ResponseEntity.ok().body(plugService.removePlug(plugId));
    }
}
