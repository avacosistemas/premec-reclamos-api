package ar.com.avaco.arc.sec.service;

import java.io.Serializable;
import java.util.List;

import ar.com.avaco.arc.sec.domain.Acceso;
import ar.com.avaco.arc.sec.domain.Cliente;

/**
 * Facade para validaciones de seguridad
 * 
 * @author HDimitri
 */
public interface SeguridadService extends Serializable {
	
	List<Acceso> getAccesosConPermiso(Cliente usuario, String permiso);
	
	boolean hasPermiso(Cliente usuario, String permiso);

	boolean hasRol(Cliente usuario, String permiso, String rol);

	boolean hasRolYPermisos(Cliente usuario, List<String> permisos, String rol);
}