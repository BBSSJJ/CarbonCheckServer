package kr.co.carboncheck.spring.carboncheckserver.dto.user;

public class GetGroupTargetAmountResponse {

    private String name;
    private int targetAmount;

    public GetGroupTargetAmountResponse(String name, int targetAmount) {
        this.name = name;
        this.targetAmount = targetAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(int targetAmount) {
        this.targetAmount = targetAmount;
    }
}
