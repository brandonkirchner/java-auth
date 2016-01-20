package com.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class AuthControllerIT {

    @Value("${local.server.port}")
    private int port;

    private URL base;
    private RestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
        template = new TestRestTemplate();
    }

    @Test
    public void getRoot() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/", String.class);
        assertThat(response.getBody(), equalTo("Ba-Bam!"));
    }

    @Test
    public void getLogin() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/login", String.class);
        assertThat(response.getBody(), equalTo("You're logged in!"));
    }

    @Test
    public void getLogout() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/logout", String.class);
        assertThat(response.getBody(), equalTo("You're logged out!"));
    }
}