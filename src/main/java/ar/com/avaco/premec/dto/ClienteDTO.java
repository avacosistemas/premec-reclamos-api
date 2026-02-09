package ar.com.avaco.premec.dto;

import ar.com.avaco.ws.rest.dto.DTOEntity;

public class ClienteDTO extends DTOEntity<Long> {

	private Long id;

	private String usuario;

	public ClienteDTO() {
		// TODO Auto-generated constructor stub
	}

	public ClienteDTO(Long idUsr, String nombreApellidoUsername) {
		this.id = idUsr;
		this.usuario = nombreApellidoUsername;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
