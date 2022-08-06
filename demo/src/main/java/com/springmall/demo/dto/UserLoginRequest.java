package com.springmall.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserLoginRequest {
    @NotBlank@Email
    private String email;
    @NotBlank
    private String Password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
