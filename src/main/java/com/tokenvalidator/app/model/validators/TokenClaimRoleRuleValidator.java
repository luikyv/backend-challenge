package com.tokenvalidator.app.model.validators;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * Rule: the claim "Role" must be either "Admin", "Member" or "External"
 */
public class TokenClaimRoleRuleValidator implements TokenRuleValidator {

    private enum Roles {
        ADMIN, MEMBER, EXTERNAL;

        public String getName() {
            switch (this) {
                case ADMIN:
                    return "Admin";
                case MEMBER:
                    return "Member";
                case EXTERNAL:
                    return "External";
                default:
                    return null;
            }
        }
    }

    public boolean validateRule(Jws<Claims> jws) {
        String role = jws.getBody().get("Role", String.class);
        if(role == null) { return false; }

        // Check if role is equal to one of the pre-defined ones
        for (Roles r : Roles.values()) {
            if(role.equals(r.getName())) {
                return true;
            }
        }
        return false;
    }
}
