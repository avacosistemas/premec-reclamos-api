/**
 * 
 */
package ar.com.avaco.premec.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.arc.core.component.bean.repository.NJBaseRepository;
import ar.com.avaco.premec.domain.TipoProblemaMaquina;

@Repository("tipoProblemaMaquinaRepository")
public class TipoProblemaMaquinaRepositoryImpl extends NJBaseRepository<Long, TipoProblemaMaquina>
		implements TipoProblemaMaquinaRepository {

	public TipoProblemaMaquinaRepositoryImpl(EntityManager entityManager) {
		super(TipoProblemaMaquina.class, entityManager);
	}
}
