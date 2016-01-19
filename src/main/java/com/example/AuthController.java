package com.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @RequestMapping("/login")
    public String login() {
        return "You've logged in!";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "You've logged out!";
    }
}
