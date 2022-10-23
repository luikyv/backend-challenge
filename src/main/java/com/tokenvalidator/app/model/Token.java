package com.tokenvalidator.app.model;

public class Token {
    
    String value;

    public Token( String value) {
        this.value = value;
    }
    public Token() {
      
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
