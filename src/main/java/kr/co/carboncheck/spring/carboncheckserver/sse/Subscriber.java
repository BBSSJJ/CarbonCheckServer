package kr.co.carboncheck.spring.carboncheckserver.sse;

public class Subscriber {
    private String id;

    public Subscriber(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
