package ar.com.avaco.premec.sap.service;

import ar.com.avaco.factory.SapBusinessException;
import ar.com.avaco.premec.sap.dto.BusinessPartnerResponseDTO;

public interface BusinessPartnerService {

	BusinessPartnerResponseDTO getByCUIT(String cuit) throws SapBusinessException;

}
