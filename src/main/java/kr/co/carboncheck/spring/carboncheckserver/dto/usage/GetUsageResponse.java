package kr.co.carboncheck.spring.carboncheckserver.dto.usage;

import java.util.Map;

public class GetUsageResponse {
    Map<String, Float> usage;

    public GetUsageResponse(Map<String, Float> usage) {
        this.usage = usage;
    }

    public Map<String, Float> getUsage() {
        return usage;
    }

    public void setUsage(Map<String, Float> usage) {
        this.usage = usage;
    }
}
