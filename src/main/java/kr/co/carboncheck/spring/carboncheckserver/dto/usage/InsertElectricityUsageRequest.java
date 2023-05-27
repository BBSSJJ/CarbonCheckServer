package kr.co.carboncheck.spring.carboncheckserver.dto.usage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsertElectricityUsageRequest {
    @JsonProperty("plug_id")
    private int plugId;

    private float amount;

    public int getPlugId() {
        return plugId;
    }

    public void setPlugId(int plugId) {
        this.plugId = plugId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
