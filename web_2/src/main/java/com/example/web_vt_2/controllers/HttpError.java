package com.example.web_vt_2.controllers;

import java.io.Serializable;

public class HttpError implements Serializable {
        private int statusCode;
        private String errorMessage;

        HttpError(int statusCode,String errorMessage){
            this.errorMessage = errorMessage;
            this.statusCode = statusCode;
        }

    public int getStatusCode() {
        return statusCode;
    }
    public String getErrorMessage(){
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public void setStatusCode(int statusCode){
        this.statusCode = statusCode;
    }
}
