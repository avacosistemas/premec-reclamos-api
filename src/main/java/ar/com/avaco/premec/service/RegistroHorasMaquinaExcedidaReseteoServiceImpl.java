/**
 * 
 */
package ar.com.avaco.premec.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.avaco.arc.core.component.bean.service.NJBaseService;
import ar.com.avaco.commons.repository.RegistroHorasMaquinaExcedidaReseteoRepository;
import ar.com.avaco.entities.RegistroHorasMaquinaExcedidaReseteo;

/**
 * @author avaco
 */

@Transactional
@Service("registroHorasMaquinaExcedidaReseteoService")
public class RegistroHorasMaquinaExcedidaReseteoServiceImpl extends NJBaseService<Long, RegistroHorasMaquinaExcedidaReseteo, RegistroHorasMaquinaExcedidaReseteoRepository>
		implements RegistroHorasMaquinaExcedidaReseteoService {

	@Resource(name = "registroHorasMaquinaExcedidaReseteoRepository")
	public void setRepository(RegistroHorasMaquinaExcedidaReseteoRepository repository) {
		this.repository = repository;
	}

}
