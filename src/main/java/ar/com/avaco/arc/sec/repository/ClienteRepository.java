package ar.com.avaco.arc.sec.repository;

import ar.com.avaco.arc.sec.domain.Cliente;

import java.util.List;

import ar.com.avaco.arc.core.component.bean.repository.NJRepository;

public interface ClienteRepository extends NJRepository<Long, Cliente>, ClienteRepositoryCustom {

	Cliente findByUsername(String username);

	Cliente findByEmail(String email);

	boolean isUserExistWithEmail(String email);

	List<Cliente> findByLegajoIn(List<String> legajos);

	Cliente findByLegajo(int legajo);

	List<Cliente> findByIdIn(List<Long> lista);

}