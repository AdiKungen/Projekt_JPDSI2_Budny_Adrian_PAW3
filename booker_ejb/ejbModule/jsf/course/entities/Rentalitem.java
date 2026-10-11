package jsf.course.entities;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the rentalitem database table.
 * 
 */
@Entity
@NamedQuery(name="Rentalitem.findAll", query="SELECT r FROM Rentalitem r")
public class Rentalitem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idrentalitem;

	private int price;

	//bi-directional many-to-one association to Book
	@ManyToOne
	private Book book;

	//bi-directional many-to-one association to Rental
	@ManyToOne
	private Rental rental;

	public Rentalitem() {
	}

	public int getIdrentalitem() {
		return this.idrentalitem;
	}

	public void setIdrentalitem(int idrentalitem) {
		this.idrentalitem = idrentalitem;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Rental getRental() {
		return this.rental;
	}

	public void setRental(Rental rental) {
		this.rental = rental;
	}

}