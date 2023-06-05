package kr.co.carboncheck.spring.carboncheckserver.dto.user;

public class UpdateTargetAmountRequest {
    String userId;
    int targetAmount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(int targetAmount) {
        this.targetAmount = targetAmount;
    }
}
