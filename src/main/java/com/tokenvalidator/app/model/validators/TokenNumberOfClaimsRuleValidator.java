package com.tokenvalidator.app.model.validators;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Component;

/**
 * Rule: there must be three claims: "Name", "Seed" and "Role"
 */
@Component
public class TokenNumberOfClaimsRuleValidator implements TokenRuleValidator {
    private enum AllowedClaims {
        Name, Role, Seed;
    }
    public boolean validateRule(Jws<Claims> jws) {
        Claims claims = jws.getBody();
        if(AllowedClaims.values().length != claims.size()) {
            return false;
        }

        for(AllowedClaims allowedClaim: AllowedClaims.values()) {
            String value = claims.get(allowedClaim.name(), String.class);
            if(value == null) {return false;}
        }

        return true;
    }
}
