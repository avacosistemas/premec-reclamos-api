package ar.com.avaco.ws.rest.security.service.impl;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.com.avaco.arc.sec.domain.Cliente;
import ar.com.avaco.arc.sec.service.ClienteService;

@Service(value="jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {


    private ClienteService clienteService;

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        Cliente user = (Cliente) this.clienteService.loadUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        return user;
    }
    
	@Resource(name = "clienteService")
	public void setCService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
}