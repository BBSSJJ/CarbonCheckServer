package kr.co.carboncheck.spring.carboncheckserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;
import kr.co.carboncheck.spring.carboncheckserver.domain.WaterUsage;
import kr.co.carboncheck.spring.carboncheckserver.service.usage.UsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Objects;

@Controller
public class UsageHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final UsageService<WaterUsage> waterUsageService;
    private final UsageService<ElectricityUsage> electricityUsageService;

    @Autowired
    public UsageHandler(ObjectMapper objectMapper,
                        UsageService<WaterUsage> waterUsageService,
                        UsageService<ElectricityUsage> electricityUsageService) {
        this.objectMapper = objectMapper;
        this.waterUsageService = waterUsageService;
        this.electricityUsageService = electricityUsageService;
    }

    //websocket 사용할 때는 Rest API와 달리 일반적으로 dto사용하지 않고 Json 형식의 텍스트 메세지 이용
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("in handleTextMessage");
        String requestUrl = Objects.requireNonNull(session.getUri()).getPath();
        if (requestUrl.equals("/waterusage/insert")) {
            String waterUsageData = message.getPayload();

            // JSON 배열을 맵의 배열로 역직렬화
            Map<String, Object>[] waterUsageDataMap = objectMapper.readValue(waterUsageData, Map[].class);

            // 각 맵 요소를 객체로 변환하고 처리
            for (Map<String, Object> waterUsageMap : waterUsageDataMap) {
                WaterUsage waterUsage = objectMapper.convertValue(waterUsageMap, WaterUsage.class);
                waterUsageService.insertUsage(waterUsage);
            }

            //받은 데이터를 객체로 변환
//            WaterUsage waterUsage = objectMapper.readValue(waterUsageData, WaterUsage.class);
//            waterUsageService.insertUsage(waterUsage);

            String response = "등록 성공!";
            session.sendMessage(new TextMessage(response));
        } else if (requestUrl.equals("/electricityusage/insert")) {
        }
    }
}

//STOMP이용 방법. 다음에 시도해보자

//@Controller
//public class UsageHandler extends TextWebSocketHandler {
//
//    private final ObjectMapper objectMapper;
//    private final UsageService<WaterUsage> waterUsageService;
//
//    public UsageHandler(ObjectMapper objectMapper, UsageService<WaterUsage> waterUsageService) {
//        System.out.println("UsageHandler bean created successfully.");
//        this.objectMapper = objectMapper;
//        this.waterUsageService = waterUsageService;
//        System.out.println(objectMapper);
//        System.out.println(waterUsageService);
//        System.out.println("생성자 잘 됨");
//    }
//
//    //websocket 사용할 때는 Rest API와 달리 일반적으로 dto사용하지 않고 Json 형식의 텍스트 메세지 이용
//    @MessageMapping("/waterusage/insert")
//    protected void insertWaterUsage(WebSocketSession session, TextMessage waterUsageMessage) throws Exception {
//        System.out.println("in handleTextMessage");
//        String waterUsageData = waterUsageMessage.getPayload();
//
//        //받은 데이터를 객체로 변환
//        WaterUsage waterUsage = objectMapper.readValue(waterUsageData, WaterUsage.class);
//        waterUsageService.insertUsage(waterUsage);
//
//        String response = "등록 성공!";
//        session.sendMessage(new TextMessage(response));
//    }
//
//    @MessageMapping("/electricityusage/insert")
//    public void insertElectricityUsage(WebSocketSession session, TextMessage message) {
//        System.out.println("이건 되나?");
//    }
//}