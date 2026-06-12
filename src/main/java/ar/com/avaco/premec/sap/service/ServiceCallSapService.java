package ar.com.avaco.premec.sap.service;

import java.util.List;

import ar.com.avaco.arc.core.domain.filter.ReclamoFilterDTO;
import ar.com.avaco.factory.SapBusinessException;
import ar.com.avaco.premec.dto.ReclamoCreateDTO;
import ar.com.avaco.premec.sap.dto.ServiceCallActivityDTO;
import ar.com.avaco.premec.sap.dto.ServiceCallCreateSapDTO;
import ar.com.avaco.premec.sap.dto.ServiceCallMachineStatsDTO;
import ar.com.avaco.premec.sap.dto.ServiceCallReclamoListDTO;
import ar.com.avaco.ws.service.PageDTO;

public interface ServiceCallSapService {

	PageDTO<ServiceCallReclamoListDTO> getServiceCallsByCustomer(String customerCode, ReclamoFilterDTO filter); 
	
	Long create(ServiceCallCreateSapDTO dto) throws SapBusinessException;

	List<ServiceCallActivityDTO> getActivitiesByServiceCall(String customerCode, Long serviceCallId);

	boolean insertEventoReclamoCreacion(ReclamoCreateDTO dto, Long serviceCallId, String customerCode,
			String customerName, String customerEmail);

	List<ServiceCallMachineStatsDTO> getEstadisticasMaquinaParada(String machine, String periodosJson);

	void valorar(String string, Long id, Integer valoracion) throws SapBusinessException;

}
