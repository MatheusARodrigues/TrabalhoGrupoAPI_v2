package org.serratec.appsocial.service;

import java.util.List;

import org.serratec.appsocial.model.Relacionamento;
import org.serratec.appsocial.repository.RelacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelacionamentoService {
    @Autowired
    private RelacionamentoRepository relacionamentoRepository;

    public List<Relacionamento> listarTodos() {
        return relacionamentoRepository.findAll();
    }

    public Relacionamento salvar(Relacionamento relacionamento) {
        return relacionamentoRepository.save(relacionamento);
    }

	// será que vamos precisar de algum construtor?
  //comentario
}
