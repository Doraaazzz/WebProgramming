package com.dora;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Named
@SessionScoped
public class AreaCheckService implements Serializable {

    @Inject
    private ResultsManager results;

    private long calculateExecutionTime(LocalDateTime startDate, LocalDateTime finishDate) {
        return ChronoUnit.MILLIS.between(startDate, finishDate);
    }

    public Result check(double x, double y, double r, LocalDateTime startDate) {
        boolean hit;
        if (x < 0 && y < 0) {
            hit = false;
        } else if (x >= 0 && y >= 0) {
            hit = y <= r && x <= r;
        } else if (x <= 0 && y >= 0) {
            hit = y <= x + r;
        } else {
            hit = x * x + y * y <= r * r;
        }

        LocalDateTime endDate = LocalDateTime.now();
        long executionTime = calculateExecutionTime(startDate, endDate);
        Result result = new Result(x, y, r, hit, startDate, executionTime);

        if (results.insert(result))
            return result;

        return null;
    }
}
