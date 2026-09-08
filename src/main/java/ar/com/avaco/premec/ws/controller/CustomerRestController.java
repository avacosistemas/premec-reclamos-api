package ar.com.avaco.premec.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.avaco.factory.SapBusinessException;
import ar.com.avaco.premec.dto.ContractContactDTO;
import ar.com.avaco.premec.sap.service.BusinessPartnerService;
import ar.com.avaco.premec.sap.service.CustomerEquipmentCardsSapService;
import ar.com.avaco.premec.ws.service.ReclamoEPService;
import ar.com.avaco.ws.rest.dto.JSONResponse;

@RestController
public class CustomerRestController {

	@Autowired
	private CustomerEquipmentCardsSapService maquinaService;
	
	@Autowired
	private BusinessPartnerService businessPartnerService;

	@RequestMapping(value = "/customer/equipment", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> getMaquinasCustomer(@RequestParam(required = false, defaultValue = "") String maquina)
			throws SapBusinessException {
		String cuit = SecurityContextHolder.getContext().getAuthentication().getName();
		JSONResponse response = new JSONResponse();
		response.setData(maquinaService.listByCustomer(cuit, maquina));
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/customer/equipment/validate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> validateEquipment(@RequestParam String internalSerialNum) throws SapBusinessException {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		JSONResponse response = new JSONResponse();
		response.setData(maquinaService.valiteByCustomerMachine(name, internalSerialNum));
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/customer/equipment/validateEmail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> validateEquipmentEmail(@RequestParam String internalSerialNum) throws SapBusinessException {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		JSONResponse response = new JSONResponse();
		ContractContactDTO contractContact = this.businessPartnerService.getContractContact(name, internalSerialNum);
		response.setData(contractContact != null);
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

}
