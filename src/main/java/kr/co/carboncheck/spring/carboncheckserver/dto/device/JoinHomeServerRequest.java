package kr.co.carboncheck.spring.carboncheckserver.dto.device;

public class JoinHomeServerRequest {
    private String homeServerId;
    private String email;

    public JoinHomeServerRequest(String homeServerId, String email) {
        this.homeServerId = homeServerId;
        this.email = email;
    }

    public String getHomeServerId() {
        return homeServerId;
    }

    public void setHomeServerId(String homeServerId) {
        this.homeServerId = homeServerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
