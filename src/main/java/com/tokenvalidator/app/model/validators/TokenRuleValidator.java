package com.tokenvalidator.app.model.validators;

import com.tokenvalidator.app.model.validators.exceptions.InvalidClaimValueException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * Generic interface to implement business rules on JWTs
 */
public abstract class TokenRuleValidator {
    abstract boolean validateRule(Jws<Claims> jws);

    public String getClaimValue(Jws<Claims> jws, String claimName) throws InvalidClaimValueException {
        String claimString;
        try {
            claimString = jws.getBody().get(claimName, String.class);
        } catch (Exception e) {
            // TODO: Catch the right exception
            throw new InvalidClaimValueException("Claim " + claimName + " does not contain a string value");
        }
        if(claimString == null) {
            throw new InvalidClaimValueException("Claim " + claimName + " is null");
        }
        return claimString;
    }
}
