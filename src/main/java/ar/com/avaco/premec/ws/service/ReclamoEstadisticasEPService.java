package ar.com.avaco.premec.ws.service;

import ar.com.avaco.premec.sap.dto.EstadisticaMaquinaDTO;
import ar.com.avaco.premec.sap.dto.MachineReclamoStatsRequestDTO;

public interface ReclamoEstadisticasEPService {

	EstadisticaMaquinaDTO getEstadisticasMaquinaParada(MachineReclamoStatsRequestDTO dto);

}
