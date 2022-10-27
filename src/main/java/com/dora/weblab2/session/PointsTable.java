package com.dora.weblab2.session;

import java.util.ArrayList;
import java.util.List;

public class PointsTable {
    private List<Point> points;

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public PointsTable() {
        this.points = new ArrayList<>();
    }
}
