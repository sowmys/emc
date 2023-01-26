package com.wakanda.emc.controller;

import com.wakanda.emc.model.EmcUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmcUserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetUsers() {
        ResponseEntity<EmcUser[]> response = restTemplate.exchange(
                "/users", HttpMethod.GET, null, new ParameterizedTypeReference<EmcUser[]>() {});
        assertEquals(200, response.getStatusCode().value());
        /*EmcUser[] users = response.getBody();
        assertEquals(2, users.length);
        assertEquals(1, users[0].getId());
        assertEquals("John", users[0].getUserName());
        assertEquals(2, users[1].getId());
        assertEquals("Jane", users[1].getUserName());*/
    }

}
