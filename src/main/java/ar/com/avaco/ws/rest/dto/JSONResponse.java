/**
 * 
 */
package ar.com.avaco.ws.rest.dto;

import org.springframework.http.HttpStatus;

import ar.com.avaco.premec.sap.dto.ServiceCallReclamoListDTO;
import ar.com.avaco.ws.service.PageDTO;
import ar.com.avaco.ws.service.PageResponseDTO;

/**
 * 
 *
 */
public class JSONResponse {

	private String status;
	private Object data;
	private PageResponseDTO page;;

	public JSONResponse() {

	}

	public JSONResponse(String status, PageDTO<?> pageDTO) {
		this.status = status;
		this.data = pageDTO.getList();
		this.page = new PageResponseDTO();
		this.page.setPage(pageDTO.getPage());
		this.page.setPageSize(pageDTO.getPageSize());
		this.page.setTotalReg(pageDTO.getTotalReg());
	}

	public JSONResponse(HttpStatus status, PageDTO<?> pageDTO) {
		this.status = status.name();
		this.data = pageDTO.getList();
		this.page = new PageResponseDTO();
		this.page.setPage(pageDTO.getPage());
		this.page.setPageSize(pageDTO.getPageSize());
		this.page.setTotalReg(pageDTO.getTotalReg());
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

	public PageResponseDTO getPage() {
		return page;
	}

	public void setPage(PageResponseDTO page) {
		this.page = page;
	}

	public void setStatus(HttpStatus status) {
		this.status = status.name();

	}

}