package ar.com.avaco.premec.dto;

import ar.com.avaco.ws.rest.dto.DTOEntity;

public class ProblemaMaquinaDTO extends DTOEntity<Long> {

	private Long id;

	private String nombre;

	private Long idTipoProblema;

	private String tipoProblema;

	public String getTipoProblema() {
		return tipoProblema;
	}

	public void setTipoProblema(String tipoProblema) {
		this.tipoProblema = tipoProblema;
	}

	public Long getIdTipoProblema() {
		return idTipoProblema;
	}

	public void setIdTipoProblema(Long idTipoProblema) {
		this.idTipoProblema = idTipoProblema;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
