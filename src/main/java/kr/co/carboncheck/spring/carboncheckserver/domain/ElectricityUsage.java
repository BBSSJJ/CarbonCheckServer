package kr.co.carboncheck.spring.carboncheckserver.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ElectricityUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "electricity_usage_id")
    private int electricityUsageId;

    @Column(name = "plug_id")
    private String plugId;

    private LocalDateTime date;

    private int amount;

    public int getElectricityUsageId() {
        return electricityUsageId;
    }

    public void setElectricityUsageId(int electricityUsageId) {
        this.electricityUsageId = electricityUsageId;
    }

    public String getPlugId() {
        return plugId;
    }

    public void setPlugId(String plugId) {
        this.plugId = plugId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
