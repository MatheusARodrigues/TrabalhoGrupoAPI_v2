package org.serratec.appsocial.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.serratec.appsocial.exception.ResourceNotFoundException;
import org.serratec.appsocial.model.Relacionamento;
import org.serratec.appsocial.model.Usuario;
import org.serratec.appsocial.repository.RelacionamentoRepository;
import org.serratec.appsocial.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/relacionamentos")
public class RelacionamentoController {
	private static final Logger logger = LoggerFactory.getLogger(RelacionamentoController.class);

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private RelacionamentoRepository relacionamentoRepository;

	@PostMapping("/{id}/seguir/{idSeguir}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> createRelacionamento(@PathVariable Long id, @PathVariable Long idSeguir) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
		Optional<Usuario> usuarioSeguirOpt = usuarioRepository.findById(idSeguir);

		if (usuarioOpt.isPresent() && usuarioSeguirOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();
			Usuario usuarioSeguir = usuarioSeguirOpt.get();

			Relacionamento relacionamento = new Relacionamento();
			Relacionamento.RelacionamentoPK relacionamentoPK = new Relacionamento.RelacionamentoPK();
			relacionamentoPK.setSeguidor(usuario);
			relacionamentoPK.setSeguido(usuarioSeguir);
			relacionamento.setId(relacionamentoPK);
			relacionamento.setDataCriacao(LocalDate.now());

			relacionamentoRepository.save(relacionamento);

			return ResponseEntity.status(HttpStatus.CREATED).body("Relacionamento criado com sucesso.");
		} else {
			throw new ResourceNotFoundException("Usuário não encontrado.");
		}
	}

	@DeleteMapping("/{id}/deixarDeSeguir/{idSeguir}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRelacionamento(@PathVariable Long id, @PathVariable Long idSeguir) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        Optional<Usuario> usuarioSeguirOpt = usuarioRepository.findById(idSeguir);

        if (usuarioOpt.isPresent() && usuarioSeguirOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Usuario usuarioSeguir = usuarioSeguirOpt.get();

            Relacionamento.RelacionamentoPK relacionamentoPK = new Relacionamento.RelacionamentoPK();
            relacionamentoPK.setSeguidor(usuario);
            relacionamentoPK.setSeguido(usuarioSeguir);

            logger.debug("Procurando relacionamento com PK: " + relacionamentoPK);
            Optional<Relacionamento> relacionamentoOpt = relacionamentoRepository.findById(relacionamentoPK);

            if (relacionamentoOpt.isPresent()) {
                logger.debug("Relacionamento encontrado: " + relacionamentoOpt.get());
                relacionamentoRepository.delete(relacionamentoOpt.get());
                logger.debug("Relacionamento deletado com sucesso.");
            } else {
                logger.debug("Relacionamento não encontrado.");
                throw new ResourceNotFoundException("Relacionamento não encontrado.");
            }
        } else {
            logger.debug("Usuário ou usuário a seguir não encontrado.");
            throw new ResourceNotFoundException("Usuário não encontrado.");
        }
    }

}
