package org.serratec.appsocial.repository;

import org.serratec.appsocial.model.Relacionamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelacionamentoRepository extends JpaRepository<Relacionamento, Relacionamento.RelacionamentoPK> {
}
