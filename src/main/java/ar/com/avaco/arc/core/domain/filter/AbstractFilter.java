package ar.com.avaco.arc.core.domain.filter;

import java.util.ArrayList;
import java.util.List;

import ar.com.avaco.ws.service.filter.SortPageDTO;

public abstract class AbstractFilter extends SortPageDTO {

	private Integer pageSize;

	private Integer page;

	private Boolean asc;

	private String idx;
	
	private Boolean distinctRootEntity;

	public AbstractFilter(Integer pageSize, Integer page, Boolean asc, String idx) {
		super();
		this.pageSize = pageSize;
		this.page = page;
		this.asc = asc;
		this.idx = idx;
	}

	public AbstractFilter() {
	}
	
	public List<FilterData> getFilterDatas() {
		return new ArrayList<FilterData>();
	}
	
	public List<List<FilterData>> getOrFilterDatas(){
		return new ArrayList<List<FilterData>>();
	}
	
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Boolean isAsc() {
		return asc;
	}

	public String getSidx() {
		return idx;
	}

	public Boolean getAsc() {
		return asc;
	}

	public void setAsc(Boolean asc) {
		this.asc = asc;
	}

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public Boolean getDistinctRootEntity() {
		return distinctRootEntity;
	}

	public void setDistinctRootEntity(Boolean distinctRootEntity) {
		this.distinctRootEntity = distinctRootEntity;
	}
	
}