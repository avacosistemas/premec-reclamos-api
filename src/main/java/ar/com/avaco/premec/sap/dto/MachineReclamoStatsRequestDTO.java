package ar.com.avaco.premec.sap.dto;

import java.util.List;

public class MachineReclamoStatsRequestDTO {

	private List<String> maquinas;
	private List<PeriodoDTO> periodos;

	public List<String> getMaquinas() {
		return maquinas;
	}

	public void setMaquinas(List<String> maquinas) {
		this.maquinas = maquinas;
	}

	public List<PeriodoDTO> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(List<PeriodoDTO> periodos) {
		this.periodos = periodos;
	}
}
