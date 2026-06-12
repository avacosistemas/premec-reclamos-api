package ar.com.avaco.ws.service;

public class PageResponseDTO {

	private Integer totalReg;

	private Integer pageSize;

	private Integer page;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotalReg() {
		return totalReg;
	}

	public void setTotalReg(Integer totalReg) {
		this.totalReg = totalReg;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
