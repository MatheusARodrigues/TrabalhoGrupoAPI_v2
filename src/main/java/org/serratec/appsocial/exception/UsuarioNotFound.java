package org.serratec.appsocial.exception;

public class UsuarioNotFound extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UsuarioNotFound(Long id) {
        super("Usuário com ID " + "não encontrado.");
    }
	
}
