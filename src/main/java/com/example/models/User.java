package com.example.models;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.SecureRandom;

@Entity
@Table(name = "APP_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String token;

    public User() {
        // do nothing
    }

    public String getUsername() { return username; }

    public String getEncryptedPassword() { return password; }

    public String getToken() { return token; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = encryptPassword(password); }

    public void setToken() { this.token = nextSessionId(); }

    public void invalidateToken() { this.token = null; }

    private static String encryptPassword(String password) {
        String salt = BCrypt.gensalt(12);

        return BCrypt.hashpw(password, salt);
    }

    private static String nextSessionId() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }
}
