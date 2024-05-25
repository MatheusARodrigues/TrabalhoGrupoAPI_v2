package org.serratec.appsocial.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RelacaoNaoExisteException extends RuntimeException {

    public RelacaoNaoExisteException(String message) {
        super(message);
    }
}
