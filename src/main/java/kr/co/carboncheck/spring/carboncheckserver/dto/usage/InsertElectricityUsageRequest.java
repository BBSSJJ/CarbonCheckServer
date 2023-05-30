package kr.co.carboncheck.spring.carboncheckserver.dto.usage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsertElectricityUsageRequest {

    @JsonProperty("plug_id")
    private String plugId;

    private int amount;

    public String getPlugId() {
        return plugId;
    }

    public void setPlugId(String plugId) {
        this.plugId = plugId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
