package com.dora.weblab4.model;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "weblab4_check")
public class CheckModel implements Check {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHECK_ID_GENERATOR")
    @SequenceGenerator(name = "CHECK_ID_GENERATOR", sequenceName = "weblab4_check_id", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    private double x;
    private double y;
    private double r;

    private boolean hit;
    private ZonedDateTime date;
    private long executionTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private WebUserModel user;

    public CheckModel() {}

    public CheckModel(
        double x,
        double y,
        double r,
        boolean hit,
        ZonedDateTime date,
        long executionTime,
        WebUserModel user
    ) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.date = date;
        this.executionTime = executionTime;
        this.user = user;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getR() {
        return r;
    }

    @Override
    public boolean isHit() {
        return hit;
    }

    @Override
    public ZonedDateTime getDate() {
        return date;
    }

    @Override
    public long getExecutionTime() {
        return executionTime;
    }
}
