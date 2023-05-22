package kr.co.carboncheck.spring.carboncheckserver;


import kr.co.carboncheck.spring.carboncheckserver.controller.UsageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;


//@Configuration
//@EnableWebSocket
//@ComponentScan
public class WebSocketConfig implements WebSocketConfigurer {

    private final UsageHandler usageHandler;

//    @Autowired
    public WebSocketConfig(UsageHandler usageHandler) {
        this.usageHandler = usageHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {    //웹소켓 핸들러 등록

        registry.addHandler(usageHandler, "/waterusage/insert").setAllowedOrigins("*");
        registry.addHandler(usageHandler, "/electricityusage/insert").setAllowedOrigins("*");
    }
}

//STOMP이용 방법. 다음에 시도해보자

//@Configuration
//@EnableWebSocket
//@ComponentScan
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    private final UsageHandler usageHandler;
//
//    @Autowired
//    public WebSocketConfig(UsageHandler usageHandler) {
//        System.out.println("WebSocketConfig bean created successfully.");
//        this.usageHandler = usageHandler;
//        System.out.println(this.usageHandler);
//    }
//
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry){
//        registry.addEndpoint("/ws").withSockJS();
//    }
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry){
//        registry.setApplicationDestinationPrefixes("/app");
//        registry.enableSimpleBroker("/topic");
//    }
//}
