package com.tokenvalidator.app.unit.validators;

import com.tokenvalidator.app.model.validators.TokenClaimNameRuleValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.Key;

public class TestTokenClaimNameRuleValidator {

    TokenClaimNameRuleValidator ruleValidator = new TokenClaimNameRuleValidator();
    Key key = Keys.hmacShaKeyFor("secret00000000000000000000000000000".getBytes());

    @Test
    public void testNoNameClaim() {
        String jwt = Jwts.builder()
                .claim("Role", "External")
                .claim("Seed", "72341")
                .signWith(key)
                .compact();
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt);

        assertFalse(ruleValidator.validateRule(jws));
    }

    @Test
    public void testClaimIsNotString() {
        String jwt = Jwts.builder()
                .claim("Name", 1)
                .claim("Role", "External")
                .claim("Seed", "72341")
                .signWith(key)
                .compact();
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt);

        assertFalse(ruleValidator.validateRule(jws));
    }

    @Test
    public void testNameWithNumbers() {
        String jwt = Jwts.builder()
                .claim("Role", "External")
                .claim("Seed", "72341")
                .claim("Name", "M4ria Olivia")
                .signWith(key)
                .compact();
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt);

        assertFalse(ruleValidator.validateRule(jws));
    }

    @Test
    public void testNameLargerThen256Characters() {
        String jwt = Jwts.builder()
                .claim("Role", "External")
                .claim("Seed", "72341")
                // Generate a string with 257 characters
                .claim("Name", new String(new char[257]).replace("\0", "A"))
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
                .claim("Role", "External")
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
