package ar.com.avaco.premec.sap.service;

import java.util.List;

import ar.com.avaco.factory.SapBusinessException;
import ar.com.avaco.premec.sap.dto.CustomerEquipmentCardsItemListDTO;

public interface CustomerEquipmentCardsSapService {

	List<CustomerEquipmentCardsItemListDTO> listByCustomer(String cuit, String name) throws SapBusinessException;

	Boolean valiteByCustomerMachine(String name, String internalSerialNum);

}
