package jsf.course.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jsf.course.entities.Rentalitem;

@Stateless
public class RentalitemDAO {
	
	@PersistenceContext
	EntityManager em;
	
	public void insert(Rentalitem rentalitem) {
		em.persist(rentalitem);
	}
	
	public Rentalitem update(Rentalitem rentalitem) {
		return em.merge(rentalitem);
	}
	
	public void delete(Rentalitem rentalitem) {
	    em.remove(em.contains(rentalitem) ? rentalitem : em.merge(rentalitem));
	}
	
	public Rentalitem get(Object id) {
		return em.find(Rentalitem.class, id);
	}
	
	public List<Rentalitem> getFullList() {
		List<Rentalitem> list = null;

		Query query = em.createQuery("select u from Rentalitem u");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new java.util.ArrayList<>();
		}

		return list;
	}
	
	public List<Rentalitem> getList(Map<String, Object> searchParams) {
		List<Rentalitem> list = null;

		String select = "select u ";
		String from = "from Rentalitem u ";
		String where = "";
		String orderby = "order by u.idrentalitem asc";

		Integer idrentalitem = (Integer) searchParams.get("idrentalitem");
		if (idrentalitem != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.idrentalitem like :idrentalitem ";
		}
		
		Query query = em.createQuery(select + from + where + orderby);

		if (idrentalitem != null) {
			query.setParameter("idrentalitem", idrentalitem+"%");
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new java.util.ArrayList<>();
		}

		return list;
	}
	
	public int deleteByRentalAndBook(int rentalId, int bookId) {
	    try {
	        Query query = em.createQuery("DELETE FROM Rentalitem r WHERE r.rental.idrental = :rentalId AND r.book.idbook = :bookId");
	        query.setParameter("rentalId", rentalId);
	        query.setParameter("bookId", bookId);
	        return query.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return 0;
	    }
	}
	
	public long countByRental(int rentalId) {
	    try {
	        Query query = em.createQuery("SELECT COUNT(r) FROM Rentalitem r WHERE r.rental.idrental = :rentalId");
	        query.setParameter("rentalId", rentalId);
	        return (Long) query.getSingleResult();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return 0;
	    }
	}
}
