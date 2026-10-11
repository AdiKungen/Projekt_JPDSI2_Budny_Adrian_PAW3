package jsf.course.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jsf.course.entities.Permission;

@Stateless
public class PermissionDAO {
	
	@PersistenceContext
	EntityManager em;
	
	public void insert(Permission permission) {
		em.persist(permission);
	}
	
	public Permission update(Permission permission) {
		return em.merge(permission);
	}
	
	public void delete(Permission permission) {
		em.remove(em.merge(permission));
	}
	
	public Permission get(Object id) {
		return em.find(Permission.class, id);
	}
	
	public List<Permission> getFullList() {
		List<Permission> list = null;

		Query query = em.createQuery("select u from Permission u");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new java.util.ArrayList<>();
		}

		return list;
	}
	
	public List<Permission> getListForUser(Integer id) {
		List<Permission> list = null;
		
		Query query = em.createQuery("select u from Permission u WHERE u.user.iduser =" + id);

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new java.util.ArrayList<>();
		}

		return list;
	}
	
	public List<Permission> getList(Map<String, Object> searchParams) {
		List<Permission> list = null;

		String select = "select u ";
		String from = "from Permission u ";
		String where = "";
		String orderby = "order by u.idpermission asc";

		String idpermission = (String) searchParams.get("idpermission");
		if (idpermission != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.idpermission like :idpermission ";
		}
		
		Query query = em.createQuery(select + from + where + orderby);

		if (idpermission != null) {
			query.setParameter("idpermission", idpermission+"%");
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
