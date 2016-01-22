package com.example.models;

public class UserLogin {

    private Long id;

    private String username;
    private String password;

    public UserLogin() {
        // do nothing
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }
}
