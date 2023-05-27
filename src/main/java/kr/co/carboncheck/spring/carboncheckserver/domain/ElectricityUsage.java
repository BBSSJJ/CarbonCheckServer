package kr.co.carboncheck.spring.carboncheckserver.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class ElectricityUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "electricity_usage_id")
    private int electricityUsageId;

    @Column(name = "plug_id")
    private int plugId;

    private LocalDateTime date;

    private float amount;

    public ElectricityUsage(int plugId, float amount) {
        this.plugId = plugId;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }

    public ElectricityUsage() {
    }
}
