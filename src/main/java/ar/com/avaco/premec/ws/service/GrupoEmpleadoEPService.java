package ar.com.avaco.premec.ws.service;

import java.util.List;

import ar.com.avaco.premec.dto.GrupoEmpleadoDTO;
import ar.com.avaco.premec.dto.UsuarioDTO;
import ar.com.avaco.ws.service.CRUDEPService;

public interface GrupoEmpleadoEPService extends CRUDEPService<Long, GrupoEmpleadoDTO> {

	List<UsuarioDTO> listUsuarios();

}
