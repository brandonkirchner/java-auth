package com.example
import com.example.models.User
import com.example.models.UserLogin
import com.example.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

class LoginSpec extends RestIntegrationBase {
    String getBasePath() { "login" }

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

    def "valid user login"() {
        setup:
        User user = new User(username: "brandon", password: "password")
        userRepository.save(user)

        UserLogin userLogin = new UserLogin(username: "brandon", password: "password")
        RequestEntity<User> request = RequestEntity.post(serviceURI()).body(userLogin)

        when:
        ResponseEntity<String> response = restTemplate.exchange(request, String)

        then:
        response.statusCode == HttpStatus.OK
        response.body == userRepository.findByUsername("brandon").getToken()

        cleanup:
        userRepository.delete(user)
    }

    def "invalid user login"() {
        setup:
        UserLogin userLogin = new UserLogin(username: "fakeuser", password: "password")
        RequestEntity<User> request = RequestEntity.post(serviceURI()).body(userLogin)

        when:
        ResponseEntity<String> response = restTemplate.exchange(request, String)

        then:
        response.statusCode == HttpStatus.UNAUTHORIZED
    }
}
