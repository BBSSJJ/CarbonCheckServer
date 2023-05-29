package kr.co.carboncheck.spring.carboncheckserver.dto.user;

public class RegisterFaceRequest {
    String userId;
    String homeServerId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHomeServerId() {
        return homeServerId;
    }

    public void setHomeServerId(String homeServerId) {
        this.homeServerId = homeServerId;
    }
}
