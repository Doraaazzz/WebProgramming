package com.dora;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_id")
    @SequenceGenerator(name = "result_id", sequenceName = "result_id", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Range(min = -4, max = 4)
    @NotNull
    private BigDecimal x = new BigDecimal(0);

    @Range(min = -5, max = 5)
    @NotNull
    private BigDecimal y = new BigDecimal(0);

    @Range(min = 2, max = 5)
    @NotNull
    private BigDecimal r = new BigDecimal(2);

    @NotNull
    private boolean hit;

    @NotNull
    private LocalDateTime date;
    @NotNull
    private long executionTime;

    public long getId() { return id; }

    public double getX() {
        return x.doubleValue();
    }

    public double getY() {
        return y.doubleValue();
    }

    public double getR() {
        return r.doubleValue();
    }

    public boolean isHit() {
        return hit;
    }

    public String getDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
        return date.format(formatter);
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public Result() {}

    public Result(double x, double y, double r, boolean hit, LocalDateTime date, long executionTime) {
        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
        this.r = new BigDecimal(r);
        this.hit = hit;
        this.date = date;
        this.executionTime = executionTime;
    }
}
