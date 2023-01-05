package com.dora.weblab4.controller.validation;

public class ErrorReason {
    private String field;
    private String message;

    public ErrorReason(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
