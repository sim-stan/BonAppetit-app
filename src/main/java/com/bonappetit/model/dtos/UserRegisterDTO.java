package com.bonappetit.model.dtos;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterDTO {


    @NotNull
    @Size(min = 3, max = 20,message = "Username length must be between 3 and 20 characters!")
    private String username;


    @Email
    @NotBlank(message = "Email cannot be empty!")
    private String email;

    @NotBlank(message = "Password length must be between 3 and 20 characters!")
    private String password;


    @NotBlank(message = "Passwords should match!")
    private String confirmPassword;


    public UserRegisterDTO() {
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
