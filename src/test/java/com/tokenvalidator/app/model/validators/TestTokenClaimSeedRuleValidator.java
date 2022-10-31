package com.tokenvalidator.app.model.validators;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTokenClaimSeedRuleValidator {
    TokenClaimSeedRuleValidator ruleValidator = new TokenClaimSeedRuleValidator();
    Key key = Keys.hmacShaKeyFor("secret00000000000000000000000000000".getBytes());

    @Test
    public void testSeedIsNotANumber() {
        String jwt = Jwts.builder()
                .claim("Seed", "test")
                .claim("Name", "Maria")
                .claim("Role", "External")
                .signWith(key)
                .compact();
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt);

        assertFalse(ruleValidator.validateRule(jws));
    }

    @Test
    public void testSeedIsNotAPrimeNumber() {
        String jwt = Jwts.builder()
                .claim("Seed", "240")
                .claim("Name", "Maria")
                .claim("Role", "External")
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
                .claim("Seed", "3779")
                .claim("Name", "Maria")
                .claim("Role", "External")
                .signWith(key)
                .compact();
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt);

        assertTrue(ruleValidator.validateRule(jws));
    }
}
