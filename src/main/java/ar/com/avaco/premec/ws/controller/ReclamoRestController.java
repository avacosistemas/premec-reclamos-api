package ar.com.avaco.premec.ws.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.avaco.arc.core.domain.filter.ReclamoFilterDTO;
import ar.com.avaco.commons.exception.ErrorValidationException;
import ar.com.avaco.factory.SapBusinessException;
import ar.com.avaco.premec.dto.ReclamoCreateDTO;
import ar.com.avaco.premec.dto.ReclamoValorarDTO;
import ar.com.avaco.premec.sap.dto.EstadisticaMaquinaDTO;
import ar.com.avaco.premec.sap.dto.MachineReclamoStatsRequestDTO;
import ar.com.avaco.premec.sap.dto.ServiceCallActivityDTO;
import ar.com.avaco.premec.sap.dto.ServiceCallReclamoListDTO;
import ar.com.avaco.premec.ws.service.ReclamoEPService;
import ar.com.avaco.premec.ws.service.ReclamoEstadisticasEPService;
import ar.com.avaco.ws.rest.dto.JSONResponse;
import ar.com.avaco.ws.service.PageDTO;

@Controller
public class ReclamoRestController {

	@Autowired
	private ReclamoEPService service;

	@Autowired
	private ReclamoEstadisticasEPService estadisticasService;

	@RequestMapping(value = "/reclamo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> list(ReclamoFilterDTO reclamoFilterDTO) {
		String cuit = SecurityContextHolder.getContext().getAuthentication().getName();
		PageDTO<ServiceCallReclamoListDTO> pageDTO = this.service.list("C" + cuit, reclamoFilterDTO);
		JSONResponse response = new JSONResponse();
		response.setData(pageDTO.getList());
		response.setPage(pageDTO.toPageRepsponse());
		response.setOk(true);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/reclamo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> create(@RequestBody ReclamoCreateDTO reclamo) {
		String cuit = SecurityContextHolder.getContext().getAuthentication().getName();
		try {
			this.service.create(cuit, reclamo);
		} catch (SapBusinessException e) {
			e.printStackTrace();
			Map<String, String> error = new HashMap<String, String>();
			error.put("error", e.getSapMessage());
			throw new ErrorValidationException("No se pudo crear el reclamo", error);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> error = new HashMap<String, String>();
			error.put("error", e.getMessage());
			throw new ErrorValidationException("No se pudo crear el reclamo", error);
		}
		JSONResponse response = new JSONResponse();
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/reclamo/valorar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> create(@RequestBody ReclamoValorarDTO valoracion) {
		String cuit = SecurityContextHolder.getContext().getAuthentication().getName();
		try {
			this.service.valorar(cuit, valoracion);
		} catch (SapBusinessException e) {
			e.printStackTrace();
			Map<String, String> error = new HashMap<String, String>();
			error.put("error", e.getSapMessage());
			throw new ErrorValidationException("No se pudo valorar el reclamo", error);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> error = new HashMap<String, String>();
			error.put("error", e.getMessage());
			throw new ErrorValidationException("No se pudo valorar el reclamo", error);
		}
		JSONResponse response = new JSONResponse();
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/reclamo/actividades", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> getActividades(@RequestParam Long idServiceCall) {
		String cuit = SecurityContextHolder.getContext().getAuthentication().getName();
		List<ServiceCallActivityDTO> listActividades = this.service.listActividades("C" + cuit, idServiceCall);
		JSONResponse response = new JSONResponse();
		response.setStatus(JSONResponse.OK);
		response.setData(listActividades);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@PostMapping("/reclamo/estadisticas/maquina-parada")
	public ResponseEntity<?> getMachineStats(@RequestBody MachineReclamoStatsRequestDTO dto) {
		Map<String, EstadisticaMaquinaDTO> estadisticasMaquinaParada = this.estadisticasService
				.getEstadisticasMaquinaParada(dto);
		JSONResponse response = new JSONResponse();
		response.setData(estadisticasMaquinaParada);
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

}
