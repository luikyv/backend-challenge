package com.tokenvalidator.app.model.validators;

import com.tokenvalidator.app.model.validators.exceptions.InvalidClaimValueException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Component;

/**
 * Rule: The claims "Seed" must be a prime number
 */
@Component
public class TokenClaimSeedRuleValidator extends TokenRuleValidator {
    public boolean validateRule(Jws<Claims> jws) {
        String seedString;
        int seed;

        try {
            seedString = getClaimValue(jws, "Seed");
        } catch (InvalidClaimValueException e) {
            return false;
        }
        try {
            seed = Integer.parseInt(seedString);
        } catch (NumberFormatException e) {
            return false;
        }

        return isPrime(seed);
    }

    private boolean isPrime(int number) {
        if(number <= 1) { return false; }
        // We just need to verify division from 2 to sqrt(n) included
        for(int i=2; i<=Math.sqrt(number); i++) {
            if(number%i == 0) { return false; }
        }
        return true;
    }

}
