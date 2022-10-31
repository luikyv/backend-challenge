package com.tokenvalidator.app.model.validators;

import com.tokenvalidator.app.model.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Rule: the claim "Name" must neither contain digits nor be larger than 256 characters
 */
public class TokenClaimNameRuleValidator implements TokenRuleValidator {
    private Pattern numberPattern;
    public TokenClaimNameRuleValidator() {
        // Instantiate pattern to identify digits
        numberPattern = Pattern.compile("\\d", Pattern.CASE_INSENSITIVE);
    }

    public boolean validateRule(Jws<Claims> jws) {
        String name = jws.getBody().get("Name", String.class);
        if(name == null || name.length() > 256) { return false; }

        Matcher matcher = numberPattern.matcher(name);
        return !matcher.find(); // true if no number was found
    }
}