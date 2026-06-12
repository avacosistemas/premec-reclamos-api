package ar.com.avaco.ws.service.filter;

import java.util.ArrayList;
import java.util.List;

import ar.com.avaco.arc.core.domain.filter.AbstractFilter;
import ar.com.avaco.arc.core.domain.filter.FilterData;
import ar.com.avaco.arc.core.domain.filter.FilterDataType;
import ar.com.avaco.commons.domain.TipoActividad;

public class ProblemaMaquinaFilter extends AbstractFilter {

	private Long idTipoProblemaMaquina;

	@Override
	public List<FilterData> getFilterDatas() {
		List<FilterData> list = new ArrayList<FilterData>();
		list.add(new FilterData("tipoProblemaMaquina.id", idTipoProblemaMaquina, FilterDataType.EQUALS));
		return list;
	}

	public Long getIdTipoProblemaMaquina() {
		return idTipoProblemaMaquina;
	}

	public void setIdTipoProblemaMaquina(Long idTipoProblemaMaquina) {
		this.idTipoProblemaMaquina = idTipoProblemaMaquina;
	}

}
