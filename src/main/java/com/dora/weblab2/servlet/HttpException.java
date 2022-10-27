package com.dora.weblab2.servlet;

import javax.servlet.ServletException;

public class HttpException extends ServletException {
    public final int statusCode;

    public HttpException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
