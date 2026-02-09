/**
 * 
 */
package ar.com.avaco.commons.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.arc.core.component.bean.repository.NJBaseRepository;
import ar.com.avaco.entities.RegistroHorasMaquinaExcedidaReseteo;

@Repository("registroHorasMaquinaExcedidaReseteoRepository")
public class RegistroHorasMaquinaExcedidaReseteoRepositoryImpl extends NJBaseRepository<Long, RegistroHorasMaquinaExcedidaReseteo>
		implements RegistroHorasMaquinaExcedidaReseteoRepositoryCustom {

	public RegistroHorasMaquinaExcedidaReseteoRepositoryImpl(EntityManager entityManager) {
		super(RegistroHorasMaquinaExcedidaReseteo.class, entityManager);
	}
}
