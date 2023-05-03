package kr.co.carboncheck.spring.carboncheckserver.dto;

public class JoinRequestDTO {
    private String email;
    private String password;
    private String name;
    private String authType;

    public JoinRequestDTO(String email, String password, String name, String authType) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.authType = authType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }
}
