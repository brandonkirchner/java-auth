package com.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @RequestMapping("/login")
    public String login() {
        return "You're logged in!";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "You're logged out!";
    }
}
