package ar.com.avaco.premec.sap.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ar.com.avaco.arc.core.domain.filter.ReclamoFilterDTO;
import ar.com.avaco.factory.SapBusinessException;
import ar.com.avaco.premec.dto.ReclamoCreateDTO;
import ar.com.avaco.premec.dto.ResponseServiceCallPost;
import ar.com.avaco.premec.sap.dto.ServiceCallActivityDTO;
import ar.com.avaco.premec.sap.dto.ServiceCallCreateSapDTO;
import ar.com.avaco.premec.sap.dto.ServiceCallMachineStatsDTO;
import ar.com.avaco.premec.sap.dto.ServiceCallReclamoListDTO;
import ar.com.avaco.utils.DateUtils;
import ar.com.avaco.ws.service.AbstractSapService;
import ar.com.avaco.ws.service.PageDTO;

@Service
public class ServiceCallSapServiceImpl extends AbstractSapService implements ServiceCallSapService {

	@Override
	public Long create(ServiceCallCreateSapDTO dto) throws SapBusinessException {
		HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(dto.toMap());
		String actividadUrl = urlSAP + "/ServiceCalls";
		ResponseEntity<ResponseServiceCallPost> doExchange = getRestTemplate().doExchange(actividadUrl, HttpMethod.POST,
				httpEntity, ResponseServiceCallPost.class);
		return doExchange.getBody().getServiceCallId();
	}

	@Override
	public void valorar(String cuitConC, Long id, Integer valoracion) throws SapBusinessException {

		Map<String, Object> mapValoracion = new HashMap<String, Object>();
		mapValoracion.put("LineNum", 0);
		mapValoracion.put("U_valoracionreclamo", valoracion);

		List<Map<String, Object>> listValoracion = new ArrayList<Map<String, Object>>();
		listValoracion.add(mapValoracion);
		Map<String, Object> mapServiceCall = new HashMap<String, Object>();
		mapServiceCall.put("ServiceCallActivities", listValoracion);

		HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(mapServiceCall);

		String serviceCallUrl = urlSAP + "/ServiceCalls({idServiceCall})";
		serviceCallUrl = serviceCallUrl.replace("{idServiceCall}", id.toString());
		getRestTemplate().doExchange(serviceCallUrl, HttpMethod.PATCH, httpEntity, Object.class);
	}

