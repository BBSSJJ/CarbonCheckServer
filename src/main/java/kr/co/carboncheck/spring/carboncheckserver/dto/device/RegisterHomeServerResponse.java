package kr.co.carboncheck.spring.carboncheckserver.dto.device;

public class RegisterHomeServerResponse {
    private Boolean success;
    private String message;


    public RegisterHomeServerResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
