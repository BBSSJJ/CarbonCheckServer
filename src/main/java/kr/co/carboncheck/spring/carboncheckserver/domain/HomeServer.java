package kr.co.carboncheck.spring.carboncheckserver.domain;

import javax.persistence.*;

@Entity
public class HomeServer {
    @Id
    @Column(name = "home_server_id")
    private String homeServerId;

    public String getHomeServerId() {
        return homeServerId;
    }

    public void setHomeServerId(String homeServerId) {
        this.homeServerId = homeServerId;
    }
}
