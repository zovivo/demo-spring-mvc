package com.input.create;

import com.validator.Password;
import org.hibernate.validator.constraints.NotBlank;

public class UserFormCreate {

    @NotBlank(message = "userName not empty")
    private String userName;
    @NotBlank(message = "email not empty")
    private String email;
    private String name;
    @NotBlank(message = "password not empty")
    @Password
    private String password;
    @NotBlank(message = "confirm password not empty")
    private String confirmPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
