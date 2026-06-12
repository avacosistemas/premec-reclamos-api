package ar.com.avaco.ws.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceCallActivitiesResponse {

	@JsonProperty(value = "ServiceCallActivities")
	private List<ServiceCallActivityDTO> serviceCallActivities;

	public List<ServiceCallActivityDTO> getServiceCallActivities() {
		return serviceCallActivities;
	}

	public void setServiceCallActivities(List<ServiceCallActivityDTO> serviceCallActivities) {
		this.serviceCallActivities = serviceCallActivities;
	}

}
