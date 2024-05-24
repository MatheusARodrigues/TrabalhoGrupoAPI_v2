package org.serratec.appsocial.controller;

import java.util.List;
import java.util.Optional;

import org.serratec.appsocial.model.Comentario;
import org.serratec.appsocial.repository.ComentarioRepository;
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
@RequestMapping ("/comentarios")
public class ComentarioController {
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@GetMapping 
	public ResponseEntity<List<Comentario>> listar() {
		return ResponseEntity.ok(comentarioRepository.findAll());
	}

	@GetMapping("/{id}") 
	public ResponseEntity<Comentario> buscar(@PathVariable Long id) {
		Optional<Comentario> livroOpt = comentarioRepository.findById(id);
		if (livroOpt.isPresent()) {
			return ResponseEntity.ok(livroOpt.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping 
	@ResponseStatus(HttpStatus.CREATED)
	public Comentario createLivro(@Valid @RequestBody Comentario comentario) {
		return comentarioRepository.save(comentario);
	}

	@PutMapping("/{id}") 
	public ResponseEntity<Comentario> atualizar(@PathVariable Long id, @Valid @RequestBody Comentario comentario) {
		if (!comentarioRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		comentario.setId(id);
		comentario = comentarioRepository.save(comentario);
		return ResponseEntity.ok(comentario);
	}

	@DeleteMapping("/{id}") 
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if (!comentarioRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		comentarioRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	//comentario

}
