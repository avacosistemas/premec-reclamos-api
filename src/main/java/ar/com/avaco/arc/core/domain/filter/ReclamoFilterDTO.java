package ar.com.avaco.arc.core.domain.filter;

public class ReclamoFilterDTO extends AbstractFilter {

	private Integer nroReclamo;
	private String internalSerialNum;
	private String fechaDesde;
	private String fechaHasta;
	private String tipoFecha;
	private String estado;
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getNroReclamo() {
		return nroReclamo;
	}

	public void setNroReclamo(Integer nroReclamo) {
		this.nroReclamo = nroReclamo;
	}

	public String getInternalSerialNum() {
		return internalSerialNum;
	}

	public void setInternalSerialNum(String internalSerialNum) {
		this.internalSerialNum = internalSerialNum;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getTipoFecha() {
		return tipoFecha;
	}

	public void setTipoFecha(String tipoFecha) {
		this.tipoFecha = tipoFecha;
	}

}
