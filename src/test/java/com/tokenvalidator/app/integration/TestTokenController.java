package com.tokenvalidator.app.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokenvalidator.app.model.Token;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Key;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "secret=secret00000000000000000000000000000000000000")
@AutoConfigureMockMvc
public class TestTokenController {
    Key key = Keys.hmacShaKeyFor("secret00000000000000000000000000000000000000".getBytes());
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testInvalidTokens() throws Exception {
        String jwtInvalid = "test";
        String jwtMissingClaim = Jwts.builder()
                .claim("Role", "External")
                .claim("Seed", "72341")
                .signWith(key)
                .compact();
        String jwtSeedNotPrime = Jwts.builder()
                .claim("Name", "Pedro")
                .claim("Role", "External")
                .claim("Seed", "0")
                .signWith(key)
                .compact();

        mockMvc.perform(post("/validate", 42L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new Token(jwtInvalid))))
                .andExpect(status().isOk()).andExpect(content().string("false"));
        mockMvc.perform(post("/validate", 42L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new Token(jwtMissingClaim))))
                .andExpect(status().isOk()).andExpect(content().string("false"));
        mockMvc.perform(post("/validate", 42L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new Token(jwtSeedNotPrime))))
                .andExpect(status().isOk()).andExpect(content().string("false"));
    }

    @Test
    public void testSuccessful() throws Exception {
        String jwtCorrect1 = Jwts.builder()
                .claim("Name", "Ana")
                .claim("Role", "Admin")
                .claim("Seed", "2")
                .signWith(key)
                .compact();
        String jwtCorrect2 = Jwts.builder()
                .claim("Name", "Pedro")
                .claim("Role", "Member")
                .claim("Seed", "11")
                .signWith(key)
                .compact();

        mockMvc.perform(post("/validate", 42L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new Token(jwtCorrect1))))
                .andExpect(status().isOk()).andExpect(content().string("true"));
        mockMvc.perform(post("/validate", 42L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new Token(jwtCorrect2))))
                .andExpect(status().isOk()).andExpect(content().string("true"));
    }

}