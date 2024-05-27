package org.serratec.appsocial.repository;

import org.serratec.appsocial.model.Comentario;
import org.serratec.appsocial.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPostagem(Postagem postagem);
}
