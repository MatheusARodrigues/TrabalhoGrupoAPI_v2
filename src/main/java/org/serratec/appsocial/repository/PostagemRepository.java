package org.serratec.appsocial.repository;

import org.serratec.appsocial.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostagemRepository extends JpaRepository<Postagem, Long> {
	
	//comentario
}

