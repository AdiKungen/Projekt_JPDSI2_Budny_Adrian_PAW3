package jsf.course.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jsf.course.entities.User;

@Stateless
public class UserDAO {

	@PersistenceContext
	EntityManager em;
	
	public void insert(User user) {
		em.persist(user);
	}
	
	public User update(User user) {
		return em.merge(user);
	}
	
	public void delete(User user) {
	    em.remove(em.contains(user) ? user : em.merge(user));
	}
	
	public User get(Object id) {
		User u = em.find(User.class, id);
		u.getPermissions().size();
		
		return u;
	}
	
	public List<User> getFullList() {
		List<User> list = null;

		Query query = em.createQuery("select u from User u");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new java.util.ArrayList<>();
		}

		return list;
	}
	
	public List<User> getList(Map<String, Object> searchParams) {
		List<User> list = null;

		String select = "select u ";
		String from = "from User u ";
		String where = "";
		String orderby = "order by u.iduser asc";

		String login = (String) searchParams.get("login");
		if (login != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.login like :login ";
		}
		
		Query query = em.createQuery(select + from + where + orderby);

		if (login != null) {
			query.setParameter("login", login+"%");
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
