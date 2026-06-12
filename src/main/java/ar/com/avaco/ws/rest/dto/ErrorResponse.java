package ar.com.avaco.ws.rest.dto;

import java.util.Map;

import org.springframework.http.HttpStatus;

public class ErrorResponse extends JSONResponse {

	private String message;
	private String error;
	private Map<String, String> errors;

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(HttpStatus status, Object data, String message, Map<String, String> errors) {
		super.setStatus(status);
		super.setData(data);
		this.message = message;
		this.errors = errors;
	}

	public ErrorResponse(HttpStatus status, Object data, String message, String error) {
		super.setStatus(status);
		super.setData(data);
		this.message = message;
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

}