package com.example
import com.example.controllers.UserController
import com.example.errors.InvalidUserException
import com.example.models.User
import com.example.models.UserLogin
import com.example.repositories.UserRepository
import spock.lang.Specification

class UserSpec extends Specification {
    private UserRepository userRepository = Mock(UserRepository);
    private UserController userController = new UserController(userRepository: userRepository);
    private UserLogin userLogin = new UserLogin(username: "brandon", password: "password")
    private User user = new User(username: "brandon", password: "password")

    def "get root should return ba-bam string"() {
        when: String result = userController.root()
        then: result == "Ba-Bam!"
    }

    def "logging in should return a token"() {
        when:
        String result = userController.login(userLogin)

        then:
        1 * userRepository.findByUsername(userLogin.username) >> user
        result == user.getToken()
    }

    def "logging in with invalid credentials should return error"() {
        when:
        userController.login(userLogin)

        then:
        thrown(InvalidUserException)
    }

    def "logging out should invalidate the token"() {
        when:
        userController.login(userLogin)
        userController.logout(user.getToken())

        then:
        1 * userRepository.findByUsername(userLogin.username) >> user
        1 * userRepository.findByToken(_) >> user
        user.getToken() == null
    }

    def "logging out with invalid token should carry on normally"() {
        given:
        String fakeTokenValue = "random not a token"

        when:
        String response = userController.logout(fakeTokenValue)

        then:
        1 * userRepository.findByToken(fakeTokenValue) >> null
        response == "You're logged out!"
    }
}
