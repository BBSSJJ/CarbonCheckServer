package kr.co.carboncheck.spring.carboncheckserver.sse;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SseGroupManager {
    private Map<String, SseGroup> groups;

    public SseGroupManager() {
        this.groups = new HashMap<>();
    }

    public void addSubscriberToGroup(String homeServerId, Subscriber subscriber) {
        SseGroup group = groups.get(homeServerId);
        if (group == null) {
            group = new SseGroup(homeServerId);
            groups.put(homeServerId, group);
        }
        group.addSubscriber(subscriber);
    }

    public void removeSubscribeFromGroup(String homeServerId, Subscriber subscriber) {
        SseGroup group = groups.get(homeServerId);
        if (group != null) {
            group.removeSubscriber(subscriber);
            if (group.getSubscribers().isEmpty()) {
                groups.remove(homeServerId);
            }
        }
    }

    public void findSubscribers(String homeServerId) {
        SseGroup group = groups.get(homeServerId);
        if (group == null) {
            System.out.println("no group");
        } else {
            List<Subscriber> subscribers = group.getSubscribers();
            for (Subscriber subscriber : subscribers) {
                System.out.println(subscriber.getId());
            }
        }
    }
}
