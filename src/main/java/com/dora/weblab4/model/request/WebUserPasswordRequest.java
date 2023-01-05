package com.dora.weblab4.model.request;

import com.dora.weblab4.model.WebUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class WebUserPasswordRequest implements WebUser {

    @NotBlank(message = "required")
    private String username;

    @NotBlank(message = "required")
    @Pattern(regexp = "^.{8,}$", message = "shorter_than_8")
    @Pattern(regexp = "^.{0,20}$", message = "longer_than_20")
    @Pattern(regexp = "^.*[0-9]+.*$", message = "no_digits")
    @Pattern(regexp = "^.*[A-Z]+.*$", message = "no_uppercase")
    @Pattern(regexp = "^.*[a-z]+.*$", message = "no_lowercase")
    private String password;

    public WebUserPasswordRequest() {}

    public WebUserPasswordRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
