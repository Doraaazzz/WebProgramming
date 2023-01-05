package com.dora.weblab4.model;

public class WebUserName implements WebUser {
    private String username;

    public WebUserName(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
