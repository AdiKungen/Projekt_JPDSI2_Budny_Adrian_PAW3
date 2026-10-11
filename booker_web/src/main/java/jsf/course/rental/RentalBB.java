package jsf.course.rental;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import jsf.course.book.BookBB;
import jsf.course.dao.BookDAO;
import jsf.course.dao.RentalDAO;
import jsf.course.dao.RentalitemDAO;
import jsf.course.entities.Book;
import jsf.course.entities.Rental;
import jsf.course.entities.Rentalitem;
import jsf.course.entities.User;
import jsf.course.utils.NavigationBB;

@Named
@RequestScoped
public class RentalBB {
	
	private Rental rental = new Rental();
	private Rentalitem rentalitem = new Rentalitem();
	private int cenaPodstawowa = 0;
	private int cenaZaWynajecie = 0;
	private int iloscDni = 1;
	List<Rentalitem> orderList = new ArrayList<Rentalitem>();
	List<Rentalitem> returnList = new ArrayList<Rentalitem>();
	private boolean chckbx = false;
	private boolean chckbxR = false;

	
	public boolean isChckbxR() {
		return chckbxR;
	}

	public void setChckbxR(boolean chckbxR) {
		this.chckbxR = chckbxR;
	}

	public boolean isChckbx() {
		return chckbx;
	}

	public void setChckbx(boolean chckbx) {
		this.chckbx = chckbx;
	}

	public void setOrderList(List<Rentalitem> orderList) {
		this.orderList = orderList;
	}
	
	public List<Rentalitem> getOrderList(Integer idrental) {
		orderList.clear();
		for(Rentalitem r: rentalitemDAO.getFullList()) {
			if(r.getRental().getIdrental() == idrental) {
				orderList.add(r);
			}
		}
		
		return orderList;
	}

	public List<Rentalitem> getReturnList(Integer idrental) {
		returnList.clear();
		for(Rentalitem r: rentalitemDAO.getFullList()) {
			if(r.getRental().getIdrental() == idrental) {
				returnList.add(r);
			}
		}
		
		return returnList;
	}

	public void setReturnList(List<Rentalitem> returnList) {
		this.returnList = returnList;
	}

	public int getCenaPodstawowa() {
		return cenaPodstawowa;
	}


	public void setCenaPodstawowa(int cenaPodstawowa) {
		this.cenaPodstawowa = cenaPodstawowa;
	}


	public int getCenaZaWynajecie() {
		return cenaZaWynajecie;
	}


	public void setCenaZaWynajecie(int cenaZaWynajecie) {
		this.cenaZaWynajecie = cenaZaWynajecie;
	}


	public int getIloscDni() {
		return iloscDni;
	}


	public void setIloscDni(int iloscDni) {
		this.iloscDni = iloscDni;
	}

	@Inject
	NavigationBB navigationBB;
	
	@EJB
	RentalDAO rentalDAO;
	
	@EJB
	RentalitemDAO rentalitemDAO;
	
	@EJB
	BookDAO bookDAO;
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	FacesContext fcscontext;
	
	@Inject
	Flash flash;
	
	@Inject
	BookBB bookBB;
	
	public int obliczWynajecie() {
		cenaZaWynajecie = cenaPodstawowa * iloscDni;
		
		return cenaZaWynajecie;
	}
	
	public boolean isInCart(Integer bookid) {
		User user = (User) extcontext.getSessionMap().get("user");
		
		for(Rentalitem r: rentalitemDAO.getFullList()) {
			if(r.getBook().getIdbook() == bookid && r.getRental().getUser().getIduser() == user.getIduser() && r.getRental().getIssent() == null) {
				return true;
			}
		}
		return false;
	}
	 
	
	public Rental cartExists() {
		User user = (User) extcontext.getSessionMap().get("user");
		
		for(Rental r: rentalDAO.getFullList()) {
			if(r.getUser().getIduser() == user.getIduser() && r.getIssent() == null) {
				return r;
			}
		}
		
		return null;
	}

