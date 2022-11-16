package com.dora.weblab2.util;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Area implements Hittable {
    public final Set<Hittable> subareas;

    @Override
    public boolean contains(double x, double y, double r) {
        return subareas.stream().anyMatch(h -> h.contains(x, y, r));
    }

    public Area() {
        subareas = new HashSet<Hittable>();

        Hittable sector = (x, y, r) -> x <= 0 && y >= 0 && (x * x + y * y) <= r * r / 4;
        Hittable triangle = (x, y, r) -> x <= 0 && y <= 0 && y >= (-x - r) / 2;
        Hittable rectangle = (x, y, r) -> x >= 0 && y <= 0 && y >= -r && x <= r / 2;

        subareas.add(sector);
        subareas.add(triangle);
        subareas.add(rectangle);
    }
}
