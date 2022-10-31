package com.tokenvalidator.app.model.validators;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTokenClaimRoleRuleValidator {
    TokenClaimRoleRuleValidator ruleValidator = new TokenClaimRoleRuleValidator();
    Key key = Keys.hmacShaKeyFor("secret00000000000000000000000000000".getBytes());

    @Test
    public void testNoRoleClaim() {
        String jwt = Jwts.builder()
                .claim("Seed", "72341")
                .claim("Name", "Maria")
                .signWith(key)
                .compact();
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt);

        assertFalse(ruleValidator.validateRule(jws));
    }

    @Test
    public void testNonExistentRole() {
        String jwt = Jwts.builder()
                .claim("Role", "NonExistet")
                .claim("Seed", "72341")
                .claim("Name", "Maria")
                .signWith(key)
                .compact();
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt);

        assertFalse(ruleValidator.validateRule(jws));
    }

    @Test
    public void testRoleSuccessful() {
        String jwt = Jwts.builder()
                .claim("Role", "Member")
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
