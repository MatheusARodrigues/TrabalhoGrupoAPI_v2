package org.serratec.appsocial.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.serratec.appsocial.model.Relacionamento;
import org.serratec.appsocial.model.Usuario;
import org.serratec.appsocial.repository.RelacionamentoRepository;
import org.serratec.appsocial.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relacionamentos")
public class RelacionamentoController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired

	private RelacionamentoRepository relacionamentoRepository;

	@PostMapping("/{id}/seguir/{idSeguir}") // Método para seguir | localhost:8080/relacionamentos/2/seguir/4
	@ResponseStatus(HttpStatus.CREATED)

	public Usuario createRelacionemento(@PathVariable Long id, @PathVariable Long idSeguir) {
		Relacionamento relacionamento = new Relacionamento();
		Optional<Usuario> usuarioSeguir = usuarioRepository.findById(idSeguir);
		Optional<Usuario> usuario = usuarioRepository.findById(id);

		relacionamento.getId().setSeguidor(usuario.get());
		relacionamento.getId().setSeguido(usuarioSeguir.get());

		relacionamento.setDataCriacao(LocalDate.now());

		usuario.get().getSeguindo().add(relacionamento);

		return usuarioRepository.save(usuario.get());

	}

	@DeleteMapping("/{idSeguir}/deixarDeSeguir/{id}") // Método para deixar de seguir | localhost:8080/relacionamentos/deixarDeSeguir/2/seguir/4
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRelacionamento(@PathVariable Long id, @PathVariable Long idSeguir) {
		Optional<Usuario> usuarioSeguir = usuarioRepository.findById(idSeguir);
		Optional<Usuario> usuario = usuarioRepository.findById(id);

		if (usuario.isPresent() && usuarioSeguir.isPresent()) {
			Usuario seguidor = usuario.get();
			Usuario seguido = usuarioSeguir.get();

			Optional<Relacionamento> relacionamentoOpt = seguidor.getSeguindo().stream()
					.filter(r -> r.getId().getSeguido().equals(seguido)).findFirst();

			if (relacionamentoOpt.isPresent()) {
				Relacionamento relacionamento = relacionamentoOpt.get();
				seguidor.getSeguindo().remove(relacionamento);
				relacionamentoRepository.delete(relacionamento);
				usuarioRepository.save(seguidor);
			}
		}
	}

}
