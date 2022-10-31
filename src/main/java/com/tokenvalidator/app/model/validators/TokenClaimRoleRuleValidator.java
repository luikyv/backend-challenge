package com.tokenvalidator.app.model.validators;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * Rule: the claim "Role" must be either "Admin", "Member" or "External"
 */
public class TokenClaimRoleRuleValidator implements TokenRuleValidator {

    private enum Roles {
        Admin, Member, External;
    }

    public boolean validateRule(Jws<Claims> jws) {
        String role = jws.getBody().get("Role", String.class);
        if(role == null) { return false; }

        // Check if role is equal to one of the pre-defined ones
        for (Roles r : Roles.values()) {
            if(role.equalsIgnoreCase(r.name())) {
                return true;
            }
        }
        return false;
    }
}
