package jsf.course.utils;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import jsf.course.dao.BookDAO;
import jsf.course.entities.Book;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LazyBookDataModel extends LazyDataModel<Book> {
	
	private static final long serialVersionUID = 1L;
    private BookDAO bookDAO;

    public LazyBookDataModel(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public List<Book> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        List<Book> books = bookDAO.getLazyList(first, pageSize);
        if (filterBy != null && !filterBy.isEmpty()) {
            books = books.stream()
                    .filter(book -> true)
                    .collect(Collectors.toList());
        }

        int rowCount = bookDAO.count().intValue();
        this.setRowCount(rowCount);

        return books;
    }

    @Override
    public Book getRowData(String rowKey) {
        Integer id = Integer.valueOf(rowKey);
        return bookDAO.get(id); 
    }

    @Override
    public String getRowKey(Book book) {
        return book != null ? String.valueOf(book.getIdbook()) : null;
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return bookDAO.count().intValue();
    }
}
