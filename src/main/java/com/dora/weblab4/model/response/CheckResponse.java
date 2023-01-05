package com.dora.weblab4.model.response;

import com.dora.weblab4.model.Check;

import java.time.ZonedDateTime;

public class CheckResponse implements Check {

    private double x;
    private double y;
    private double r;
    private boolean hit;
    private ZonedDateTime date;
    private long executionTime;

    public CheckResponse(Check result) {
        this.x = result.getX();
        this.y = result.getY();
        this.r = result.getR();
        this.hit = result.isHit();
        this.date = result.getDate();
        this.executionTime = result.getExecutionTime();
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
}
