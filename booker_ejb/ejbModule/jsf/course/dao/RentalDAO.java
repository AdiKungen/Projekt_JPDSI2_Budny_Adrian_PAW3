package jsf.course.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jsf.course.entities.Rental;

@Stateless
public class RentalDAO {

	@PersistenceContext
	EntityManager em;
	
	public void insert(Rental rental) {
		em.persist(rental);
	}
	
	public Rental update(Rental rental) {
		return em.merge(rental);
	}
	
	public void delete(Rental rental) {
		em.remove(em.merge(rental));
	}
	
	public Rental get(Object id) {
		return em.find(Rental.class, id);
	}
	
	public List<Rental> getFullList() {
		List<Rental> list = null;

		Query query = em.createQuery("select u from Rental u");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new java.util.ArrayList<>();
		}

		return list;
	}
	
	public List<Rental> getList(Map<String, Object> searchParams) {
		List<Rental> list = null;

		String select = "select u ";
		String from = "from Rental u ";
		String where = "";
		String orderby = "order by u.idrental asc";

		Integer idrental = (Integer) searchParams.get("idrental");
		if (idrental != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.idrental like :idrental ";
		}
		
		Query query = em.createQuery(select + from + where + orderby);

		if (idrental != null) {
			query.setParameter("idrental", idrental+"%");
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new java.util.ArrayList<>();
		}

		return list;
	}
	
	public int deleteById(int idrental) {
	    try {
	        Query query = em.createQuery("DELETE FROM Rental r WHERE r.idrental = :idrental");
	        query.setParameter("idrental", idrental);
	        return query.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return 0;
	    }
	}
}
