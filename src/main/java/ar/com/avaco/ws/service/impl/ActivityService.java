package ar.com.avaco.ws.service.impl;

import java.util.List;

import ar.com.avaco.ws.dto.actividad.RegistroPreviewEmpleadoMensualDTO;
import ar.com.avaco.ws.dto.employee.liquidacion.PlantillaData;
import ar.com.avaco.ws.dto.actividad.HorasPorEmpleadoDTO;

public interface ActivityService {

	List<HorasPorEmpleadoDTO> obtenerHorasAgrupadasPorFechaEmpleado(Long employeeId, String fechaDesde,
			String fechaHasta, String horaDesde, String horaHasta, String exclusionesActividadesCalculoHorasNetas);

	List<HorasPorEmpleadoDTO> obtenerHorasAgrupadasPorFechaEmpleado(List<Long> employeeIds, String fechaDesde,
			String fechaHasta, String exclusionesActividadesCalculoHorasNetas);

	List<RegistroPreviewEmpleadoMensualDTO> obtenerActividadesValoradas(String mes, String anio, String exclusiones);

	PlantillaData getPreviewNovedadesContador(String anio, String mes);


	List<RegistroPreviewEmpleadoMensualDTO> obtenerActividadesValoradasSinAgrupar(String fechaDesde, String fechaHasta,
			String exclusionesActividadesCalculoHorasNetas);

	List<RegistroPreviewEmpleadoMensualDTO> getRegistrosCierre(String fechaDesde, String fechaHasta,
			String exclusionesActividadesCalculoHorasNetas, String usuarioSap);

	List<RegistroPreviewEmpleadoMensualDTO> obtenerIndicadoresPorGrupoEmpleado(String fechaDesde, String fechaHasta,
			String exclusionesActividadesCalculoHorasNetas, List<Long> idUsuariosSap);

	List<RegistroPreviewEmpleadoMensualDTO> obtenerIndicadoresPorEmpleados(String fechaDesde, String fechaHasta,
			String exclusionesActividadesCalculoHorasNetas, List<Long> idUsuariosSap);

}
