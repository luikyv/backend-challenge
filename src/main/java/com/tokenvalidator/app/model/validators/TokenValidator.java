package com.tokenvalidator.app.model.validators;

import com.tokenvalidator.app.model.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.List;

/**
 * Define and apply business rules to validate tokens
 */
@Service
public class TokenValidator {
    // List containing all the implementations of TokenRuleValidator
    private List<TokenRuleValidator> tokenRuleValidators;
    private Key key;
    @Autowired
    public TokenValidator(List<TokenRuleValidator> tokenRuleValidators, @Value("${secret}") String secret) {
        this.tokenRuleValidators = tokenRuleValidators;
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public boolean validateRules(Token token) {
        Jws<Claims> jws;
        // Check if the token is valid
        try {
            jws = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token.getValue());
            //TODO: Catch the right exceptions
        } catch (Exception e) { return false; }

        // True if all the business rules are valid
        return tokenRuleValidators.stream().allMatch(
                (tokenRuleValidator) -> tokenRuleValidator.validateRule(jws)
        );
    }
}
