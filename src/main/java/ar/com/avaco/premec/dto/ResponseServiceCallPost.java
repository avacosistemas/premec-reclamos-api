package ar.com.avaco.premec.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseServiceCallPost {

	@JsonProperty("ServiceCallID")
	private Long serviceCallId;

	public Long getServiceCallId() {
		return serviceCallId;
	}

	public void setServiceCallId(Long serviceCallId) {
		this.serviceCallId = serviceCallId;
	}

}
