package com.wakanda.emc.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmcUserControllerTest {

    @Test
    public void testGetUsers() {
        /*ResponseEntity<EmcUser[]> response = restTemplate.exchange(
                "/users", HttpMethod.GET, null, new ParameterizedTypeReference<EmcUser[]>() {});
        assertEquals(200, response.getStatusCode().value());
        EmcUser[] users = response.getBody();
        assertEquals(2, users.length);
        assertEquals(1, users[0].getId());
        assertEquals("John", users[0].getUserName());
        assertEquals(2, users[1].getId());
        assertEquals("Jane", users[1].getUserName());*/
    }

}
