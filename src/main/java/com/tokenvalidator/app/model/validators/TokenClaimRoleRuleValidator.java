package com.tokenvalidator.app.model.validators;

import com.tokenvalidator.app.model.validators.exceptions.InvalidClaimValueException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Rule: the claim "Role" must be either "Admin", "Member" or "External"
 */
@Service
public class TokenClaimRoleRuleValidator extends TokenRuleValidator {

    private enum Roles {
        Admin, Member, External;
    }

    public boolean validateRule(Jws<Claims> jws) {
        String role;
        try {
            role = getClaimValue(jws, "Role");
        } catch (InvalidClaimValueException e) {
            return false;
        }

        // Check if role is equal to one of the pre-defined ones
        return Arrays.stream(Roles.values()).anyMatch(
                (r) -> role.equalsIgnoreCase(r.name())
        );
    }
}
