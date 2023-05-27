package kr.co.carboncheck.spring.carboncheckserver.dto.usage;

public class InsertElectricityUsageResponse {
    private Boolean success;

    public InsertElectricityUsageResponse(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
