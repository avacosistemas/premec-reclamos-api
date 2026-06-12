package ar.com.avaco.ws.dto.formulario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceCallActivitiesResponseSapDTO {

	@JsonProperty("ActivityCode")
	private Long activityCode;

	@JsonProperty("U_U_HsMaq")
	private String horasMaquina;

	@JsonProperty("LineNum")
	private Integer line;
	
	@JsonProperty("U_valoracionreclamo")
	private Integer valoracion;
	
	public Long getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(Long activityCode) {
		this.activityCode = activityCode;
	}

	public String getHorasMaquina() {
		return horasMaquina;
	}

	public void setHorasMaquina(String horasMaquina) {
		this.horasMaquina = horasMaquina;
	}

	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}

	public Integer getValoracion() {
		return valoracion;
	}

	public void setValoracion(Integer valoracion) {
		this.valoracion = valoracion;
	}

	
	
}
