package kr.co.carboncheck.spring.carboncheckserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Plug {
    @Id
    @Column(name = "plug_id")
    String plugId;

    @Column(name = "user_id")
    String userId;

    public Plug(String plugId, String userId) {
        this.plugId = plugId;
        this.userId = userId;
    }

    public Plug() {
    }

    public String getPlugId() {
        return plugId;
    }

    public void setPlugId(String plugId) {
        this.plugId = plugId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
