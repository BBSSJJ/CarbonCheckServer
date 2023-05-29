package kr.co.carboncheck.spring.carboncheckserver.dto.user;

public class RegisterFaceResponse {
    Boolean delivered;
    String message;

    public RegisterFaceResponse(Boolean delivered, String message) {
        this.delivered = delivered;
        this.message = message;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
