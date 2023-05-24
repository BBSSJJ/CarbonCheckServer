package kr.co.carboncheck.spring.carboncheckserver.dto.user;

public class GetUserDataResponse {
    String userId;
    String homeServerId;
    String name;

    public GetUserDataResponse(String userId, String homeServerId, String name) {
        this.userId = userId;
        this.homeServerId = homeServerId;
        this.name = name;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
