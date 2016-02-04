package com.example
import com.example.models.User
import com.example.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

public class LogoutSpec extends RestIntegrationBase {
    String getBasePath() { "logout" }

    @Autowired
    UserRepository userRepository

    def "remove all users from database"() {
        given:
        userRepository.deleteAll()

        when:
        List<User> allUsers = userRepository.findAll()

        then:
        allUsers.isEmpty()
    }

    def "valid user logout"() {
        setup:
        String tokenString = "token"
        User user = new User(username: "brandon", password: "password", token: tokenString)
        userRepository.save(user)

        when:
        RequestEntity<String> request = RequestEntity.get(serviceURI()).header("token", user.getToken()).build()
        ResponseEntity<String> response = restTemplate.exchange(request, String)

        then:
        response.statusCode == HttpStatus.OK
        response.body == "You're logged out!"
        userRepository.findByToken(tokenString) == null

        cleanup:
        userRepository.delete(user)
    }

    def "invalid user logout shouldnt give away anything"() {
        setup:
        String tokenString = "token"

        when:
        RequestEntity<String> request = RequestEntity.get(serviceURI()).header("token", tokenString).build()
        ResponseEntity<String> response = restTemplate.exchange(request, String)

        then:
        response.statusCode == HttpStatus.OK
        response.body == "You're logged out!"
    }
}
