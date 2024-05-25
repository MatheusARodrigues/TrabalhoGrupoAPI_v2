package org.serratec.appsocial.service;

import java.util.List;
import java.util.Optional;

import org.serratec.appsocial.dto.UsuarioAtualizarDTO;
import org.serratec.appsocial.dto.UsuarioAtualizarSenhaDTO;
import org.serratec.appsocial.dto.UsuarioDTO;
import org.serratec.appsocial.exception.SenhaException;
import org.serratec.appsocial.exception.UsuarioNotFound;
import org.serratec.appsocial.model.Usuario;
import org.serratec.appsocial.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder; // fiz a injecao para atualizarSenha

	public List<Usuario> listarTodos() {
		return usuarioRepository.findAll();
	}

	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

//	public Usuario findById(Long id) {
//		return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNotFound(id));
//	}

	public Usuario findById(Long id) throws UsuarioNotFound {
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
		if (usuarioOpt.isEmpty()) {
			throw new UsuarioNotFound(id);
		}
		return usuarioOpt.get();
	}

	public UsuarioDTO atualizarDados(Long id, UsuarioAtualizarDTO usuarioAtualizarDTO) {
		Usuario usuario = findById(id);
		usuario.setNome(usuarioAtualizarDTO.getNome());
		usuario.setDataNascimento(usuarioAtualizarDTO.getDataNascimento());
		usuario = usuarioRepository.save(usuario);
		return new UsuarioDTO(usuario); // Converter para DTO
	}

	public void atualizarSenha(Long id, UsuarioAtualizarSenhaDTO usuarioAtualizarSenhaDTO) {
		Usuario usuario = findById(id);

		if (usuarioAtualizarSenhaDTO.getSenhaAntiga().equalsIgnoreCase(usuarioAtualizarSenhaDTO.getNovaSenha())) {
			throw new SenhaException("Senha antiga e Senha Nova são iguais!");
		}

		else if (passwordEncoder.matches(usuarioAtualizarSenhaDTO.getSenhaAntiga(), usuario.getSenha())) {
			usuario.setSenha(passwordEncoder.encode(usuarioAtualizarSenhaDTO.getNovaSenha()));
			usuarioRepository.save(usuario);
		}

	}

}
