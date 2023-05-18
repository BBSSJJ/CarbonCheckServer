package kr.co.carboncheck.spring.carboncheckserver.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class WaterUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "water_usage_id")
    private int waterUsageId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    private String place;

    private float amount;

    public int getWaterUsageId() {
        return waterUsageId;
    }

    public void setWaterUsageId(int waterUsageId) {
        this.waterUsageId = waterUsageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
