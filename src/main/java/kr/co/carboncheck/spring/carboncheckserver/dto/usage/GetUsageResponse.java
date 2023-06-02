package kr.co.carboncheck.spring.carboncheckserver.dto.usage;

import java.util.Map;

public class GetUsageResponse {
    private String str;
    private float amount;

    public GetUsageResponse(String str, float amount) {
        this.str = str;
        this.amount = amount;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
