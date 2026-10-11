package jsf.course.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jsf.course.entities.Genre;

@Stateless
public class GenreDAO {

	@PersistenceContext
	EntityManager em;
	
	public void insert(Genre genre) {
		em.persist(genre);
	}
	
	public Genre update(Genre genre) {
		return em.merge(genre);
	}
	
	public void delete(Genre genre) {
		em.remove(em.merge(genre));
	}
	
	public Genre get(Object id) {
		return em.find(Genre.class, id);
	}
	
	public List<Genre> getFullList() {
		List<Genre> list = null;

		Query query = em.createQuery("select u from Genre u");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new java.util.ArrayList<>();
		}

		return list;
	}
	
	public List<Genre> getList(Map<String, Object> searchParams) {
		List<Genre> list = null;

		String select = "select u ";
		String from = "from Genre u ";
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
