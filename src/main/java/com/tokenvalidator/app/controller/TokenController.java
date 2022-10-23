package com.tokenvalidator.app.controller;
import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tokenvalidator.app.model.Token;

@RestController
public class TokenController {

	@PostMapping(value="/validate")
	public String validate( @RequestBody Token token) {

		// altere esse metodo para atender as regras de definidas no readme.
		// você pode modificar o tipo de retorno, importar outros pacotes, criar mais classes.
		// existe uma pasta chamada Model para gerenciar o objeto Token

		// Imprimindo o input recebido
		//System.out.println(token.getValue());

		return "false";
	}
}




