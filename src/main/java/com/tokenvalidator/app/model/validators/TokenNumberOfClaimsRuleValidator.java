package com.tokenvalidator.app.model.validators;

import com.tokenvalidator.app.model.validators.exceptions.InvalidClaimValueException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Component;

/**
 * Rule: there must be three claims: "Name", "Seed" and "Role"
 */
@Component
public class TokenNumberOfClaimsRuleValidator extends TokenRuleValidator {
    private enum AllowedClaims {
        Name, Role, Seed;
    }
    public boolean validateRule(Jws<Claims> jws) {
        Claims claims = jws.getBody();
        if(AllowedClaims.values().length != claims.size()) {
            return false;
        }

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
