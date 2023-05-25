package kr.co.carboncheck.spring.carboncheckserver.sse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class SseController {

    private SseGroupManager sseGroupManager;

    @Autowired
    public SseController(SseGroupManager sseGroupManager) {
        this.sseGroupManager = sseGroupManager;
    }

    //objectId가 homeServerId와 같으면 홈서버이고, 다르면 User이다.
    @RequestMapping(value = "/subscribe/{homeServerId}/{objectId}", method = RequestMethod.GET)
    public SseEmitter subscribeToGroup(@PathVariable String homeServerId, @PathVariable String objectId) throws IOException {
        System.out.println("in subscribe to group");
        SseEmitter emitter = new SseEmitter(0L);
        Subscriber subscriber = new Subscriber(objectId);

        sseGroupManager.addSubscriberToGroup(homeServerId, subscriber);

        //test
        sseGroupManager.findSubscribers(homeServerId);

        //emitter가 닫혔으면 종료하도록
        AtomicBoolean EmitterClosed = new AtomicBoolean(false);

        emitter.onCompletion(() -> {
            sseGroupManager.removeSubscribeFromGroup(homeServerId, subscriber);
            stopConnection(emitter);
            EmitterClosed.set(true);
        });

        emitter.onTimeout(() -> {
            sseGroupManager.removeSubscribeFromGroup(homeServerId, subscriber);
            stopConnection(emitter);
            EmitterClosed.set(true);
        });

        emitter.send(SseEmitter.event().name("sse").data("connected"));

        maintainConnection(emitter, EmitterClosed);

        return emitter;
    }

    private void maintainConnection(SseEmitter emitter, AtomicBoolean EmitterClosed) {
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);
        scheduledExecutor.scheduleAtFixedRate(() -> {
            if (!EmitterClosed.get()) {
                try {
                    System.out.println("sse 연결 유지용 데이터 전송");
                    emitter.send(SseEmitter.event().data("to maintain connection"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                scheduledExecutor.shutdown();
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    private void stopConnection(SseEmitter emitter) {
        emitter.complete();
    }
}
