package ar.com.avaco.arc.sec.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

/**
 * Un acceso representa la union de un perfil con un listado de rolCompania. Un
 * clientek tiene asigando un acceso y este determina que las acciones que puede
 * realizar en base a los permisos puede realizarse oeprando con el listado de
 * companias y gasoductos asignados.
 * 
 * @author aogonzalez
 * 
 */
@Entity
@Table(name = "CLIENTE_ACCESO")
@Immutable
public class Acceso extends ar.com.avaco.arc.core.domain.Entity<Long> {

	/** */
	private static final long serialVersionUID = -445795213829738066L;

	@Id
	@Column(name = "ID_CLIENTE_ACCESO")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_CLIENTE")
	private Cliente cliente;

	/**
	 * El perfil del Acceso
	 */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "ID_PERFIL")
	private Perfil perfil;

	/**
	 * @return the perfil
	 */
	public Perfil getPerfil() {
		return perfil;
	}

	/**
	 * @param perfil the perfil to set
	 */
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Acceso (");
		sb.append(id);
		sb.append(")");
		return sb.toString();
	}

	public String getDescripcion() {
		StringBuilder sb = new StringBuilder();
		sb.append(getPerfil().getRol().getNombre());
		sb.append(" - ");
		sb.append(getPerfil().getNombre());
		return sb.toString();
	}

}