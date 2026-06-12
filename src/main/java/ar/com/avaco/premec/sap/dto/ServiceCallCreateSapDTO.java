package ar.com.avaco.premec.sap.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.avaco.premec.dto.ProblemaMaquinaDTO;
import ar.com.avaco.premec.dto.TipoProblemaMaquinaDTO;

public class ServiceCallCreateSapDTO {

	private String subject;
	private String customerCode;
	private String internalSerialNum;
	private String itemCode;
	private String manufacturerSerialNum;
	private Integer assigneeCode;
	private Integer origin;
	private Integer status;
	private String priority;
	private String description;
	private String serviceBPType;
	private Long attachmentEntry;

	private TipoProblemaMaquinaDTO tipoProblema;
	private ProblemaMaquinaDTO problema;

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Subject", this.subject);
		map.put("CustomerCode", this.customerCode);
		map.put("InternalSerialNum", this.internalSerialNum);
		map.put("ItemCode", this.itemCode);
		map.put("ManufacturerSerialNum", this.manufacturerSerialNum);
		map.put("AssigneeCode", assigneeCode);
		map.put("Origin", 2);
		map.put("Status", -3);
		map.put("Priority", this.priority);
		map.put("Description", this.description);
		map.put("ServiceBPType", "srvcSales");

		map.put("U_tipodeproblema", tipoProblema.getNombre());
		map.put("U_detalleproblema", problema.getNombre());

		map.put("U_tipodeproblemaid", tipoProblema.getId());
		map.put("U_detalleproblemaid", problema.getId());

		map.put("AttachmentEntry", attachmentEntry);

		Map<String, Object> solution = new HashMap<String, Object>();
		solution.put("LineNum", 0);
		solution.put("SolutionID", 31);

		List<Map<String, Object>> solutions = new ArrayList<Map<String, Object>>();
		solutions.add(solution);

		map.put("ServiceCallSolutions", solutions);

		return map;

	}

	public Long getAttachmentEntry() {
		return attachmentEntry;
	}

	public void setAttachmentEntry(Long attachmentEntry) {
		this.attachmentEntry = attachmentEntry;
	}

	public TipoProblemaMaquinaDTO getTipoProblema() {
		return tipoProblema;
	}

	public void setTipoProblema(TipoProblemaMaquinaDTO tipoProblema) {
		this.tipoProblema = tipoProblema;
	}

	public ProblemaMaquinaDTO getProblema() {
		return problema;
	}

	public void setProblema(ProblemaMaquinaDTO problema) {
		this.problema = problema;
	}

	private List<ServiceCallSolution> serviceCallSolutions = new ArrayList<ServiceCallSolution>();

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getInternalSerialNum() {
		return internalSerialNum;
	}

	public void setInternalSerialNum(String internalSerialNum) {
		this.internalSerialNum = internalSerialNum;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getManufacturerSerialNum() {
		return manufacturerSerialNum;
	}

	public void setManufacturerSerialNum(String manufacturerSerialNum) {
		this.manufacturerSerialNum = manufacturerSerialNum;
	}

	public Integer getAssigneeCode() {
		return assigneeCode;
	}

	public void setAssigneeCode(Integer assigneeCode) {
		this.assigneeCode = assigneeCode;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getServiceBPType() {
		return serviceBPType;
	}

	public void setServiceBPType(String serviceBPType) {
		this.serviceBPType = serviceBPType;
	}

	public List<ServiceCallSolution> getServiceCallSolutions() {
		return serviceCallSolutions;
	}

	public void setServiceCallSolutions(List<ServiceCallSolution> serviceCallSolutions) {
		this.serviceCallSolutions = serviceCallSolutions;
	}

}
