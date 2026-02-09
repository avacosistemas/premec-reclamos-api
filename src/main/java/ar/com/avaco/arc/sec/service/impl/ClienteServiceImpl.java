package ar.com.avaco.arc.sec.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.avaco.arc.core.component.bean.service.NJBaseService;
import ar.com.avaco.arc.core.service.MailSenderSMTPService;
import ar.com.avaco.arc.sec.domain.Cliente;
import ar.com.avaco.arc.sec.exception.NuclearJSecurityException;
import ar.com.avaco.arc.sec.repository.ClienteRepository;
import ar.com.avaco.arc.sec.service.ClienteService;
import ar.com.avaco.commons.exception.ErrorValidationException;

@Transactional
@Service("clienteService")
public class ClienteServiceImpl extends NJBaseService<Long, Cliente, ClienteRepository>
		implements ClienteService, UserDetailsService {

	private static final Integer INICIO_REINTENTOS_LOGIN = 0;
	private static final String USER_NEWPASSWORD_EQUALS_CURRENT = "user.newpassword.currentpassword.equals";
	private static final String USER_CURRENT_PASSWORD_INVALID = "user.currentpassword.invalid";

	private Logger logger = Logger.getLogger(this.getClass());

	@Value("${email.from}")
	private String from;

	@Value("${email.cc}")
	private String cc;

	/**
	 * The Password Encoder
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailSenderSMTPService mailSenderSMTPService;

	@Override
	public void updatePassword(Cliente user, String password, String newPassword) {
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new NuclearJSecurityException(USER_CURRENT_PASSWORD_INVALID);
		} else if (password.equals(newPassword)) {
			throw new NuclearJSecurityException(USER_NEWPASSWORD_EQUALS_CURRENT);
		}
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setFechaAltaPassword(Calendar.getInstance().getTime());
		user.setRequiereCambioPassword(Boolean.FALSE);
		getRepository().save(user);
	}

	@Override
	public Cliente save(Cliente cliente) throws NuclearJSecurityException {
		validarCliente(cliente);

		// Por default el cliente se da de desbloqueado.
		cliente.setBloqueado(false);

		// La cantidad de intentos de login es cero.
		cliente.setIntentosFallidosLogin(INICIO_REINTENTOS_LOGIN);

		// Por mas que luego se reenviara el password, se requerira cambio de
		// password.
		cliente.setRequiereCambioPassword(false);

		String tmppass = generarPasswordAleatorio();
		cliente.setPassword(passwordEncoder.encode(tmppass));

		cliente = getRepository().save(cliente);

		if (mailSenderSMTPService != null) {
			notifyPasswordNewUser(cliente, tmppass);
		}
		return cliente;
	}

	private void validarCliente(Cliente cliente) throws NuclearJSecurityException {
		Map<String, String> errors = new HashMap<String, String>();
		String username = cliente.getUsername();
		Cliente userByUsername = getRepository().findByUsername(username);
		if (userByUsername != null) {
			errors.put("username", "Ya existe un cliente registrado con ese Nombre de Cliente.");
		}
		String mail = cliente.getEmail();
		Cliente userByMail = getRepository().findByEmail(mail);
		if (userByMail != null) {
			errors.put("email", "Ya existe un cliente registrado con ese Email.");
		}
		if (!errors.isEmpty()) {
			throw new ErrorValidationException("Se han encontrado los siguientes errores", errors);
		}

	}

	/**
	 * Genera un string en forma aleatoria para ser usado de password.
	 * 
	 * @return un string de 8 caracteres.
	 */
	private String generarPasswordAleatorio() {
		String generateKey = KeyGenerators.string().generateKey();
		return generateKey;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return findByUsername(username);
	}

	@Override
	public boolean isUserExists(String username) {
		return getRepository().findByUsername(username) != null;
	}

	@Override
	public void sendMissingPassword(String username) {
		Cliente user = getRepository().findByUsername(username);
		if (user == null) {
			user = getRepository().findByEmail(username);
		}
		if (user != null) {
			generateNewPassword(user);
		} else {
			throw new UsernameNotFoundException("No se ha podido identificar al usuario");
		}
	}

	@Override
	public void sendMissingPasswordById(Long id) {
		Cliente user = getRepository().findOne(id);

		if (user != null) {
			generateNewPassword(user);
		} else {
			throw new UsernameNotFoundException("No se ha podido identificar al usuario");
		}
	}

	@Override
	public void generateNewPassword(Cliente user) {
		user.addPasswordToHistoric(user.getPassword());
		String tmppass = generarPasswordAleatorio();
		String enctmppass = passwordEncoder.encode(tmppass);
		user.setPassword(enctmppass);
		user.setIntentosFallidosLogin(0);
		user.setRequiereCambioPassword(Boolean.TRUE);
		update(user);
		if (mailSenderSMTPService != null) {
			notifyPassword(user, tmppass);
		}
	}

	private void notifyPasswordNewUser(Cliente user, String tmpass) {
		String subject = "PremecMobile";
		StringBuilder msg = new StringBuilder("¡Bienvenido ");
		msg.append(user.getNombre());
		msg.append(" a PremecMobile! <br>");
		msg.append("Se le ha asignado una contraseña a su usuario ");
		msg.append(user.getUsername());
		msg.append(".<br>");
		msg.append("La contraseña asignada es: <strong>");
		msg.append(tmpass);
		msg.append(".<br>");
		mailSenderSMTPService.sendMail(from, user.getEmail(), cc, subject.toString(), msg.toString(), null);
	}

	private void notifyPassword(Cliente user, String tmppas) {
		String subject = " Admin";
		StringBuilder msg = new StringBuilder("Estimado ");
		msg.append(user.getNombre());
		msg.append("<br>Se le ha asignado una contraseña temporal a su usuario ");
		msg.append(user.getUsername());
		msg.append(".<br>");
		msg.append("La contraseña asignada es: <strong>");
		msg.append(tmppas);
		msg.append(".<br>");
		mailSenderSMTPService.sendMail(from, user.getEmail(), cc, subject.toString(), msg.toString(), null);
	}

	@Override
	public void update(Cliente cliente, List<Cliente> impersonables) {
		cliente = getRepository().findOne(cliente.getId());
		getRepository().save(cliente);
	}

	public Cliente findById(Long id) {
		return getRepository().findOne(id);
	}

	public void setMailSenderSMTPService(MailSenderSMTPService mailSenderSMTPService) {
		this.mailSenderSMTPService = mailSenderSMTPService;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Resource(name = "clienteRepository")
	public void setClienteRepository(ClienteRepository clienteRepository) {
		this.repository = clienteRepository;
	}

	@Override
	public boolean isUserExistWithEmail(String email) {
		return getRepository().isUserExistWithEmail(email);
	}

	@Override
	public Cliente findByUsername(String username) {
		Cliente user = getRepository().findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Usuario " + username + "not found");
		}
		return user;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	@Override
	public List<Cliente> getByIds(List<Long> lista) {
		return this.repository.findByIdIn(lista);
	}

}