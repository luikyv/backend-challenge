package com.tokenvalidator.app.model.validators;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Component;

/**
 * Rule: The claims "Seed" must be a prime number
 */
@Component
public class TokenClaimSeedRuleValidator implements TokenRuleValidator {
    public boolean validateRule(Jws<Claims> jws) {
        String seedString = jws.getBody().get("Seed", String.class);
        int seed;
        try {
            seed = Integer.parseInt(seedString);
        } catch (NumberFormatException e) {
            return false;
        }

        return isPrime(seed);
    }

    private boolean isPrime(int number) {
        if(number <= 1) { return false; }
        for(int i=2; i<=Math.sqrt(number); i++) {
            if(number%i == 0) { return false; }
        }
        return true;
    }

}
