package com.dora.weblab4.controller.validation;

import java.util.List;

public class ErrorMessage {

    private String message;
    private List<ErrorReason> reasons;

    public ErrorMessage(String message, List<ErrorReason> reasons) {
        this.message = message;
        this.reasons = reasons;
    }

    public String getMessage() {
        return message;
    }

    public List<ErrorReason> getReasons() {
        return reasons;
    }
}
