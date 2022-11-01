package com.tokenvalidator.app.unit.validators;

import com.tokenvalidator.app.model.validators.TokenValidator;
import com.tokenvalidator.app.model.Token;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "secret=secret00000000000000000000000000000")
public class TestTokenValidator {
    String secret = "secret00000000000000000000000000000";
    Key key = Keys.hmacShaKeyFor(secret.getBytes());
    @Autowired
    TokenValidator tokenValidator;

    @Test
    public void testInvalidToken() {
        Token token = new Token("invalid token");
        assertFalse(tokenValidator.validateRules(token));
    }

    @Test
    public void testSomeRules() {
        String jwtMissingClaim = Jwts.builder()
                .claim("Role", "External")
                .claim("Seed", "72341")
                .signWith(key)
                .compact();
        String jwtNameWithNumber = Jwts.builder()
                .claim("Name", "João123")
                .claim("Role", "External")
                .claim("Seed", "72341")
                .signWith(key)
                .compact();
        String jwtSeedNotPrime = Jwts.builder()
                .claim("Name", "João")
                .claim("Role", "External")
                .claim("Seed", "30")
                .signWith(key)
                .compact();
        String jwtNameNotString = Jwts.builder()
                .claim("Name", 1234)
                .claim("Role", "External")
                .claim("Seed", "30")
                .signWith(key)
                .compact();

        assertFalse(tokenValidator.validateRules(new Token(jwtMissingClaim)));
        assertFalse(tokenValidator.validateRules(new Token(jwtNameWithNumber)));
        assertFalse(tokenValidator.validateRules(new Token(jwtSeedNotPrime)));
        assertFalse(tokenValidator.validateRules(new Token(jwtNameNotString)));
    }

    @Test
    public void testSuccessful() {
        String jwt = Jwts.builder()
                .claim("Name", "João")
                .claim("Role", "External")
                .claim("Seed", "72341")
                .signWith(key)
                .compact();
        Token token = new Token(jwt);
        assertTrue(tokenValidator.validateRules(token));
    }
}
