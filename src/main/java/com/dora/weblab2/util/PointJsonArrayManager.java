package com.dora.weblab2.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalDateTime;

public class PointJsonArrayManager {
    private JsonArray array;

    public void insertPoint(double x, double y, double r, boolean hit, LocalDateTime date, long executionTime) {
        JsonObject obj = new JsonObject();
        obj.addProperty("x", x);
        obj.addProperty("y", y);
        obj.addProperty("r", r);
        obj.addProperty("hit", hit);
        obj.addProperty("date", date.toString());
        obj.addProperty("executionTime", executionTime);
        array.add(obj);
    }

    @Override
    public String toString() {
        return new Gson().toJson(array);
    }

    public PointJsonArrayManager() {
        array = new JsonArray();
    }
}
