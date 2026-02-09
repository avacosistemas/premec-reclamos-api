package ar.com.avaco.arc.sec.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ar.com.avaco.arc.core.component.bean.service.NJService;
import ar.com.avaco.arc.sec.domain.Cliente;

/**
 * the user service.
 * 
 * @author aogonzalez
 */
public interface ClienteService extends NJService<Long, Cliente> {

	void updatePassword(Cliente user, String password,String newPassword);

	boolean isUserExists(String username);
	
	void sendMissingPassword(String username);
	
	void sendMissingPasswordById(Long id);

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	void generateNewPassword(Cliente user);
	
	void update(Cliente usuario, List<Cliente> impersonables);
	
	Cliente findById(Long id);
	
	boolean isUserExistWithEmail(String email);
	
	Cliente findByUsername(String username);

	List<Cliente> getByIds(List<Long> lista);
	
}