	@Override
	public List<ServiceCallMachineStatsDTO> getEstadisticasMaquinaParada(String maquinasJson, String periodosJson) {

		StringBuilder sql = new StringBuilder();

		sql.append("EXEC SP_GetMultipeMachineReclamoStats ?, ?");

		List<ServiceCallMachineStatsDTO> result = new ArrayList<>();

		try (Connection conn = sqlcon.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

			stmt.setString(1, maquinasJson);
			stmt.setString(2, periodosJson);

			try (ResultSet rs = stmt.executeQuery()) {

				while (rs.next()) {

					ServiceCallMachineStatsDTO dto = new ServiceCallMachineStatsDTO();

					dto.setMaquina(rs.getString("Maquina"));

					Object anioObj = rs.getObject("Anio");
					if (anioObj != null) {
						dto.setAnio(rs.getInt("Anio"));
					}

					Object mesObj = rs.getObject("Mes");
					if (mesObj != null) {
						dto.setMes(rs.getInt("Mes"));
					}

					dto.setCantidadReclamos(rs.getInt("CantidadReclamos"));

					dto.setDiasParadaTotal(rs.getInt("DiasParadaTotal"));

					dto.setTotalGeneral(rs.getBoolean("TotalGeneral"));

					result.add(dto);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public boolean insertEventoReclamoCreacion(ReclamoCreateDTO dto, Long serviceCallId, String customerCode,
			String customerName, String customerEmail) {

		StringBuilder sql = new StringBuilder();

		sql.append("INSERT INTO EVENTOS_RECLAMOS (").append("ServiceCallId, ").append("CustomerCode, ")
				.append("CustomerName, ").append("InternalSerialNum, ").append("ItemCode, ")
				.append("ManufacturerSerialNum, ").append("CustomerEmail, ").append("TipoEvento, ")
				.append("Observaciones ").append(") VALUES (").append("?, ?, ?, ?, ?, ?, ?, ?, ?").append(")");

		try (Connection conn = sqlcon.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

			stmt.setObject(1, serviceCallId);
			stmt.setString(2, customerCode);
			stmt.setString(3, customerName);
			stmt.setString(4, dto.getInternalSerialNum());
			stmt.setString(5, dto.getItemCode());
			stmt.setString(6, dto.getManufacturerSerialNum());
			stmt.setString(7, customerEmail);
			stmt.setString(8, "CREACION");
			stmt.setString(9, "");

			int rowsAffected = stmt.executeUpdate();

			return rowsAffected > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<ServiceCallActivityDTO> getActivitiesByServiceCall(String customerCode, Long serviceCallId) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ").append("ServiceCallId, ").append("ActivityCode, ").append("Resolucion, ")
				.append("Fecha, ").append("HoraInicio, ").append("HoraFin, ").append("EmpleadoAsignado, ")
				.append("Estado, ").append("Valoracion, ").append("Supervisor ")
				.append("FROM VW_ServiceCallActivitiesReclamos ").append("WHERE ServiceCallId = ? ")
				.append("and CustomerCode = ? ").append("ORDER BY Fecha DESC, HoraInicio DESC");

		List<ServiceCallActivityDTO> result = new ArrayList<>();

		List<String> estadosInforme = new ArrayList<String>();
		estadosInforme.add("Enviado");
		estadosInforme.add("Enviada");
		estadosInforme.add("Aprobada");
		estadosInforme.add("Aprobado");
		estadosInforme.add("Cerrada");
		estadosInforme.add("Cerrado");

		try (Connection conn = sqlcon.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

			stmt.setLong(1, serviceCallId);
			stmt.setString(2, customerCode);

			try (ResultSet rs = stmt.executeQuery()) {

				while (rs.next()) {

					ServiceCallActivityDTO dto = new ServiceCallActivityDTO();

					dto.setServiceCallId(rs.getInt("ServiceCallId"));
					dto.setActivityCode(rs.getInt("ActivityCode"));
					dto.setResolucion(rs.getString("Resolucion"));
					dto.setFecha(rs.getString("Fecha"));
					dto.setHoraInicio(rs.getString("HoraInicio"));
					dto.setHoraFin(rs.getString("HoraFin"));
					dto.setEmpleadoAsignado(rs.getString("EmpleadoAsignado"));
					dto.setEstado(rs.getString("Estado"));
					dto.setValoracion(rs.getString("Valoracion"));
					dto.setSupervisor(rs.getString("Supervisor"));
					dto.setInforme(estadosInforme.contains(dto.getEstado()));

					result.add(dto);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public PageDTO<ServiceCallReclamoListDTO> getServiceCallsByCustomer(String customerCode, ReclamoFilterDTO filter) {

		StringBuilder sql = new StringBuilder();

		sql.append(
				"SELECT COUNT(*) OVER() AS TotalRegistros,CustomerName,CustomerCode,ValoracionReclamo,ServiceCallID,Asunto,EstadoServiceCall,estadoReclamo,")
				.append("FechaCreacion,HoraCreacion,FechaInicioActividad,FechaFinActividad,")
				.append("EquipmentCardNum,ManufacturerSerialNum,InternalSN,ItemCode,ItemName, DetalleReclamoRechazado ")
				.append("FROM VW_ServiceCalls_Reclamos WHERE CustomerCode = ? ");

		List<Object> params = new ArrayList<>();
		params.add(customerCode);

		// --- Filtro por nro de reclamo ---
		if (filter.getNroReclamo() != null) {
			sql.append("AND ServiceCallID = ? ");
			params.add(filter.getNroReclamo());
		}

		// --- Filtro por internal serial ---
		if (filter.getInternalSerialNum() != null && !filter.getInternalSerialNum().isEmpty()) {
			sql.append("AND InternalSN = ? ");
			params.add(filter.getInternalSerialNum());
		}

		// --- Filtro por fechas ---
		String campoFecha = null;

		if (filter.getTipoFecha() != null) {
			switch (filter.getTipoFecha()) {
			case "CREACION":
				campoFecha = "FechaCreacion";
				break;
			case "INICIO":
				campoFecha = "FechaInicioActividad";
				break;
			case "FIN":
				campoFecha = "FechaFinActividad";
				break;
			}
		}

		if (campoFecha != null) {

			if (filter.getFechaDesde() != null && !filter.getFechaDesde().isEmpty()
					&& parseDate(filter.getFechaDesde()) != null) {
				sql.append("AND ").append(campoFecha).append(" >= ? ");
				params.add(parseDate(filter.getFechaDesde()));
			}

			if (filter.getFechaHasta() != null && !filter.getFechaHasta().isEmpty()
					&& parseDate(filter.getFechaHasta()) != null) {
				sql.append("AND ").append(campoFecha).append(" <= ? ");
				params.add(parseDate(filter.getFechaHasta()));
			}
		}

		if (StringUtils.isNotBlank(filter.getEstado())) {
			sql.append(" AND estadoReclamo = '" + filter.getEstado() + "'");
		}

		// --- Orden ---
		sql.append("ORDER BY FechaCreacion DESC");

		if (filter.getPageSize() != null && filter.getPage() != null) {
			sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ");
			params.add(filter.getPage());
			params.add(filter.getPageSize());
		}

		List<ServiceCallReclamoListDTO> result = new ArrayList<>();
		Integer totalReg = 0;

		try (Connection conn = sqlcon.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

			// Set dinámico de parámetros
			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					totalReg = rs.getInt("TotalRegistros");
					result.add(mapRow(rs));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		PageDTO<ServiceCallReclamoListDTO> page = new PageDTO<ServiceCallReclamoListDTO>();
		page.setList(result);
		page.setPage(filter.getPage());
		page.setTotalReg(totalReg);
		page.setPageSize(filter.getPageSize());

		return page;
	}

	private ServiceCallReclamoListDTO mapRow(ResultSet rs) throws SQLException {

		ServiceCallReclamoListDTO dto = new ServiceCallReclamoListDTO();

		dto.setCustomerName(rs.getString("CustomerName"));
		dto.setCustomerCode(rs.getString("CustomerCode"));
		dto.setServiceCallID(rs.getInt("ServiceCallID"));
		dto.setAsunto(rs.getString("Asunto"));
		dto.setEstadoServiceCall(rs.getInt("EstadoServiceCall"));
		dto.setEstadoReclamo(rs.getString("estadoReclamo"));

		dto.setFechaCreacion(DateUtils.toString(rs.getDate("FechaCreacion"), "dd/MM/yyyy"));
		dto.setHoraCreacion(rs.getInt("HoraCreacion"));

		dto.setFechaInicioActividad(DateUtils.toString(rs.getDate("FechaInicioActividad"), "dd/MM/yyyy"));
		dto.setFechaFinActividad(DateUtils.toString(rs.getDate("FechaFinActividad"), "dd/MM/yyyy"));

		dto.setEquipmentCardNum(rs.getInt("EquipmentCardNum"));
		dto.setManufacturerSerialNum(rs.getString("ManufacturerSerialNum"));
		dto.setInternalSN(rs.getString("InternalSN"));
		dto.setItemCode(rs.getString("ItemCode"));
		dto.setItemName(rs.getString("ItemName"));

		String valoracion = rs.getString("ValoracionReclamo");
		dto.setValoracion(valoracion != null ? Integer.parseInt(valoracion) : null);

		dto.setMotivoRechazo(rs.getString("DetalleReclamoRechazado"));

		return dto;
	}

	private java.sql.Date parseDate(String fecha) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date utilDate = sdf.parse(fecha);
			return new java.sql.Date(utilDate.getTime());
		} catch (Exception e) {

		}
		return null;
	}

}
