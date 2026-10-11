package jsf.course.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jsf.course.entities.Book;

@Stateless
public class BookDAO {

	@PersistenceContext
	EntityManager em;
	
	public void insert(Book book) {
		em.persist(book);
	}
	
	public Book update(Book book) {
		return em.merge(book);
	}
	
	public void delete(Book book) {
		em.remove(em.merge(book));
	}
	
	public Book get(Object id) {
		return em.find(Book.class, id);
	}
	
	public List<Book> getFullList() {
		List<Book> list = null;

		Query query = em.createQuery("select u from Book u");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new java.util.ArrayList<>();
		}

		return list;
	}
	
	public List<Book> getList(Map<String, Object> searchParams) {
		List<Book> list = null;

		String select = "select u ";
		String from = "from Book u ";
		String where = "";
		String orderby = "order by u.title asc";

		String title = (String) searchParams.get("title");
		if (title != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.title like :title ";
		}
		
		Query query = em.createQuery(select + from + where + orderby);

		if (title != null) {
			query.setParameter("title", title+"%");
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new java.util.ArrayList<>();
		}

		return list;
	}
	
	public List<Book> getLazyList(int first, int pageSize) {
		TypedQuery<Book> query = em.createQuery("SELECT b from Book b ORDER BY b.title ASC", Book.class);
		query.setFirstResult(first);
		query.setMaxResults(pageSize);
		
		return query.getResultList();
	}
	
	public Long count() {
	    return em.createQuery("SELECT COUNT(b) FROM Book b", Long.class).getSingleResult();
	}
}
