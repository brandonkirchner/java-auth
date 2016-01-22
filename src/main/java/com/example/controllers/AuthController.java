package com.example.controllers;

import com.example.models.User;
import com.example.models.UserLogin;
import com.example.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableJpaRepositories("com.example.repositories")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String root() { return "Ba-Bam!"; }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(@RequestBody UserLogin user) {
        List<User> userList = userRepository.findByUsername(user.getUsername());

        if (userList.size() != 1)  {
            return "Seems like the user doesnt exist, or" +
                " there are multiple users with username " + user.getUsername() + ". Not good..." + userList.toString();
        }

        User realUser = userList.get(0);

        if (checkPassword(user.getPassword(), realUser.getEncryptedPassword())) {
            return "You're logged in!";
        }

        return "Invalid! " + user.getPassword() + " isnt equal to " + realUser.getEncryptedPassword() + ".";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "You're logged out!";
    }

    private static boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified = false;

        if(null == stored_hash || !stored_hash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

        return(password_verified);
    }
}
