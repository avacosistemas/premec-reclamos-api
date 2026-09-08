package ar.com.avaco.premec.sap.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ar.com.avaco.factory.SapBusinessException;
import ar.com.avaco.premec.dto.ContractContactDTO;
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
	
	@Override
	public ContractContactDTO getContractContact(String customerCode, String internalSerialNum) {

	    StringBuilder sql = new StringBuilder();

	    sql.append("SELECT TOP (1) ")
	       .append("bp.CardCode, ")
	       .append("bp.CardName, ")
	       .append("cp.CntctCode, ")
	       .append("cp.Name AS ContactName, ")
	       .append("LOWER(REPLACE(cp.E_MailL, ' ', '')) AS Email ")
	       .append("FROM CTR1 ct1 ")
	       .append("INNER JOIN OCTR ctr ")
	       .append("ON ctr.ContractID = ct1.ContractID ")
	       .append("AND ctr.CstmrCode = ? ")
	       .append("INNER JOIN OCRD bp ")
	       .append("ON bp.CardCode = ctr.CstmrCode ")
	       .append("INNER JOIN OCPR cp ")
	       .append("ON cp.CardCode = bp.CardCode ")
	       .append("AND cp.CntctCode = ctr.CntctCode ")
	       .append("WHERE ct1.InternalSN = ?");

	    ContractContactDTO result = null;

	    try (Connection conn = sqlcon.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

	        stmt.setString(1, "C" + customerCode);
	        stmt.setString(2, internalSerialNum);

	        try (ResultSet rs = stmt.executeQuery()) {

	            if (rs.next()) {

	                result = new ContractContactDTO();

	                result.setCardCode(rs.getString("CardCode"));
	                result.setCardName(rs.getString("CardName"));
	                result.setContactCode(rs.getInt("CntctCode"));
	                result.setContactName(rs.getString("ContactName"));
	                result.setEmail(rs.getString("Email"));
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}

}
