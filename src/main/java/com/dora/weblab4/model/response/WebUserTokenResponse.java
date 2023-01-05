package com.dora.weblab4.model.response;

import com.dora.weblab4.model.WebUser;

public class WebUserTokenResponse implements WebUser {
    private String username;
    private String token;

    public WebUserTokenResponse(String username, String token) {
        this.username = username;
        this.token = token;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