	public String addToCart(Integer bookIdParam) {
	    User user = (User) extcontext.getSessionMap().get("user");
	    
	    if (cartExists() != null) {
	        rental = cartExists();
	    } else {
	        rental = new Rental();
	        rental.setUser(user);
	        rentalDAO.insert(rental);
	    }   
	    
	    Integer bookId = bookIdParam;
	    if (bookId == null) {
	        String param = extcontext.getRequestParameterMap().get("rentalId");
	        if (param != null) {
	            bookId = Integer.parseInt(param);
	        }
	    }
	    
	    if (bookId != null) {
	        Book book = bookDAO.get(bookId);
	        Rentalitem item = new Rentalitem();
	        item.setRental(rental);
	        item.setBook(book);
	        item.setPrice(book.getPrice());
	        item.setRental(rental);
	        rentalitemDAO.insert(item);
	        
	        fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Poprawnie dodano do koszyka", null));
	    }
	    
	    return null;
	}

	public String addToCart() {
	    return addToCart(null);
	}

	public List<Rentalitem> getFullList(){
		return rentalitemDAO.getFullList();
	}
	
	public List<Book> getList(){
		List<Book> list = new ArrayList<Book>();
		
		Rental rental = cartExists();
		cenaPodstawowa = 0;
		
		if(rental != null) {
			for(Rentalitem r: getFullList()) {
				if(r.getRental().getIdrental() == rental.getIdrental()) {
					list.add(r.getBook());
					cenaPodstawowa = cenaPodstawowa + r.getBook().getPrice();
				}
			}
		}
		
		return list;
	}
	
	public String removeFromCart(Integer removeId) {
	    Rental rental = cartExists();
	    
	    if (rental != null && removeId != null) {
	        int rentalId = rental.getIdrental();

	        int deletedRows = rentalitemDAO.deleteByRentalAndBook(rentalId, removeId);
	        
	        if (deletedRows > 0) {
	            if (rentalitemDAO.countByRental(rentalId) == 0) {
	                rentalDAO.deleteById(rentalId);
	                this.rental = new Rental();
	            }
	            
	            flash.setKeepMessages(true);
	            flash.put("message", "Poprawnie usunięto z koszyka");
	        } else {
	            fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nie udało się usunąć pozycji z bazy", null));
	        }
	    } else {
	        fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Brak aktywnego koszyka lub identyfikatora książki", null));
	    }

	    return navigationBB.redirectToCart();
	}

	public String removeFromCart() {
	    return removeFromCart(null);
	}
	
	public String orderCart() {
		Rental cart = cartExists();
		cart.setIssent("1");
		cart.setDuration(iloscDni);
		cart.setCost(cenaPodstawowa * iloscDni);
		rentalDAO.update(cart);
		
		String message = "Poprawnie zamówiono koszyk";
		flash.put("message", message);
		return navigationBB.redirectToCart();
	}
	
	public boolean isAwaiting(Integer bookId) {
		User user = (User) extcontext.getSessionMap().get("user");
		
		for(Rentalitem r: rentalitemDAO.getFullList()) {
			if(r.getBook().getIdbook() == bookId && r.getRental().getUser().getIduser() == user.getIduser() && r.getRental().getIssent() != null && r.getRental().getIsaccepted() == null) {
				return true;
			}
		}
		return false;
	}
	
	public Timestamp isRented(Integer bookId) {
		User user = (User) extcontext.getSessionMap().get("user");	
		
		for(Rentalitem r: rentalitemDAO.getFullList()) {
			if(r.getBook().getIdbook() == bookId && r.getRental().getUser().getIduser() == user.getIduser() && r.getRental().getIssent() != null && r.getRental().getIsaccepted() != null && r.getRental().getIsreturned() == null) {
				return r.getRental().getWhenends();
			}
		}
		return null;
	}
	
	
	
	public boolean isAvailable(Integer bookId) {
		
		if(isInCart(bookId)) {
			return false;
		} else if(isAwaiting(bookId)) {
			return false;
		} else if(isRented(bookId) != null) {
			return false;
		} else if(!bookBB.getSingleBook(bookId).getIsavailable().equals("1")) {
			return false;
		}
		return true;
	}
	
	public List<Rental> getOrders() {
	    List<Rental> orders = new ArrayList<Rental>();
	    List<Rental> fullList = rentalDAO.getFullList();
	    if (fullList != null) {
	        for (Rental r : fullList) {
	            if (r.getUser() != null && r.getIssent() != null && r.getIsaccepted() == null) {
	                orders.add(r);
	            }
	        }
	    }
	    return orders;
	}

