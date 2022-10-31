package com.tokenvalidator.app.model.validators;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTokenNumberOfClaimsRuleValidator {
    TokenNumberOfClaimsRuleValidator ruleValidator = new TokenNumberOfClaimsRuleValidator();
    Key key = Keys.hmacShaKeyFor("secret00000000000000000000000000000".getBytes());

    @Test
    public void testMoreThan3Claims() {
        String jwt = Jwts.builder()
                .claim("Role", "Admin")
                .claim("Seed", "72341")
                .claim("Name", "Maria")
                .claim("Test", "test")
                .signWith(key)
                .compact();
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt);

        assertFalse(ruleValidator.validateRule(jws));
    }

    @Test
    public void testClaimMissing() {
        String jwt = Jwts.builder()
                .claim("Role", "Admin")
                .claim("Seed", "72341")
                .claim("Test", "Maria")
                .signWith(key)
                .compact();
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt);

        assertFalse(ruleValidator.validateRule(jws));
    }

    @Test
    public void testLessThan3Claims() {
        String jwt = Jwts.builder()
                .claim("Role", "Admin")
                .claim("Seed", "72341")
                .signWith(key)
                .compact();
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt);

        assertFalse(ruleValidator.validateRule(jws));
    }

    @Test
    public void testSuccessful() {
        String jwt = Jwts.builder()
                .claim("Role", "Admin")
                .claim("Seed", "72341")
                .claim("Name", "Maria")
                .signWith(key)
                .compact();
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt);

        assertTrue(ruleValidator.validateRule(jws));
    }

}
