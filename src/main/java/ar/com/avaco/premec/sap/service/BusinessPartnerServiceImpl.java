package ar.com.avaco.premec.sap.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ar.com.avaco.factory.SapBusinessException;
import ar.com.avaco.premec.sap.dto.BusinessPartnerResponseDTO;
import ar.com.avaco.ws.service.AbstractSapService;

@Service("businessPartnerService")
public class BusinessPartnerServiceImpl extends AbstractSapService implements BusinessPartnerService {

	@Override
	public BusinessPartnerResponseDTO getByCUIT(String cuit) throws SapBusinessException {
		String bpUrl = urlSAP + "/BusinessPartners('C{cuit}')".replace("{cuit}", cuit);
		ResponseEntity<BusinessPartnerResponseDTO> bpresponse = null;
		bpresponse = getRestTemplate().doExchange(bpUrl, HttpMethod.GET, null, BusinessPartnerResponseDTO.class);
		BusinessPartnerResponseDTO registro = bpresponse.getBody();
		return registro;
	}

}
