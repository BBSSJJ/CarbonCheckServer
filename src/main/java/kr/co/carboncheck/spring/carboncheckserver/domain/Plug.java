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
    int userId;

    public Plug(String plugId, int userId) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
