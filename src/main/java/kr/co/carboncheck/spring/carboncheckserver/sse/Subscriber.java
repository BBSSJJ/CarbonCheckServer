package kr.co.carboncheck.spring.carboncheckserver.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class Subscriber {
    private String id;
    private SseEmitter emitter;

    public Subscriber(String id) {
        this.id = id;
        this.emitter = new SseEmitter();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SseEmitter getEmitter() {
        return emitter;
    }

    public void setEmitter(SseEmitter emitter) {
        this.emitter = emitter;
    }
}
