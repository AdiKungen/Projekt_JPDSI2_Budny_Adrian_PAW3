package jsf.course.book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jsf.course.dao.BookDAO;
import jsf.course.entities.Book;
import jsf.course.utils.LazyBookDataModel;

@Named
@RequestScoped
public class BookBB {
	
	private String title;
	
	@EJB
	BookDAO bookDAO;
	
	@Inject
	Flash flash;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Book getSingleBook(Object id) {
		return bookDAO.get(id);
	}
	
	public List<Book> getFullList(){
		return bookDAO.getFullList();
	}
	
	public List<Book> getList(){
		List<Book> list = null;
		
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (title != null && title.length() > 0){
			searchParams.put("title", title);
		}
		
		list = bookDAO.getList(searchParams);
		
		return list;
	}
	
	private LazyDataModel<Book> lazyModel;
	
	@PostConstruct
	public void init() {
		lazyModel = new LazyBookDataModel(bookDAO);
	}
	
	public LazyDataModel<Book> getLazyModel() {
		return lazyModel;
	}
	
}
