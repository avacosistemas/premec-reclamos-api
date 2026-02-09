package ar.com.avaco.ws.service.impl;

import javax.annotation.Resource;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import ar.com.avaco.entities.RegistroHorasMaquinaExcedidaReseteo;
import ar.com.avaco.premec.service.RegistroHorasMaquinaExcedidaReseteoService;
import ar.com.avaco.utils.DateUtils;
import ar.com.avaco.ws.dto.RegistroHorasMaquinaExcedidaReseteoDTO;
import ar.com.avaco.ws.service.CRUDEPBaseService;
import ar.com.avaco.ws.service.RegistroHorasMaquinaExcedidaReseteoEPService;

@Service("registroHorasMaquinaExcedidaReseteoEPService")
public class RegistroHorasMaquinaExcedidaReseteoEPServiceImpl
		extends CRUDEPBaseService<Long, RegistroHorasMaquinaExcedidaReseteoDTO, RegistroHorasMaquinaExcedidaReseteo, RegistroHorasMaquinaExcedidaReseteoService>
		implements RegistroHorasMaquinaExcedidaReseteoEPService {

	@Override
	protected RegistroHorasMaquinaExcedidaReseteo convertToEntity(RegistroHorasMaquinaExcedidaReseteoDTO dto) {
		ModelMapper modelMapper = new ModelMapper();
		RegistroHorasMaquinaExcedidaReseteo map = modelMapper.map(dto, RegistroHorasMaquinaExcedidaReseteo.class);
		return map;
	}

	@Override
	protected RegistroHorasMaquinaExcedidaReseteoDTO convertToDto(RegistroHorasMaquinaExcedidaReseteo entity) {
		ModelMapper modelMapper = new ModelMapper();
		RegistroHorasMaquinaExcedidaReseteoDTO map = modelMapper.map(entity, RegistroHorasMaquinaExcedidaReseteoDTO.class);
		map.setFechaActualString(DateUtils.toString(map.getFechaActual(), "dd/MM/yyyy"));
		map.setFechaAnteriorString(DateUtils.toString(map.getFechaAnterior(), "dd/MM/yyyy"));
		return map;
	}

	@Override
	@Resource(name = "registroHorasMaquinaExcedidaReseteoService")
	protected void setService(RegistroHorasMaquinaExcedidaReseteoService service) {
		this.service = service;
	}

}
