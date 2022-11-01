package com.tokenvalidator.app.model.validators.exceptions;

public class InvalidClaimValueException extends Exception {
    public InvalidClaimValueException(String errorMessage) {
        super(errorMessage);
    }
}
