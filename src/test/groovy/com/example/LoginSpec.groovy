package com.example
import com.example.models.User
import com.example.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

class LoginSpec extends RestIntegrationBase {
    String getBasePath() { "login" }

    @Autowired
    UserRepository userRepository

    def "remove all userse from database"() {
        given:
        userRepository.deleteAll()

        when:
        List<User> allUsers = userRepository.findAll()

        then:
        allUsers.isEmpty()
    }

    def "valid user login"() {
        given:
        User user = new User()
        user.setUsername("brandon")
        user.setPassword("password")
        userRepository.save(user)
        RequestEntity<User> request = RequestEntity.post(serviceURI()).body(user)

        when:
        ResponseEntity<User> response = restTemplate.exchange(request, User)

        then:
        response.statusCode == HttpStatus.OK
    }
}
