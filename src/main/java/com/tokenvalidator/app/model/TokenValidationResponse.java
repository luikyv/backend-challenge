package com.tokenvalidator.app.model;

public class TokenValidationResponse {

    private boolean validToken;

    public TokenValidationResponse(boolean validToken) {
        this.validToken = validToken;
    }

    public boolean getValidToken() {
        return validToken;
    }

    public void setValidToken(boolean validToken) {
        this.validToken = validToken;
    }
}
