package ar.com.avaco.premec.ws.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.avaco.premec.domain.TipoMaquina;
import ar.com.avaco.premec.dto.TipoProblemaMaquinaDTO;
import ar.com.avaco.premec.ws.service.TipoProblemaMaquinaEPService;
import ar.com.avaco.ws.rest.controller.AbstractDTORestController;
import ar.com.avaco.ws.rest.dto.JSONResponse;

@RestController
public class TipoProblemaMaquinaRestController
		extends AbstractDTORestController<TipoProblemaMaquinaDTO, Long, TipoProblemaMaquinaEPService> {

	@RequestMapping(value = "/tipoProblemaMaquina", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> listByTipoMaquina(@RequestParam(required = false) String tipo) {
		List<TipoProblemaMaquinaDTO> tipos;
		if (StringUtils.isNotEmpty(tipo)) {
			tipos = this.service.listPattern("tipoMaquina", TipoMaquina.valueOf(tipo));
		} else {
			tipos = this.service.list();
		}
		JSONResponse response = new JSONResponse();
		response.setStatus(JSONResponse.OK);
		response.setData(tipos);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@Override
	@Resource(name = "tipoProblemaMaquinaEPService")
	public void setService(TipoProblemaMaquinaEPService service) {
		this.service = service;
	}

}
