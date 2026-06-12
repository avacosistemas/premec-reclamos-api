package ar.com.avaco.premec.ws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.avaco.premec.sap.dto.EstadisticaMaquinaDTO;
import ar.com.avaco.premec.sap.dto.MachineReclamoStatsRequestDTO;
import ar.com.avaco.premec.sap.dto.MaquinaParadaPeriodoDTO;
import ar.com.avaco.premec.sap.dto.ServiceCallMachineStatsDTO;
import ar.com.avaco.premec.sap.service.ServiceCallSapService;
import ar.com.avaco.ws.service.AbstractSapService;

@Service
public class ReclamoEstadisticasEPServiceImpl extends AbstractSapService implements ReclamoEstadisticasEPService {

	@Autowired
	private ServiceCallSapService serviceCallservice;

	@Override
	public EstadisticaMaquinaDTO getEstadisticasMaquinaParada(MachineReclamoStatsRequestDTO dto) {

		ObjectMapper mapper = new ObjectMapper();

		String periodosJson = "";

		try {

			periodosJson = mapper.writeValueAsString(dto.getPeriodos());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<ServiceCallMachineStatsDTO> listado = this.serviceCallservice.getEstadisticasMaquinaParada(dto.getMachine(), periodosJson);
		
		EstadisticaMaquinaDTO emdto = new EstadisticaMaquinaDTO();
		
		List<MaquinaParadaPeriodoDTO> periodos = new ArrayList<MaquinaParadaPeriodoDTO>();
		
		listado.forEach(e -> {
			if (e.getTotalGeneral().booleanValue()) {
				emdto.setCantidadReclamosTotal(e.getCantidadReclamos());
				emdto.setDiasParadaTotalTotal(e.getDiasParadaTotal());
			} else {
				MaquinaParadaPeriodoDTO periodo = new MaquinaParadaPeriodoDTO();
				periodo.setAnio(e.getAnio());
				periodo.setMes(e.getMes());
				periodo.setCantidadReclamos(e.getCantidadReclamos());
				periodo.setDiasParadaTotal(e.getDiasParadaTotal());
				periodos.add(periodo);
			}
		});
		
		emdto.getPeriodos().addAll(periodos);
		
		return emdto;
		
		
	}


}
