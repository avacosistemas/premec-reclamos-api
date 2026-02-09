package ar.com.avaco.arc.sec.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import ar.com.avaco.arc.core.component.bean.repository.NJBaseRepository;
import ar.com.avaco.arc.sec.domain.Cliente;
import ar.com.avaco.arc.sec.repository.ClienteRepository;

@Repository("clienteRepository")
public class ClienteRepositoryImpl extends NJBaseRepository<Long, Cliente> implements ClienteRepository {

	protected ClienteRepositoryImpl(EntityManager em) {
		super(Cliente.class, em);
	}

	@Override
	protected void initialize(Cliente entity) {
		Hibernate.initialize(entity.getAccesos());
		entity.getAccesos();
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> getAllOnlyIdUsernameAndName() {
		Criteria c = this.getCurrentSession().createCriteria(this.getHandledClass());

		c.setProjection(Projections.projectionList().add(Projections.property("id"), "id")
				.add(Projections.property("username"), "username").add(Projections.property("nombre"), "nombre")
				.add(Projections.property("apellido"), "apellido"));

		c.addOrder(Order.asc("nombre")).addOrder(Order.asc("apellido"));

		c.setResultTransformer(Transformers.aliasToBean(this.getHandledClass()));

		return c.list();
	}

	@Override
	public Cliente findByUsername(String username) {
		Criteria c = this.getCurrentSession().createCriteria(this.getHandledClass());
		c.add(Restrictions.eq("username", username));
		return (Cliente) c.uniqueResult();
	}

	@Override
	public Cliente findByEmail(String email) {
		Criteria c = this.getCurrentSession().createCriteria(this.getHandledClass());
		c.add(Restrictions.eq("email", email));
		return (Cliente) c.uniqueResult();
	}

	@Override
	public boolean isUserExistWithEmail(String email) {
		Criteria c = this.getCurrentSession().createCriteria(this.getHandledClass());
		c.add(Restrictions.eq("email", email));
		return c.uniqueResult() != null;
	}

	@Override
	public List<Cliente> findByLegajoIn(List<String> legajos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente findByLegajo(int legajo) {
		Criteria c = this.getCurrentSession().createCriteria(this.getHandledClass());
		c.add(Restrictions.eq("legajo", legajo));
		return (Cliente) c.uniqueResult();
	}

	@Override
	public List<Cliente> findByIdIn(List<Long> lista) {
		Criteria c = this.getCurrentSession().createCriteria(this.getHandledClass());
		c.add(Restrictions.in("id", lista));
		return c.list();
	}

}