/**
 * 
 */
package ar.com.avaco.ws.rest.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.avaco.arc.sec.domain.Acceso;
import ar.com.avaco.arc.sec.domain.Cliente;
import ar.com.avaco.arc.sec.domain.Perfil;
import ar.com.avaco.arc.sec.service.ClienteService;
import ar.com.avaco.arc.sec.service.PerfilService;
import ar.com.avaco.service.cliente.AccesoService;
import ar.com.avaco.ws.rest.security.dto.AccesoDTO;
import ar.com.avaco.ws.rest.security.service.AccesoEPService;
import ar.com.avaco.ws.service.CRUDEPBaseService;

/**
 * @author avaco
 *
 */
@Transactional
@Service("accesoEPService")
public class AccesoEPServiceImpl extends CRUDEPBaseService<Long, AccesoDTO, Acceso, AccesoService>
		implements AccesoEPService {

	private PerfilService perfilService;

	private ClienteService clienteService;

	@Override
	protected Acceso convertToEntity(AccesoDTO dto) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	protected AccesoDTO convertToDto(Acceso entity) {
		AccesoDTO dto = new AccesoDTO();
		dto.setId(entity.getPerfil().getId());
		dto.setIdUsuario(entity.getCliente().getId());
		dto.setPerfilNombre(entity.getPerfil().getNombre());
		return dto;
	}

	@Override
	@Resource(name = "accesoService")
	protected void setService(AccesoService service) {
		this.service = service;
	}

	@Resource(name = "perfilService")
	public void setPerfilService(PerfilService perfilService) {
		this.perfilService = perfilService;
	}

	@Resource(name = "clienteService")
	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@Override
	public List<AccesoDTO> list(Long usuarioId) {
		List<Acceso> list = this.service.list(usuarioId);
		List<AccesoDTO> dtos = new ArrayList<AccesoDTO>();
		list.forEach(a -> dtos.add(convertToDto(a)));
		return dtos;
	}

	@Override
	public void delete(Long id, Long idUsuario) {
		Cliente usuario = this.clienteService.get(idUsuario);
		Perfil perfil = this.perfilService.get(id);
		usuario.getAccesos().remove(perfil);
		this.clienteService.update(usuario);
	}

}
