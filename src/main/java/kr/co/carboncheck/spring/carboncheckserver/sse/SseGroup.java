package kr.co.carboncheck.spring.carboncheckserver.sse;

import java.util.ArrayList;
import java.util.List;

public class SseGroup {
    private String homeServerId;
    private List<Subscriber> subscribers;

    public SseGroup(String homeServerId) {
        this.homeServerId = homeServerId;
        this.subscribers = new ArrayList<>();
    }

    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }
}
