package jsf.course.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the book database table.
 * 
 */
@Entity
@NamedQuery(name="Book.findAll", query="SELECT b FROM Book b")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idbook;

	private String author;

	private String cover;

	private String description;

	private String isavailable;

	private int price;

	private String title;

	@Temporal(TemporalType.DATE)
	private Date whenreleased;

	//bi-directional many-to-one association to Genre
	@ManyToOne
	private Genre genre;

	//bi-directional many-to-one association to Rentalitem
	@OneToMany(mappedBy="book")
	private List<Rentalitem> rentalitems;

	public Book() {
	}

	public int getIdbook() {
		return this.idbook;
	}

	public void setIdbook(int idbook) {
		this.idbook = idbook;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCover() {
		return this.cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsavailable() {
		return this.isavailable;
	}

	public void setIsavailable(String isavailable) {
		this.isavailable = isavailable;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getWhenreleased() {
		return this.whenreleased;
	}

	public void setWhenreleased(Date whenreleased) {
		this.whenreleased = whenreleased;
	}

	public Genre getGenre() {
		return this.genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public List<Rentalitem> getRentalitems() {
		return this.rentalitems;
	}

	public void setRentalitems(List<Rentalitem> rentalitems) {
		this.rentalitems = rentalitems;
	}

	public Rentalitem addRentalitem(Rentalitem rentalitem) {
		getRentalitems().add(rentalitem);
		rentalitem.setBook(this);

		return rentalitem;
	}

	public Rentalitem removeRentalitem(Rentalitem rentalitem) {
		getRentalitems().remove(rentalitem);
		rentalitem.setBook(null);

		return rentalitem;
	}

}