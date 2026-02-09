/**
 * 
 */
package ar.com.avaco.ws.rest.security.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.avaco.arc.sec.domain.Acceso;
import ar.com.avaco.arc.sec.domain.Perfil;
import ar.com.avaco.arc.sec.domain.Cliente;
import ar.com.avaco.arc.sec.service.ClienteService;
import ar.com.avaco.commons.exception.ErrorValidationException;
import ar.com.avaco.ws.rest.security.dto.Profile;
import ar.com.avaco.ws.rest.security.dto.UpdatePasswordDTO;
import ar.com.avaco.ws.rest.security.dto.User;
import ar.com.avaco.ws.rest.security.service.ProfileService;
import ar.com.avaco.ws.rest.security.service.UserService;
import ar.com.avaco.ws.service.AbstractConvertService;

/**
 * @author avaco
 *
 */
@Transactional
@Service("userService")
public class ClienteServiceImpl extends AbstractConvertService<User, Long, Cliente> implements UserService {

	@Resource(name = "profileService")
	private ProfileService profileService;

	private ClienteService clienteService;

	public User convertToDto(Cliente usuario) {
		Set<Profile> profiles = new HashSet<>();
		if (usuario.getAccesos() != null) {
			usuario.getAccesos().stream().forEach(e -> {
				profiles.add(profileService.convertToDto(e.getPerfil()));
			});
		}

		return new User(usuario.getId(), usuario.getUsername(), usuario.getNombre(), profiles,
				usuario.getEmail(), usuario.isEnabled());
	}

	@Override
	protected Cliente newEntity() {
		return new Cliente();
	}

	@Override
	public Cliente convertToEntity(Cliente entity, User dto) {
		entity.setEmail(dto.getEmail());
		entity.setNombre(dto.getName());
		entity.setUsername(dto.getUsername());
		entity.setBloqueado(!dto.isEnabled());
		if (dto.getProfiles() != null) {
			Set<Acceso> accesos = new HashSet<>();
			dto.getProfiles().stream().forEach(e -> {
				Acceso acceso = new Acceso();
				Perfil p = new Perfil();
				p.setId(e.getId());
				acceso.setPerfil(profileService.convertToEntity(p, e));
				acceso.setCliente(entity);
				accesos.add(acceso);
			});
			entity.setAccesos(accesos);
		}
		return entity;
	}

	@Resource(name = "clienteService")
	public void setClienteService(ClienteService clienteService) {
		this.service = clienteService;
		this.clienteService = clienteService;
	}

	@Override
	public User saveUser(User user) {
		return convertToDto(clienteService.save(convertToEntity(newEntity(), user)));
	}

	public ClienteService getService() {
		return (ClienteService) this.service;
	}

	@Override
	public void updatePassword(UpdatePasswordDTO resetPassword) {
		String username = resetPassword.getUsername();
		Cliente loadUserByUsername = (Cliente) getService().loadUserByUsername(username);
		getService().updatePassword(loadUserByUsername, resetPassword.getCurrentPassword(), resetPassword.getNewPassword());
	}

	@Override
	public void updateValidation(User user) throws ErrorValidationException {
		Map<String, String> errores = new HashMap<>();
		// Se valida que el nombre de usuario sea distinto de los existentes
		Cliente usuario = this.service.get(user.getId());

		if (!usuario.getUsername().equals(user.getUsername()) && getService().isUserExists(user.getUsername())) {
			errores.put("username", "Este nombre de usuario ya existe");
		}

		if (!usuario.getEmail().equals(user.getEmail()) && getService().isUserExistWithEmail(user.getEmail())) {
			errores.put("email", "Este email ya se encuentra registrado");
		}

		if (!errores.isEmpty()) {
			throw new ErrorValidationException("Se han encontrado errores de negocio en el objeto analizado", errores);
		}
	}

	@Override
	public User getByUsername(String username) {
		return convertToDto(getService().findByUsername(username));
	}

}
