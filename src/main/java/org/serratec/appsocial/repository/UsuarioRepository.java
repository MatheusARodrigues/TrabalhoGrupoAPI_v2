package org.serratec.appsocial.repository;

import org.serratec.appsocial.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByEmail(String email);
	//comentario
}
