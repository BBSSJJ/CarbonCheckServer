package kr.co.carboncheck.spring.carboncheckserver.domain;


import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "home_server_id")
    private String homeServerId;

    private String email;
    private String name;
    private String password;

    @Column(name = "auth_type")
    private String authType;

    @Column(name = "target_amount")
    private int targetAmount;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHomeServerId() {
        return homeServerId;
    }

    public void setHomeServerId(String homeServerId) {
        this.homeServerId = homeServerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password; }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public int getTargetAmount() { return targetAmount; }

    public void setTargetAmount(int targetAmount) { this.targetAmount = targetAmount; }
}
