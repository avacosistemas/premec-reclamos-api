package ar.com.avaco.premec.dto;

import java.util.ArrayList;
import java.util.List;

import ar.com.avaco.ws.dto.formulario.FotoDTO;

public class ReclamoCreateDTO {

	// Paquete de campos de la maquina
	private Long equipmentCardNum;
	private String internalSerialNum;
	private String itemCode;
	private String manufacturerSerialNum;

	private Long idTipoProblema;
	private Long idProblema;

	private String prioridad;

	private String asunto;

	private String descripcion;

	private List<FotoDTO> fotos = new ArrayList<FotoDTO>();

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

	public Long getEquipmentCardNum() {
		return equipmentCardNum;
	}

	public void setEquipmentCardNum(Long equipmentCardNum) {
		this.equipmentCardNum = equipmentCardNum;
	}

	public Long getIdTipoProblema() {
		return idTipoProblema;
	}

	public void setIdTipoProblema(Long idTipoProblema) {
		this.idTipoProblema = idTipoProblema;
	}

	public Long getIdProblema() {
		return idProblema;
	}

	public void setIdProblema(Long idProblema) {
		this.idProblema = idProblema;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<FotoDTO> getFotos() {
		return fotos;
	}

	public void setFotos(List<FotoDTO> fotos) {
		this.fotos = fotos;
	}

}
