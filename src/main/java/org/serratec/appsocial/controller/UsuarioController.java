package org.serratec.appsocial.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.appsocial.dto.UsuarioDTO;
import org.serratec.appsocial.dto.UsuarioInserirDTO;
import org.serratec.appsocial.model.Usuario;
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
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping // Método para Listar todos os Usuários | localhost:8080/usuarios
	public ResponseEntity<List<UsuarioDTO>> listar() {
		List<UsuarioDTO> usuariosDTO = usuarioRepository.findAll().stream()
				.map(usuario -> new UsuarioDTO(usuario))
				.collect(Collectors.toList());
		return ResponseEntity.ok(usuariosDTO);
	}

	@GetMapping("/{id}") // Método para listar usuário por ID | localhost:8080/usuarios/id
	public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
		if (usuarioOpt.isPresent()) {
			return ResponseEntity.ok(new UsuarioDTO(usuarioOpt.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping // Método para criar um novo usuário
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO createLivro(@Valid @RequestBody UsuarioInserirDTO usuarioInserirDTO) {
		Usuario usuario = new Usuario();
		usuario.setDataNascimento(usuarioInserirDTO.getDataNascimento());
		usuario.setEmail(usuarioInserirDTO.getEmail());
		usuario.setNome(usuarioInserirDTO.getNome());
		usuario.setSenha(usuarioInserirDTO.getSenha());
		usuario.setSobrenome(usuarioInserirDTO.getSobrenome());
		usuario = usuarioRepository.save(usuario);
		
		return new UsuarioDTO(usuario);
	}

	/*
	 * @PutMapping("/{id}") // Método para atualizar um usuário public
	 * ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long
	 * id, @Valid @RequestBody UsuarioDTO usuarioDTO) { if
	 * (!usuarioRepository.existsById(id)) { return
	 * ResponseEntity.notFound().build(); } Usuario usuario = usuarioDTO;
	 * usuario.setId(id); usuario = usuarioRepository.save(usuario); return
	 * ResponseEntity.ok(new UsuarioDTO(usuario)); }
	 */

	@DeleteMapping("/{id}") // Método para deletar um usuário do banco
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if (!usuarioRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		usuarioRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}

