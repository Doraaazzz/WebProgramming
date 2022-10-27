package com.dora.weblab2.service;

import java.util.stream.Stream;

public class Area implements Hittable {
    public final Hittable[] subareas;

    @Override
    public boolean contains(double x, double y, double r) {
        return Stream.of(subareas).anyMatch(h -> h.contains(x, y, r));
    }

    public Area() {
        Hittable subarea1 = new Hittable() {
            @Override
            public boolean contains(double x, double y, double r) {
                return false;
            }
        };
        Hittable subarea2 = new Hittable() {
            @Override
            public boolean contains(double x, double y, double r) {
                if (x > 0 || y < 0) return false;

                return y <= x + (r / 2);
            }
        };
        Hittable subarea3 = new Hittable() {
            @Override
            public boolean contains(double x, double y, double r) {
                if (x > 0 || y >= 0) return false;

                return x * x + y + y <= r * r;
            }
        };
        Hittable subarea4 = new Hittable() {
            @Override
            public boolean contains(double x, double y, double r) {
                if (x <= 0 || y >= 0) return false;

                boolean yAboveMinusR = y >= r;
                boolean xBelowHalfR = y <= (r / 2);

                return yAboveMinusR && xBelowHalfR;
            }
        };

        this.subareas = new Hittable[] { subarea1, subarea2, subarea3, subarea4 };
    }
}
