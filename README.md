# Documentation

## Overview
The goal here is to provide a way to validate rules on JWTs. To do so, we have a TokenValidator class that holds
a list of TokenRuleValidator which is an interface.  
Each class that implements TokenRuleValidator is responsible for ensuring one rule. For instance, the class
TokenNumberOfClaimsRuleValidator verifies that the JWT has only the claims "Name", "Role" and "Seed".

Some points worth noticing:
- By using Spring's dependency injection, the moment we create a component class that implements TokenRuleValidator,
the class will be referenced in the list held by TokenValidator. This allows us to extend rules without having
to make changes on existing code.
- We assumed there will be just one set of rules for JWTs. That means every JWT must comply with all the
rules defined. However, if we'd have, let's say, two sets of rules, we could easily refactor the code to separate the rule
validators by, for instance, creating an interface for the TokenValidator class. Then we could implement multiple TokenValidators.
- We assumed that the values of the claims in a JWT are always Strings.

### Rules
1. The JWT must be valid.
2. The JWT must contain only three claims: "Name", "Role" and "Seed".
3. The claim "Name" must not contain digits.
4. The claim "Role" is restricted to "Admin", "Member", "External"
5. The claim "Seed" must be a prime number
6. The claim "Name" must have at most 256 characters.

Here an example of valid JWT payload:
```json
{
  "Role": "Admin",
  "Seed": "7841",
  "Name": "Toninho Araujo"
}
```

### Class Diagram
```mermaid
classDiagram
    direction RL
    TokenController --> TokenValidator: uses
    TokenValidator --> TokenRuleValidator: uses
    
    TokenClaimNameRuleValidator ..> TokenRuleValidator: implements
    TokenClaimRoleRuleValidator ..> TokenRuleValidator: implements
    TokenClaimSeedRuleValidator ..> TokenRuleValidator: implements
    TokenNumberOfClaimsRuleValidator ..> TokenRuleValidator: implements
    
    class TokenController {
        TokenValidator tokenValidator
        validate(Token token) bool
    }
    class TokenValidator {
        List~TokenRuleValidator~ tokenRuleValidators
        validateRules(Token token) bool
    }
    class TokenRuleValidator {
        <<interface>>
        validateRule(Jws~Claims~ jws) bool
    }
    class TokenNumberOfClaimsRuleValidator {
        validateRule(Jws~Claims~ jws) bool
    }
    class TokenClaimNameRuleValidator {
        validateRule(Jws~Claims~ jws) bool
    }
    class TokenClaimRoleRuleValidator {
        validateRule(Jws~Claims~ jws) bool
    }
    class TokenClaimSeedRuleValidator {
        validateRule(Jws~Claims~ jws) bool
    }
```
Diagram built using mermaid.js. More info on: https://mermaid-js.github.io/mermaid/#/

Some comments on the implementation:
* The validateRule method defined in the TokenRuleValidator interface receives a Jws<Claims> parameter which is
a decrypted version of the respective JWT. By doing so we only need to perform the decryption once. This is done
in the validateRules method of the TokenValidator.
* The validateRules method of the TokenValidator call the validateRule of each class implementing TokenRuleValidator.
Therefore, TokenValidator is decoupled from the implementation of the rules.
* The validate method of TokenController simply call the validateRules of TokenValidator.
* The secret used sign and verify JWT signatures is store in the file /resources/application.properties.
In a real scenario, this secret could be stored using GitHub secrets.

## How-to
To use the API, you have run the main method in the class AppApplication. Then, you'll have a server running locally
on the port 8080. There is just one endpoint in the API which is the `POST endpoint /validate`.
This endpoint receives a json in the following format. In this json, the key "value" contains a JWT.
```json
{
  "value": "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJUb25pbmhvIEFyYXVqbyJ9.rixqgrzp9HRPy_vSYrWo1-WgaA1DTAFV_-HlewtJBNA"
}
```
The response of `/validate` is a boolean value indicating whether the JWT passed complies with the rules or not.
```json
true
```

## References
* Parse JWTs: https://developer.okta.com/blog/2018/10/31/jwts-with-java; https://www.youtube.com/watch?v=O-sTJbeUagE
* Autowired collections: https://javabydeveloper.com/spring-injecting-collections/#4-5-injecting-components-as-list
* Test endpoints: https://reflectoring.io/spring-boot-test/
* Primality test: https://en.wikipedia.org/wiki/Primality_test