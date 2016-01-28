package com.example.controllers;

import com.example.errors.InvalidCredentialsException;
import com.example.models.User;
import com.example.models.UserLogin;
import com.example.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableJpaRepositories("com.example.repositories")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String root() { return "Ba-Bam!"; }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public @ResponseBody String login(@RequestBody UserLogin user) throws InvalidCredentialsException {
        User realUser = userRepository.findByUsername(user.getUsername());

        if (realUser == null) throw new InvalidCredentialsException();

        if (checkPassword(user.getPassword(), realUser.getEncryptedPassword())) {
            realUser.setToken();
            userRepository.save(realUser);

            return realUser.getToken();
        }
        else { throw new InvalidCredentialsException(); }
    }

    @RequestMapping("/logout")
    public @ResponseBody String logout(@RequestHeader(value="token") String token) {
        User realUser = userRepository.findByToken(token);

        if (realUser != null) {
            realUser.invalidateToken();
            userRepository.save(realUser);
        }

        return "You're logged out!";
    }

    private static boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified;

        if(null == stored_hash || !stored_hash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

        return(password_verified);
    }
}
