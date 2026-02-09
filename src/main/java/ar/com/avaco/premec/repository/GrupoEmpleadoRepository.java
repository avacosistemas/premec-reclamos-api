/**
 * 
 */
package ar.com.avaco.premec.repository;

import ar.com.avaco.arc.core.component.bean.repository.NJRepository;
import ar.com.avaco.premec.domain.GrupoEmpleado;

/**
 * @author avaco
 *
 */
public interface GrupoEmpleadoRepository
		extends NJRepository<Long, GrupoEmpleado>, GrupoEmpleadoRepositoryCustom {

}
