package ar.com.avaco.premec.ws.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ibm.icu.util.Calendar;

import ar.com.avaco.arc.core.domain.filter.ReclamoFilterDTO;
import ar.com.avaco.commons.exception.BusinessException;
import ar.com.avaco.factory.SapBusinessException;
import ar.com.avaco.premec.dto.ContractContactDTO;
import ar.com.avaco.premec.dto.ProblemaMaquinaDTO;
import ar.com.avaco.premec.dto.ReclamoCreateDTO;
import ar.com.avaco.premec.dto.ReclamoValorarDTO;
import ar.com.avaco.premec.dto.TipoProblemaMaquinaDTO;
import ar.com.avaco.premec.sap.dto.ServiceCallActivityDTO;
import ar.com.avaco.premec.sap.dto.ServiceCallCreateSapDTO;
import ar.com.avaco.premec.sap.dto.ServiceCallReclamoListDTO;
import ar.com.avaco.premec.sap.service.BusinessPartnerService;
import ar.com.avaco.premec.sap.service.ServiceCallSapService;
import ar.com.avaco.ws.dto.formulario.FotoDTO;
import ar.com.avaco.ws.service.AbstractSapService;
import ar.com.avaco.ws.service.PageDTO;
import ar.com.avaco.ws.service.impl.AttachmentSapService;

@Service
public class ReclamoEPServiceImpl extends AbstractSapService implements ReclamoEPService {

	@Autowired
	private BusinessPartnerService businessPartnerService;
	
	@Autowired
	private ServiceCallSapService serviceCallservice;
	
	@Autowired
	private AttachmentSapService attachmentService;
	
	@Autowired
	private TipoProblemaMaquinaEPService tipoProblemaEPService;

	@Autowired
	private ProblemaMaquinaEPService problemaEPService;

	@Value("${reclamos.path}")
	private String reclamosPath;
	
	@Value("${reclamo.usuario.asignacion.creacion.servicecall.alquileres}")
	private Integer userIdCreacionReclamoAlquiler;
	
	@Value("${reclamo.usuario.asignacion.creacion.servicecall.postventa}")
	private Integer userIdCreacionReclamoPostventa;
	
	@Override
	public PageDTO<ServiceCallReclamoListDTO> list(String customerCode, ReclamoFilterDTO filter) {
		return serviceCallservice.getServiceCallsByCustomer(customerCode, filter);
	}
	
	@Override
	public void create(String cuit, ReclamoCreateDTO reclamo) throws Exception {
	
		Long absEntryAttachment = null;
		
		if (reclamo.getFotos() != null && !reclamo.getFotos().isEmpty()) {
			List<Map<String, String>> attMap = generarAttachmentMap(reclamo.getFotos(), cuit);
			absEntryAttachment = attachmentService.enviarAttachmentsSap(attMap);
		}
		
		TipoProblemaMaquinaDTO tipoProblema = tipoProblemaEPService.get(reclamo.getIdTipoProblema());
		ProblemaMaquinaDTO problema = problemaEPService.get(reclamo.getIdProblema());
		
		ServiceCallCreateSapDTO dto = new ServiceCallCreateSapDTO();
		
		dto.setSubject(reclamo.getAsunto());
		
		dto.setCustomerCode("C" + cuit);

		dto.setInternalSerialNum(reclamo.getInternalSerialNum());
		dto.setItemCode(reclamo.getItemCode());
		dto.setManufacturerSerialNum(reclamo.getManufacturerSerialNum());

		dto.setPriority(reclamo.getPrioridad());
		dto.setDescription(reclamo.getDescripcion());

		dto.setTipoProblema(tipoProblema);
		dto.setProblema(problema);
		
		dto.setAttachmentEntry(absEntryAttachment);
		
		dto.setAssigneeCode(userIdCreacionReclamoAlquiler);
		
		Long reclamoId = this.serviceCallservice.create(dto);
		
		ContractContactDTO contractContact = this.businessPartnerService.getContractContact(cuit, reclamo.getInternalSerialNum());
		String email = null;
		String nombre = cuit;
		
		if (contractContact != null && StringUtils.isNotBlank(contractContact.getEmail())) {
			email = contractContact.getEmail();
			nombre = contractContact.getCardName();
		}
		
		this.serviceCallservice.insertEventoReclamoCreacion(reclamo, reclamoId, dto.getCustomerCode(), nombre, email); 
		
	}
	
	private List<Map<String, String>> generarAttachmentMap(List<FotoDTO> fotos, String cuit)
			throws Exception {

		Logger logger = Logger.getLogger(this.getClass());
		
		// Armo el listado de archivos con mapa
		List<Map<String, String>> archivos = new ArrayList<>();
		
		try {
			// Busco la carpeta que tiene las fotos
			String path = reclamosPath + "\\fotos\\" + cuit;
			
			// Genero el diretorio con el path
			try {
				Files.createDirectories(Paths.get(path));
			} catch (IOException e) {
				e.printStackTrace();
				logger.debug(path + " no se pudo generar");
				throw new Exception("No se pudo generar el path " + path, e);
			}

			int i = 0;

			// Por cada foto
			for (FotoDTO foto : fotos) {

				i++;
				
				// Armo el nombre del archivo
				String[] split = foto.getNombre().split("\\.");
				String fileName = "FOTO-" + Calendar.getInstance().getTimeInMillis() + "-" + i;
				String fileExtension = split[split.length - 1];
				String pathname = path + "\\" + fileName + "." + fileExtension;
				
				logger.debug("FOTO " + pathname);
				
				try {
					FileUtils.writeByteArrayToFile(new File(pathname), foto.getArchivo());
				} catch (IOException e) {
					e.printStackTrace();
					logger.debug("FOTO " + pathname + " con error ");
					throw new Exception("No se pudo generar la foto " + pathname, e);
				}

				Map<String, String> fotoMap = new HashMap<String, String>();
				fotoMap.put("FileExtension", fileExtension);
				fotoMap.put("FileName", fileName);
				fotoMap.put("SourcePath", path);
				fotoMap.put("UserID", "1");

				archivos.add(fotoMap);
			}
			
			logger.debug("archivos cantidad " + archivos.size());

		} catch (Exception e) {
			e.printStackTrace();
			String error = "No se pudo generar attachment map para las fotos del reclamo " + e.getMessage();
			throw new Exception(error);
		}
		return archivos;
	}

	public List<ServiceCallActivityDTO> listActividades (String customerCode, Long idServiceCall) {
		return serviceCallservice.getActivitiesByServiceCall(customerCode, idServiceCall);
	}

	@Override
	public void valorar(String cuit, ReclamoValorarDTO valoracion) throws SapBusinessException {
		this.serviceCallservice.valorar("C" + cuit, valoracion.getId(), valoracion.getValoracion());
	}

}
