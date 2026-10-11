package jsf.course.login;

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
import jsf.course.dao.UserDAO;
import jsf.course.entities.User;
import jsf.course.utils.NavigationBB;
import jsf.course.utils.RoleBB;

@Named
@RequestScoped
public class LoginBB {
	
	private String login;
    private String password;
    
    public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	@EJB
	UserDAO userDAO;
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	NavigationBB navigationBB;
	
	@Inject
	FacesContext fcscontext;
	
	@Inject
	Flash flash;
	
	@Inject
	RoleBB roleBB;
	
	public String login() {
		List<User> lista = userDAO.getFullList();
		
		for(User u: lista) {
			if(u.getLogin().equals(login) && u.getPassword().equals(password)) {
				if(roleBB.whatRoles(u).isEmpty()) {
					fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Konto nie posiada żadnych uprawnień. Skontaktuj się z administratorem", null));
					return navigationBB.stayTheSame();
				}
				HttpSession session = (HttpSession) extcontext.getSession(true);
				session.setAttribute("user", u);
				return navigationBB.redirectToList();
			}
		}
		fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędna nazwa użytkownika lub hasło", null));
		return navigationBB.stayTheSame();
	}

	public String logout() {
		HttpSession session = (HttpSession) extcontext.getSession(true);
		session.invalidate();
		
		return navigationBB.redirectToLogin();
	}
	
	public boolean isMsg() {
		if(flash.containsKey("message")) {
			return true;
		} else {
			return false;
		}
	}
}
