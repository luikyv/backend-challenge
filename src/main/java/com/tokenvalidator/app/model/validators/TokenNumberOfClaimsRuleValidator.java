package com.tokenvalidator.app.model.validators;

import com.tokenvalidator.app.model.validators.exceptions.InvalidClaimValueException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Service;

/**
 * Rule: there must be three claims: "Name", "Seed" and "Role"
 */
@Service
public class TokenNumberOfClaimsRuleValidator extends TokenRuleValidator {
    private enum AllowedClaims {
        Name, Role, Seed;
    }
    public boolean validateRule(Jws<Claims> jws) {
        Claims claims = jws.getBody();
        // Verify that the number of claims is the expected
        if(AllowedClaims.values().length != claims.size()) {
            return false;
        }

        // Verify that each defined claims exists in jws
        for(AllowedClaims allowedClaim: AllowedClaims.values()) {
            try {
                getClaimValue(jws, allowedClaim.name());
            } catch (InvalidClaimValueException e) {
                return false;
            }
        }

        return true;
    }

}
