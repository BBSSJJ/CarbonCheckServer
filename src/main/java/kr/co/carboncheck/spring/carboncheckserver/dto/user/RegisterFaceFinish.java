package kr.co.carboncheck.spring.carboncheckserver.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterFaceFinish {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("home_server_id")
    private String homeServerId;

    private boolean result;

    public RegisterFaceFinish(String userId, String homeServerId, boolean result) {
        this.userId = userId;
        this.homeServerId = homeServerId;
        this.result = result;
    }

    public String getHomeServerId() {
        return homeServerId;
    }

    public void setHomeServerId(String homeServerId) {
        this.homeServerId = homeServerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
