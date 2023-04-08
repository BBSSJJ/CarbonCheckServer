package kr.co.carboncheck.spring.carboncheckserver.dto;

public class UserData {
    private String userId = "this is user id";
    private String usage = "this is usage";
    private String time = "this is time";

    public UserData(String id, String use, String t) {
        this.userId = id;
        this.usage = use;
        this.time = t;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsage() {
        return usage;
    }

    public String getTime() {
        return time;
    }
}