	public List<Rental> getReturns() {
	    List<Rental> returns = new ArrayList<Rental>();
	    List<Rental> fullList = rentalDAO.getFullList();
	    if (fullList != null) {
	        for (Rental r : fullList) {
	            if (r.getUser() != null && r.getIssent() != null && r.getIsaccepted() != null && r.getIsreturned() == null) {
	                returns.add(r);
	            }
	        }
	    }
	    return returns;
	}
	
	public void showOrderTable(Integer idrental) {
		Rental rental = rentalDAO.get(idrental);
		
		HttpSession session = (HttpSession) extcontext.getSession(true);
		
		session.setAttribute("objrental", rental);
		session.setAttribute("idrental", idrental);
	}
	
	public void showReturnTable(Integer idrental) {
		Rental rental = rentalDAO.get(idrental);
		
		HttpSession session = (HttpSession) extcontext.getSession(true);
		
		session.setAttribute("objreturn", rental);
		session.setAttribute("idreturn", idrental);
	}
	
	public String confirmOrder(Rental rental) {
		if(chckbx == true) {
			HttpSession session = (HttpSession) extcontext.getSession(true);
			
			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(currentTimestamp);
	        calendar.add(Calendar.HOUR, rental.getDuration()*24);
	        
	        Timestamp updatedTimestamp = new Timestamp(calendar.getTimeInMillis());
			
			rental.setIsaccepted("1");
			rental.setWhenrented(currentTimestamp);
			rental.setWhenends(updatedTimestamp);
			
			rentalDAO.update(rental);
			
			session.removeAttribute("objrental");
			session.removeAttribute("idrental");
			
			String message = "Poprawnie potwierdzono zamówienie";
			flash.put("message", message);
			return navigationBB.redirectToOrder();
		} else {
			fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Klient musi uiścić zapłatę", null));
			
			return navigationBB.stayTheSame();
		}
		
	}
	
	public String confirmReturn(Rental rental, Integer late) {
		if(chckbxR == true && late > 0 || late == 0) {
			HttpSession session = (HttpSession) extcontext.getSession(true);
			
			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
			
			rental.setIsreturned("1");
			rental.setWhenreturned(currentTimestamp);
			rental.setLatecost(late);
			
			rentalDAO.update(rental);
			
			session.removeAttribute("objreturn");
			session.removeAttribute("idreturn");
			
			String message = "Poprawnie potwierdzono zwrot";
			flash.put("message", message);
			return navigationBB.redirectToReturn();
		} else {
			fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Klient musi uiścić zapłatę za spóźnienie", null));
		
			return navigationBB.stayTheSame();
		}
	}
	
	public Integer howManyDaysLate(Rental rental) {
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		
		LocalDateTime ldtNow = currentTimestamp.toLocalDateTime();
		LocalDateTime ldtEnds = rental.getWhenends().toLocalDateTime();
		
		Duration duration = Duration.between(ldtEnds, ldtNow);
		
		if(duration.getSeconds() <= 0) {
			return 0;
		} else {
			Integer howLate = (int) (duration.getSeconds() / 86400);
			return howLate;
		}
	}
	
	public void checkAndRefreshRental() {
	    HttpSession session = (HttpSession) extcontext.getSession(false);
	    if (session != null) {
	        Integer idReturn = (Integer) session.getAttribute("idreturn");
	        if (idReturn != null) {
	            Rental freshReturn = rentalDAO.get(idReturn);
	            if (freshReturn == null || freshReturn.getUser() == null) {
	                session.removeAttribute("idreturn");
	                session.removeAttribute("objreturn");
	            } else {
	                session.setAttribute("objreturn", freshReturn);
	            }
	        }

	        Integer idRental = (Integer) session.getAttribute("idrental");
	        if (idRental != null) {
	            Rental freshRental = rentalDAO.get(idRental);
	            if (freshRental == null || freshRental.getUser() == null) {
	                session.removeAttribute("idrental");
	                session.removeAttribute("objrental");
	            } else {
	                session.setAttribute("objrental", freshRental);
	            }
	        }
	    }
	}
}
	

