/**
 * 
 */
package ar.com.avaco.premec.repository;

import ar.com.avaco.arc.core.component.bean.repository.NJRepository;
import ar.com.avaco.premec.domain.ProblemaMaquina;

/**
 * @author avaco
 *
 */
public interface ProblemaMaquinaRepository
		extends NJRepository<Long, ProblemaMaquina>, ProblemaMaquinaRepositoryCustom {

}
