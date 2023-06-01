package kr.co.carboncheck.spring.carboncheckserver.dto.usage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsertElectricityUsageRequest {

    @JsonProperty("plug_id")
    private String plugId;

    @JsonProperty("cumulative_amount")
    private int cumulativeAmount;

    public String getPlugId() {
        return plugId;
    }

    public void setPlugId(String plugId) {
        this.plugId = plugId;
    }

    public int getCumulativeAmount() {
        return cumulativeAmount;
    }

    public void setCumulativeAmount(int cumulativeAmount) {
        this.cumulativeAmount = cumulativeAmount;
    }
}
