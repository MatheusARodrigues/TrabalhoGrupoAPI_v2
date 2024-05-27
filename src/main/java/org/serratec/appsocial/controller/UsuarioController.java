package org.serratec.appsocial.controller;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.appsocial.dto.UsuarioAtualizarDTO;
import org.serratec.appsocial.dto.UsuarioAtualizarSenhaDTO;
import org.serratec.appsocial.dto.UsuarioDTO;
import org.serratec.appsocial.dto.UsuarioInserirDTO;
import org.serratec.appsocial.exception.EmailException;
import org.serratec.appsocial.exception.SenhaException;
import org.serratec.appsocial.model.Usuario;
import org.serratec.appsocial.repository.UsuarioRepository;
import org.serratec.appsocial.service.UsuarioService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;
	

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

	@GetMapping // Método para Listar todos os Usuários | localhost:8080/usuarios
	public ResponseEntity<List<UsuarioDTO>> listar() {
		List<UsuarioDTO> usuariosDTO = usuarioRepository.findAll().stream().map(usuario -> new UsuarioDTO(usuario))
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

	        if (!usuarioInserirDTO.getSenha().equalsIgnoreCase(usuarioInserirDTO.getConfirmaSenha())) {
	            throw new SenhaException("Senha e Confirma Senha não são iguais");
	        }
	        Usuario usuarioBd = usuarioRepository.findByEmail(usuarioInserirDTO.getEmail());
	        if (usuarioBd != null) {
	            throw new EmailException("Email já existente");
	        }

	        Usuario usuario = new Usuario();
	        usuario.setDataNascimento(usuarioInserirDTO.getDataNascimento());
	        usuario.setEmail(usuarioInserirDTO.getEmail());
	        usuario.setNome(usuarioInserirDTO.getNome());
	        usuario.setSobrenome(usuarioInserirDTO.getSobrenome());

	        // Criptografar a senha antes de salvar
	        String senhaCriptografada = passwordEncoder.encode(usuarioInserirDTO.getSenha());
	        usuario.setSenha(senhaCriptografada);

	        usuario = usuarioRepository.save(usuario);

	        return new UsuarioDTO(usuario);
	    }


	@PutMapping("/{id}/atualizarDados") //metodo para atualizar NOME e DATANASCIMENTO
	public ResponseEntity<UsuarioDTO> atualizarDados(@PathVariable Long id,
			@RequestBody UsuarioAtualizarDTO usuarioAtualizarDTO) {
		UsuarioDTO usuarioAtualizado = usuarioService.atualizarDados(id, usuarioAtualizarDTO);
		return ResponseEntity.ok(usuarioAtualizado);
	}

	@PutMapping("/{id}/atualizarSenha") //metodo para atualizar SENHA
	 public ResponseEntity<Void> atualizarSenha(
	            @PathVariable Long id, 
	            @RequestBody UsuarioAtualizarSenhaDTO usuarioAtualizarSenhaDTO) {
	        usuarioService.atualizarSenha(id, usuarioAtualizarSenhaDTO);
	        return ResponseEntity.noContent().build();
	    }

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
