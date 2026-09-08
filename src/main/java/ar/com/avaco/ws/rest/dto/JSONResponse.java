/**
 * 
 */
package ar.com.avaco.ws.rest.dto;

import ar.com.avaco.ws.service.filter.PageResponse;

public class JSONResponse {

	public static final String ERROR = "ERROR";
	public static final String OK = "OK";

	private Boolean ok;
	private String status;
	private Object data;

	private String error;

	private PageResponse page;

	public JSONResponse() {

	}

	public JSONResponse(String status, Object data) {
		super();
		this.status = status;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public PageResponse getPage() {
		return page;
	}

	public void setPage(PageResponse page) {
		this.page = page;
	}

}