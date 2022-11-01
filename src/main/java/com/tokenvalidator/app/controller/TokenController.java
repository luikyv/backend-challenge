package com.tokenvalidator.app.controller;
import com.tokenvalidator.app.model.TokenValidationResponse;
import com.tokenvalidator.app.model.validators.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tokenvalidator.app.model.Token;

@RestController
public class TokenController {

	@Autowired
	private TokenValidator tokenValidator;

	@PostMapping(value="/validate")
	@ResponseBody
	public TokenValidationResponse validate(@RequestBody Token token) {
		System.out.println("Token received: " + token.getValue());
		return new TokenValidationResponse(tokenValidator.validateRules(token));
	}
}




