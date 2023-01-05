package com.dora.weblab4.model;

import java.time.ZonedDateTime;

public interface Check extends Point {
    boolean isHit();
    ZonedDateTime getDate();
    long getExecutionTime();
}
