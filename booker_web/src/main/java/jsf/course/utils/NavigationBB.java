package jsf.course.utils;

import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import jsf.course.entities.User;

@Named
@RequestScoped
public class NavigationBB {
	
	@Inject
	ExternalContext extcontext;
	
	public String stayTheSame() {
		return null;
	}
	public String redirectToLogin() {
		return "/loginView.xhtml?faces-redirect=true";
	}
	public String redirectToList() {
		return "/bookListView.xhtml?faces-redirect=true";
	}
	public String redirectToSignIn() {
		return "/signInView.xhtml?faces-redirect=true";
	}
	public String redirectToCart() {
		return "/cartView.xhtml?faces-redirect=true";
	}
	public String redirectToOrder() {
		return "/orderView.xhtml?faces-redirect=true";
	}
	public String redirectToReturn() {
		return "/returnView.xhtml?faces-redirect=true";
	}
	public String redirectToUser() {
		return "/userListView.xhtml?faces-redirect=true";
	}
	public String redirectToEdit(User user) {
		HttpSession session = (HttpSession) extcontext.getSession(true);
		session.setAttribute("editId", user);
		return "/userEditView.xhtml?faces-redirect=true";
	}
	public String redirectToDetails() {
		Map<String, String> paramMap = extcontext.getRequestParameterMap();
		Integer bookId = Integer.parseInt(paramMap.get("bookId"));
		HttpSession session = (HttpSession) extcontext.getSession(true);
		session.setAttribute("bookId", bookId);
		return "/bookDetailsView.xhtml?faces-redirect=true";
	}
}
