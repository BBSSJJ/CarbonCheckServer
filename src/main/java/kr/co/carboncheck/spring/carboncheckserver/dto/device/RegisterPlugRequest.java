package kr.co.carboncheck.spring.carboncheckserver.dto.device;

public class RegisterPlugRequest {
    private String plugId;
    private String userId;

    public String getPlugId() {
        return plugId;
    }

    public void setPlugId(String plugId) {
        this.plugId = plugId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
