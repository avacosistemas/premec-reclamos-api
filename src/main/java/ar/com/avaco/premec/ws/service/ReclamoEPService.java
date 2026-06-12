package ar.com.avaco.premec.ws.service;

import java.util.List;

import ar.com.avaco.arc.core.domain.filter.ReclamoFilterDTO;
import ar.com.avaco.factory.SapBusinessException;
import ar.com.avaco.premec.dto.ReclamoCreateDTO;
import ar.com.avaco.premec.dto.ReclamoValorarDTO;
import ar.com.avaco.premec.sap.dto.ServiceCallActivityDTO;
import ar.com.avaco.premec.sap.dto.ServiceCallReclamoListDTO;
import ar.com.avaco.ws.service.PageDTO;

public interface ReclamoEPService {

	void create(String cuit, ReclamoCreateDTO reclamo) throws SapBusinessException, Exception;

	PageDTO<ServiceCallReclamoListDTO> list(String customerCode, ReclamoFilterDTO filter);

	List<ServiceCallActivityDTO> listActividades(String cuit, Long idServiceCall);

	void valorar(String cuit, ReclamoValorarDTO valoracion) throws SapBusinessException;

}
