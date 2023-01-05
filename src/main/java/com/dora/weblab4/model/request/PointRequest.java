package com.dora.weblab4.model.request;

import com.dora.weblab4.model.Point;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PointRequest implements Point {
    @Min(value = -4, message = "less_than_minus_4")
    @Max(value = 4, message = "greater_than_4")
    @NotNull(message = "required")
    private Double x;

    @Min(value = -4, message = "less_than_minus_5")
    @Max(value = 4, message = "greater_than_5")
    @NotNull(message = "required")
    private Double y;

    @Min(value = -4, message = "less_than_1")
    @Max(value = 4, message = "greater_than_4")
    @NotNull(message = "required")
    private Double r;

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
