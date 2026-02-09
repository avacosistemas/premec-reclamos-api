/**
 * 
 */
package ar.com.avaco.premec.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.avaco.arc.core.component.bean.service.NJBaseService;
import ar.com.avaco.arc.core.service.MailSenderSMTPService;
import ar.com.avaco.commons.exception.ErrorValidationException;
import ar.com.avaco.factory.SapBusinessException;
import ar.com.avaco.premec.domain.Cliente;
import ar.com.avaco.premec.repository.ClienteRepository;
import ar.com.avaco.premec.sap.dto.BusinessPartnerResponseDTO;
import ar.com.avaco.premec.sap.service.BusinessPartnerService;

/**
 * @author avaco
 */

@Transactional
@Service("clienteService")
public class ClienteServiceImpl extends NJBaseService<Long, Cliente, ClienteRepository> implements ClienteService {

	@Value("${mail.password.from}")
	private String from;

	@Value("${mail.password.cc}")
	private String cc;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailSenderSMTPService mailSenderSMTPService;

	@Autowired
	private BusinessPartnerService bpservice;

	@Override
	public Cliente save(Cliente cliente) {
		try {
			BusinessPartnerResponseDTO bddto = bpservice.getByCUIT(cliente.getUsername());
			cliente.setNombre(bddto.getCardName());
			cliente.setBloqueado(false);
			cliente.setIntentosFallidosLogin(0);
			cliente.setRequiereCambioPassword(false);
			cliente.setEmail(bddto.getEmailAddress());
			String tmppass = KeyGenerators.string().generateKey();
			cliente.setPassword(passwordEncoder.encode(tmppass));
			cliente = getRepository().save(cliente);
			if (mailSenderSMTPService != null) {
				notificarPasswordNuevoCliente(cliente, tmppass);
			}
			return cliente;
		} catch (SapBusinessException e) {
			if (e.getSapCode().equals(SapBusinessException.ERRROR_NO_MATCHING_RECORDS_FOUND)) {
				throw new ErrorValidationException("No existe un cliente con CUIT " + cliente.getUsername(), null);
			} else {
				throw new ErrorValidationException(e.getSapMessage(), null);
			}
		}
	}

	private void notificarPasswordNuevoCliente(Cliente cliente, String tmpass) {
		String subject = "Premec Reclamos - Bievenida";
		StringBuilder msg = new StringBuilder("¡Bienvenido ");
		msg.append(cliente.getNombre());
		msg.append(" al Sistema de Reclamos de Premec! <br>");
		msg.append("Se le ha asignado una contraseña a su usuario ");
		msg.append(cliente.getUsername());
		msg.append(".<br>");
		msg.append("La contraseña asignada es: <strong>");
		msg.append(tmpass);
		msg.append("<br>");
		msg.append("Para acceder ingrese en el siguiente link <a href='{urlReclamos}'>Sistema de Reclamos</a>");
		String email = "alosgonzalez@gmail.com"; //cliente.getEmail();
		mailSenderSMTPService.sendMail(from, email, cc, subject.toString(), msg.toString(), null);
	}

	private void notificarPassword(Cliente cliente, String tmppas) {
		String subject = " Premec Reclamos - Reseteo de contraseña";
		StringBuilder msg = new StringBuilder("Estimado ");
		msg.append(cliente.getNombre());
		msg.append("<br>Se le ha asignado una contraseña temporal a su usuario ");
		msg.append(cliente.getUsername());
		msg.append(".<br>");
		msg.append("La contraseña asignada es: <strong>");
		msg.append(tmppas);
		msg.append("<br> Úsela en el siguiente login por única vez y luego deberá cambiarla por una propia.");
		String email = "alosgonzalez@gmail.com"; //cliente.getEmail();
		mailSenderSMTPService.sendMail(from, email, cc, subject.toString(), msg.toString(), null);
	}

	@Resource(name = "clienteRepository")
	public void setRepository(ClienteRepository clienteRepository) {
		repository = clienteRepository;
	}
}
