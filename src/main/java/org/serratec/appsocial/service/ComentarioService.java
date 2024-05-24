package org.serratec.appsocial.service;

import java.util.List;

import org.serratec.appsocial.model.Comentario;
import org.serratec.appsocial.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {
	@Autowired
	private ComentarioRepository comentarioRepository;

	public List<Comentario> listarTodos() {
		return comentarioRepository.findAll();
	}

	public Comentario salvar(Comentario comentario) {
		return comentarioRepository.save(comentario);
	}

	// será que vamos precisar de algum construtor?
	//comentario
}
