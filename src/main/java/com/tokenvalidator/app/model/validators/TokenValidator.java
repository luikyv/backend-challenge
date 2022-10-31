package com.tokenvalidator.app.model.validators;

import com.tokenvalidator.app.model.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

/**
 * Define and apply business rules to validate tokens
 */
@Component
public class TokenValidator {
    @Autowired
    // List containing all the implementations of TokenRuleValidator
    private List<TokenRuleValidator> tokenRuleValidators;
    private Key key;

    public TokenValidator(@Value("${secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public boolean validateRules(Token token) {
        Jws<Claims> jws;
        // Check if the token is valid
        try {
            jws = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token.getValue());
        } catch (io.jsonwebtoken.ExpiredJwtException |
                 io.jsonwebtoken.UnsupportedJwtException |
                 io.jsonwebtoken.MalformedJwtException |
                 io.jsonwebtoken.security.SignatureException e
        ) { return false; }

        // Check each one of the business rules
        for(TokenRuleValidator tokenRuleValidator: tokenRuleValidators) {
            // If any of the rules is not validated, return false
            if(!tokenRuleValidator.validateRule(jws)) {
                return false;
            }
        }
        return true;
    }
}
