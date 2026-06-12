package ar.com.avaco.premec.sap.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerEquipmentCardBusinessPartnerDTO {
	
	@JsonProperty("BPCode")
	private String bpCode;

	public String getBpCode() {
		return bpCode;
	}

	public void setBpCode(String bpCode) {
		this.bpCode = bpCode;
	}
	
}
