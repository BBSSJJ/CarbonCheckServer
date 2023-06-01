package kr.co.carboncheck.spring.carboncheckserver.dto.user;

import java.util.Map;

public class GetGroupTargetAmountResponse {
    Map<String, Integer> groupTargetAmount;

    public GetGroupTargetAmountResponse(Map<String, Integer> groupTargetAmount) {
        this.groupTargetAmount = groupTargetAmount;
    }

    public Map<String, Integer> getGroupTargetAmount() {
        return groupTargetAmount;
    }

    public void setGroupTargetAmount(Map<String, Integer> groupTargetAmount) {
        this.groupTargetAmount = groupTargetAmount;
    }
}
