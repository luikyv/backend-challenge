package com.tokenvalidator.app.model.validators;

import com.tokenvalidator.app.model.validators.exceptions.InvalidClaimValueException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Rule: the claim "Name" must neither contain digits nor be larger than 256 characters
 */
@Service
public class TokenClaimNameRuleValidator extends TokenRuleValidator {
    private final Pattern numberPattern;
    public TokenClaimNameRuleValidator() {
        // Instantiate pattern to identify digits
        numberPattern = Pattern.compile("\\d", Pattern.CASE_INSENSITIVE);
    }

    public boolean validateRule(Jws<Claims> jws) {
        String name;
        try {
            name = getClaimValue(jws, "Name");
        } catch (InvalidClaimValueException e) {
            return false;
        }
        if(name.length() > 256) { return false; }

        Matcher matcher = numberPattern.matcher(name);
        return !matcher.find(); // true if no number was found
    }
}
