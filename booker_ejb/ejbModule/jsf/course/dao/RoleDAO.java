package jsf.course.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jsf.course.entities.Role;

@Stateless
public class RoleDAO {
	
	@PersistenceContext
	EntityManager em;
	
	public void insert(Role role) {
		em.persist(role);
	}
	
	public Role update(Role role) {
		return em.merge(role);
	}
	
	public void delete(Role role) {
		em.remove(em.merge(role));
	}
	
	public Role get(Object id) {
		return em.find(Role.class, id);
	}
	
	public List<Role> getFullList() {
		List<Role> list = null;

		Query query = em.createQuery("select u from Role u");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new java.util.ArrayList<>();
		}

		return list;
	}
	
	public List<Role> getList(Map<String, Object> searchParams) {
		List<Role> list = null;

		String select = "select u ";
		String from = "from Role u ";
		String where = "";
		String orderby = "order by u.name asc";

		String name = (String) searchParams.get("name");
		if (name != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.name like :name ";
		}
		
		Query query = em.createQuery(select + from + where + orderby);

		if (name != null) {
			query.setParameter("name", name+"%");
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new java.util.ArrayList<>();
		}

		return list;
	}
}
