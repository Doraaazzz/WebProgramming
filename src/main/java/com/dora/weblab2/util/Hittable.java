package com.dora.weblab2.util;

@FunctionalInterface
public interface Hittable {
    boolean contains(double x, double y, double r);
}
