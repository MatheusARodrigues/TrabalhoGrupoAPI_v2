package org.serratec.appsocial.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EmailException.class)
	protected ResponseEntity<Object> handleEmailException(EmailException ex) {
		return ResponseEntity.unprocessableEntity().body(ex.getMessage());
	}
	
	@ExceptionHandler(UsuarioNotFound.class)
	protected ResponseEntity<Void> handleEmailException(UsuarioNotFound ex) {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(SenhaException.class)
	protected ResponseEntity<Object> handleSenhaException(SenhaException ex) {
		return ResponseEntity.unprocessableEntity().body(ex.getMessage());
	}

}
