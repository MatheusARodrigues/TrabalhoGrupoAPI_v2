package org.serratec.appsocial.service;

import java.util.List;

import org.serratec.appsocial.model.Postagem;
import org.serratec.appsocial.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostagemService {
    @Autowired
    private PostagemRepository postagemRepository;

    public List<Postagem> listarTodas() {
        return postagemRepository.findAll();
    }

    public Postagem salvar(Postagem postagem) {
        return postagemRepository.save(postagem);
    }

	// será que vamos precisar de algum construtor?
  //comentario
}
