package jsf.course.login;

import java.sql.Timestamp;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jsf.course.dao.PermissionDAO;
import jsf.course.dao.RoleDAO;
import jsf.course.dao.UserDAO;
import jsf.course.entities.Permission;
import jsf.course.entities.Role;
import jsf.course.entities.User;
import jsf.course.utils.NavigationBB;

@Named
@RequestScoped
public class SignInBB {
	
	private User user = new User();
	
	private String login;
	private String password;
	private String passwordR;
	
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
	public String getPasswordR() {
		return passwordR;
	}
	public void setPasswordR(String passwordR) {
		this.passwordR = passwordR;
	}
	
	@EJB
	UserDAO userDAO;
	
	@EJB
	PermissionDAO permissionDAO;
	
	@EJB
	RoleDAO roleDAO;
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	NavigationBB navigationBB;
	
	@Inject
	FacesContext fcscontext;
	
	@Inject
	Flash flash;
	
	public String signin() {
		try {
			List<User> lista = userDAO.getFullList();
			if (lista != null) {
				for (User u : lista) {
					if (u.getLogin() != null && u.getLogin().equalsIgnoreCase(login)) {
						fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Użytkownik o tej nazwie już istnieje", null));
						return navigationBB.stayTheSame();
					}
				}
			}
			
			if (password != null && password.equals(passwordR)) {
				Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
				
				user.setLogin(login);
				user.setPassword(password);
				user.setWhencreated(currentTimestamp);
				user.setWhenlastmodified(currentTimestamp);
				user.setUser1(null);
				user.setUser2(null);
				
				userDAO.insert(user);
				
				User persistedUser = userDAO.get(user.getIduser());
				
				Role defaultRole = roleDAO.get(3);
				if (defaultRole == null) {
					List<Role> allRoles = roleDAO.getFullList();
					if (allRoles != null && !allRoles.isEmpty()) {
						for (Role r : allRoles) {
							if ("user".equalsIgnoreCase(r.getName()) || "użytkownik".equalsIgnoreCase(r.getName())) {
								defaultRole = r;
								break;
							}
						}
						if (defaultRole == null) {
							defaultRole = allRoles.get(0);
						}
					}
				}

				if (defaultRole != null) {
					Permission newPermission = new Permission();
					newPermission.setRole(defaultRole);
					newPermission.setUser(persistedUser != null ? persistedUser : user);
					newPermission.setWhengiven(currentTimestamp);
					newPermission.setWhenrevoked(null);
					permissionDAO.insert(newPermission);
				} else {
					System.err.println("OSTRZEŻENIE: Brak ról w bazie danych!");
				}
				
				String message = "Poprawnie zarejestrowano konto. Możesz się teraz zalogować.";
				flash.setKeepMessages(true);
				flash.put("message", message);
				
				return navigationBB.redirectToLogin();
			} else {
				fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Podane hasła nie są zgodne", null));
				return navigationBB.stayTheSame();
			}
		} catch (Exception e) {
			e.printStackTrace();
			fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd podczas rejestracji: " + e.getMessage(), null));
			return navigationBB.stayTheSame();
		}
	}
}