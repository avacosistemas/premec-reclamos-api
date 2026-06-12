package ar.com.avaco.ws.rest.security.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.avaco.arc.sec.domain.Cliente;
import ar.com.avaco.factory.SapBusinessException;
import ar.com.avaco.premec.sap.dto.BusinessPartnerResponseDTO;
import ar.com.avaco.premec.sap.service.BusinessPartnerService;
import ar.com.avaco.utils.DateUtils;
import ar.com.avaco.ws.rest.security.dto.JwtAuthenticationRequest;
import ar.com.avaco.ws.rest.security.dto.JwtAuthenticationResponse;
import ar.com.avaco.ws.rest.security.dto.Permission;
import ar.com.avaco.ws.rest.security.dto.Profile;
import ar.com.avaco.ws.rest.security.dto.User;
import ar.com.avaco.ws.rest.security.dto.UserAuthorised;
import ar.com.avaco.ws.rest.security.exception.AuthenticationException;
import ar.com.avaco.ws.rest.security.service.UserService;
import ar.com.avaco.ws.rest.security.service.impl.JwtUserDetailsService;
import ar.com.avaco.ws.rest.security.util.JwtTokenUtil;

@RestController
public class AuthenticationRestController {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Resource(name = "authenticationManager")
	private AuthenticationManager authenticationManager;

	@Resource(name = "jwtTokenUtil")
	private JwtTokenUtil jwtTokenUtil;

	@Resource(name = "jwtUserDetailsService")
	private JwtUserDetailsService userDetailsService;

	@Resource(name = "userService")
	private UserService userService;

	@Autowired
	private BusinessPartnerService bpService;

	@Value("${vigencia.warning.dias}")
	private String vigenciaWarningDias;

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public ResponseEntity<JwtAuthenticationResponse> createAuthenticationToken(
			@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

		try {
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		} catch (CredentialsExpiredException ex) {

			// Usuario y password son válidos, solo vencidos
			UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

			String token = jwtTokenUtil.generatePasswordExpiredToken(userDetails);

			User usuario = userService.getByUsername(userDetails.getUsername());

			return ResponseEntity.status(HttpStatus.CONFLICT).body(new JwtAuthenticationResponse(token, usuario, true));

		}

		// Reload password post-security so we can generate the token
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);

		User usuario = userService.getByUsername(userDetails.getUsername());

		try {
			// Busco el business partner
			BusinessPartnerResponseDTO byCUIT = bpService.getByCUIT(usuario.getUsername());

			// Si es valido, le agrego el permiso para crear reclamos generales
			if (byCUIT.getValid().equals("tYES")) {
				Permission permiso = new Permission();
				permiso.setCode("AGREGAR_RECLAMO");
				Profile perfil = new Profile();
				perfil.getPermissions().add(permiso);
				usuario.getProfiles().add(perfil);

				// Setear fecha de vencimiento obtenida de SAP
				String validTo = byCUIT.getValidTo();

				if (StringUtils.isNotBlank(validTo)) {
					
					LocalDate fecha = LocalDate.parse(validTo.substring(0, 10));

					LocalDate plusDays = fecha.plusDays(Integer.parseInt(vigenciaWarningDias) * -1);

					if (LocalDate.now().isAfter(plusDays)) {
						String resultado = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
						usuario.setFechaVencimiento(resultado);
					}
				}
			}
		} catch (SapBusinessException e) {

		}

		// Return the token and user datas
		return ResponseEntity.ok(new JwtAuthenticationResponse(token, usuario, false));
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
		String authToken = request.getHeader(tokenHeader);
		final String token = authToken.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		Cliente user = (Cliente) userDetailsService.loadUserByUsername(username);

		User usuario = userService.getByUsername(username);

		try {
			// Busco el business partner
			BusinessPartnerResponseDTO byCUIT = bpService.getByCUIT(usuario.getUsername());

			// Si es valido, le agrego el permiso para crear reclamos generales
			// FIXME PONER NOT CUANDO TERMINEN LAS PRUEBAS
			if (byCUIT.getValid().equals("tYES")) {
				Permission permiso = new Permission();
				permiso.setCode("AGREGAR_RECLAMO");
				Profile perfil = new Profile();
				perfil.getPermissions().add(permiso);
				usuario.getProfiles().add(perfil);

				// Setear fecha de vencimiento obtenida de SAP
				String validTo = byCUIT.getValidTo();

				if (StringUtils.isNotBlank(validTo)) {
					
					LocalDate fecha = LocalDate.parse(validTo.substring(0, 10));

					LocalDate plusDays = fecha.plusDays(Integer.parseInt(vigenciaWarningDias) * -1);

					if (LocalDate.now().isAfter(plusDays)) {
						String resultado = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
						usuario.setFechaVencimiento(resultado);
					}
				}

			}
		} catch (SapBusinessException e) {

		}

		if (jwtTokenUtil.canTokenBeRefreshed(token, user.getFechaAltaPassword())) {
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken, usuario, false));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<?> userAuthenticationToken(HttpServletRequest request) {
		String authToken = request.getHeader(tokenHeader);
		final String token = authToken.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		Cliente user = (Cliente) userDetailsService.loadUserByUsername(username);
		UserAuthorised userAutho = new UserAuthorised();
		userAutho.setUsername(user.getUsername());
		userAutho.setAuthorities(user.getAuthorities());
		userAutho.setAccountNoExpired(user.isAccountNonExpired());
		userAutho.setAccountNonLocked(user.isAccountNonLocked());
		userAutho.setCredentialsNonExpired(user.isCredentialsNonExpired());
		userAutho.setEnabled(user.isEnabled());
		return ResponseEntity.ok(userAutho);
	}

	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	/**
	 * Authenticates the user. If something is wrong, an
	 * {@link AuthenticationException} will be thrown
	 */
	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

}