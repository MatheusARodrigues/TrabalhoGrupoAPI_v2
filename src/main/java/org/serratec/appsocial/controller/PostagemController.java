package org.serratec.appsocial.controller;

import java.util.List;

import java.util.Optional;

import org.serratec.appsocial.model.Postagem;
import org.serratec.appsocial.repository.PostagemRepository;
import org.serratec.appsocial.repository.ComentarioRepository;
import org.serratec.appsocial.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@RestController
@RequestMapping ("/postagens")
public class PostagemController {
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;

	@GetMapping // Método para Listar todas as Postagens
	public ResponseEntity<List<Postagem>> listar() {
	    List<Postagem> postagens = postagemRepository.findAll(); // Busca todas as postagens

	    // Para cada postagem, busca os comentários associados e os adiciona à postagem
	    for (Postagem postagem : postagens) {
	        postagem.setComentarios(comentarioRepository.findByPostagem(postagem)); // Supondo que você tenha um método findByPostagem no seu ComentarioRepository
	    }

	    return ResponseEntity.ok(postagens); // Retorna a lista de postagens com os comentários associados
	}

	@PostMapping // Método para criar um novo usuário
	@ResponseStatus(HttpStatus.CREATED)
	public Postagem createLivro(@Valid @RequestBody Postagem postagem) {
		return postagemRepository.save(postagem);
	}

	@PutMapping("/{id}") // Método para atualizar um usuário
	public ResponseEntity<Postagem> atualizar(@PathVariable Long id, @Valid @RequestBody Postagem postagem) {
		if (!postagemRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		postagem.setId(id);
		postagem = postagemRepository.save(postagem);
		return ResponseEntity.ok(postagem);
	}

	@DeleteMapping("/{id}") // Método para deletar um usuário do banco
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if (!postagemRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		postagemRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	//comentario

}
