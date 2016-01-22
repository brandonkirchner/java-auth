package com.example.models;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;

@Entity
@Table(name = "APP_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    public User() {
        // do nothing
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = encryptPassword(password); }

    private static String encryptPassword(String password) {
        String salt = BCrypt.gensalt(12);

        return BCrypt.hashpw(password, salt);
    }
}
