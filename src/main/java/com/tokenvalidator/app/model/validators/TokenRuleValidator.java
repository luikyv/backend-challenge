package com.tokenvalidator.app.model.validators;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * Generic interface to implement business rules on JWTs
 */
public interface TokenRuleValidator {
    public boolean validateRule(Jws<Claims> jws);
}
