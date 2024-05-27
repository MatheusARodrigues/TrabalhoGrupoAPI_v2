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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
	@Operation(summary = "Lista todos os usuários", description = "A resposta da requisição irá listar todos os dados do usuário")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					content = {@Content(schema = @Schema(implementation = Usuario.class), mediaType = "apllication/json")}),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso")
	})
	public ResponseEntity<List<UsuarioDTO>> listar() {
		List<UsuarioDTO> usuariosDTO = usuarioRepository.findAll().stream().map(usuario -> new UsuarioDTO(usuario))
				.collect(Collectors.toList());
		return ResponseEntity.ok(usuariosDTO);
	}

	@GetMapping("/{id}") // Método para listar usuário por ID | localhost:8080/usuarios/id
	@Operation(summary = "Retorna um usuário", description = "A resposta é um objeto com os dados do usuario: id, nome, sobrenome, email, data de nascimento")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna um usuário"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
		if (usuarioOpt.isPresent()) {
			return ResponseEntity.ok(new UsuarioDTO(usuarioOpt.get()));
		}
		return ResponseEntity.notFound().build();
	}

	 @PostMapping // Método para criar um novo usuário
	    @ResponseStatus(HttpStatus.CREATED)
	 @Operation(summary = "Insere um usuário", description = "A resposta é um objeto com os dados cadastrados do usuário")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "201", description = "Usuário adicionado"),
				@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
				@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
				@ApiResponse(responseCode = "404", description = "Recurso não encontrado")
		})
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
	@Operation(summary = "Atualiza os dados de um usuário", description = "Atualiza o nome e a data de nascimento de um usuário")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Atualiza o nome e a data de nascimento"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<UsuarioDTO> atualizarDados(@PathVariable("id") Long id,
			@RequestBody UsuarioAtualizarDTO usuarioAtualizarDTO) {
		UsuarioDTO usuarioAtualizado = usuarioService.atualizarDados(id, usuarioAtualizarDTO);
		return ResponseEntity.ok(usuarioAtualizado);
	}

	@PutMapping("/{id}/atualizarSenha") //metodo para atualizar SENHA
	@Operation(summary = "Atualizar senha", description = "Atualiza a senha de um usuário")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Atualiza senha"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna da aplicação")
	})
	 public ResponseEntity<Void> atualizarSenha(
	            @PathVariable("id") Long id, 
	            @RequestBody UsuarioAtualizarSenhaDTO usuarioAtualizarSenhaDTO) {
	        usuarioService.atualizarSenha(id, usuarioAtualizarSenhaDTO);
	        return ResponseEntity.noContent().build();
	    }

	@DeleteMapping("/{id}") // Método para deletar um usuário do banco
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Remover usuário", description = "Remove um usuário")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna um usuário"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if (!usuarioRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		usuarioRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
