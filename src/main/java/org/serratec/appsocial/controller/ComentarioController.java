package org.serratec.appsocial.controller;

import java.util.List;
import java.util.Optional;

import org.serratec.appsocial.model.Comentario;
import org.serratec.appsocial.repository.ComentarioRepository;
import org.serratec.appsocial.repository.PostagemRepository;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping ("/comentarios")
public class ComentarioController {
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	@GetMapping
	public ResponseEntity<List<Comentario>> listar() {
		return ResponseEntity.ok(comentarioRepository.findAll());
	}

	@GetMapping("/{id}") 
	@Operation(summary = "Lista um comentário", description = "Lista um comentário de acordo com o seu Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista um comentário"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna da aplicação")
	}) 
	public ResponseEntity<Comentario> buscar(@PathVariable Long id) {
		Optional<Comentario> livroOpt = comentarioRepository.findById(id);
		if (livroOpt.isPresent()) {
			return ResponseEntity.ok(livroOpt.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping 
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Cria um comentário", description = "Cria um comentário em uma postagem")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cria um comentário"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna da aplicação")
	})
	public Comentario createLivro(@Valid @RequestBody Comentario comentario) {
		return comentarioRepository.save(comentario);
	}
	
	@PutMapping("/{id}") 
	@Operation(summary = "Atualiza um comentário", description = "Atualiza um comentário em uma postagem existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Atualiza um comentário"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna da aplicação")
	}) 
	public ResponseEntity<Comentario> atualizar(@PathVariable Long id, @Valid @RequestBody Comentario comentario) {
		if (!comentarioRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		comentario.setId(id);
		comentario = comentarioRepository.save(comentario);
		return ResponseEntity.ok(comentario);
	}

	@DeleteMapping("/{id}") 
	@Operation(summary = "Deleta um comentário", description = "Deleta um comentário em uma postagem existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Deleta um comentário"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna da aplicação")
	}) 
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if (!comentarioRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		comentarioRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	//comentario

}
