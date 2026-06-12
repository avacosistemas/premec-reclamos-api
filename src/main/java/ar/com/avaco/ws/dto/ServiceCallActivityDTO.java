package ar.com.avaco.ws.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceCallActivityDTO {

	@JsonProperty("LineNum")
	private Integer lineNum;

	@JsonProperty("ActivityCode")
	private Integer activityCode;

	@JsonProperty("U_U_HsMaq")
	private String uHsMaq;

	@JsonProperty("U_valoracionreclamo")
	private String valoracionReclamo;

	public Integer getLineNum() {
		return lineNum;
	}

	public void setLineNum(Integer lineNum) {
		this.lineNum = lineNum;
	}

	public Integer getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(Integer activityCode) {
		this.activityCode = activityCode;
	}

	public String getuHsMaq() {
		return uHsMaq;
	}

	public void setuHsMaq(String uHsMaq) {
		this.uHsMaq = uHsMaq;
	}

	public String getValoracionReclamo() {
		return valoracionReclamo;
	}

	public void setValoracionReclamo(String valoracionReclamo) {
		this.valoracionReclamo = valoracionReclamo;
	}

}
