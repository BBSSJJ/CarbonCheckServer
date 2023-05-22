package kr.co.carboncheck.spring.carboncheckserver.dto.usage;

public class InsertWaterUsageResponse {
    private Boolean success;

    public InsertWaterUsageResponse(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
