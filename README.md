# Documentation

## Class Diagram
```mermaid
classDiagram
    direction RL
    TokenController --> TokenValidator: uses
    TokenValidator --> TokenRuleValidator: uses
    
    TokenNameClaimRuleValidator ..> TokenRuleValidator: implements
    TokenRoleClaimRuleValidator ..> TokenRuleValidator: implements
    TokenSeedClaimRuleValidator ..> TokenRuleValidator: implements
    TokenNumberOfClaimsRuleValidator ..> TokenRuleValidator: implements
    
    
    class TokenController {
        TokenValidator tokenValidator
        validate(Token token) bool
    }
    class TokenValidator {
        List~TokenRuleValidator~ tokenRuleValidators
        defineRuleValidators() void
        validateRules(Token token) bool
    }
    class TokenRuleValidator {
        <<interface>>
        validateRule(Token token) bool
    }
    class TokenNumberOfClaimsRuleValidator {
        validateRule(Token token) bool
    }
    class TokenNameClaimRuleValidator {
        validateRule(Token token) bool
    }
    class TokenRoleClaimRuleValidator {
        validateRule(Token token) bool
    }
    class TokenSeedClaimRuleValidator {
        validateRule(Token token) bool
    }
```
Diagram built using mermaid.js. More info on: https://mermaid-js.github.io/mermaid/#/

## References
* Parse JWTs: https://developer.okta.com/blog/2018/10/31/jwts-with-java