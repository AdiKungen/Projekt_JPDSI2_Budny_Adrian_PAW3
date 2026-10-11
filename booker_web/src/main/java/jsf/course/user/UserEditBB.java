package jsf.course.user;

import java.io.IOException;
import java.sql.Timestamp;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import jsf.course.dao.PermissionDAO;
import jsf.course.dao.RoleDAO;
import jsf.course.dao.UserDAO;
import jsf.course.entities.Permission;
import jsf.course.entities.User;
import jsf.course.utils.NavigationBB;
import jsf.course.utils.RoleBB;

@Named
@RequestScoped
public class UserEditBB {
	
	private User user = new User();
	private User admin = new User();
	private User newUser = new User();
	
	private String loginF = "";
	private String passwordF = "";
	private String passwordFR = "";
	
	private boolean chckbxUserF = false;
	private boolean chckbxWorkerF = false;
	private boolean chckbxAdminF = false;

	@EJB
	UserDAO userDAO;
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	FacesContext fcscontext;
	
	@Inject
	NavigationBB navigationBB;
	
	@Inject
	Flash flash;
	
	@Inject
	RoleBB roleBB;
	
	@Inject
	PermissionDAO permissionDAO;
	
	@Inject
	RoleDAO roleDAO;

	public boolean isChckbxUserF() {
		return chckbxUserF;
	}

	public void setChckbxUserF(boolean chckbxUserF) {
		this.chckbxUserF = chckbxUserF;
	}

	public boolean isChckbxWorkerF() {
		return chckbxWorkerF;
	}

	public void setChckbxWorkerF(boolean chckbxWorkerF) {
		this.chckbxWorkerF = chckbxWorkerF;
	}

	public boolean isChckbxAdminF() {
		return chckbxAdminF;
	}

	public void setChckbxAdminF(boolean chckbxAdminF) {
		this.chckbxAdminF = chckbxAdminF;
	}

	public String getLoginF() {
		return loginF;
	}

	public void setLoginF(String loginF) {
		this.loginF = loginF;
	}

	public String getPasswordF() {
		return passwordF;
	}

	public void setPasswordF(String passwordF) {
		this.passwordF = passwordF;
	}

	public String getPasswordFR() {
		return passwordFR;
	}

	public void setPasswordFR(String passwordFR) {
		this.passwordFR = passwordFR;
	}

	public User getUser() {
		return user;
	}

	public void onLoad() throws IOException {
		HttpSession session = (HttpSession) extcontext.getSession(true);
		user = (User) session.getAttribute("editId");
		
		if(user != null) {
			loginF = user.getLogin();
			passwordF = user.getPassword();
			passwordFR = user.getPassword();
			
			if(roleBB.isUser(user)) {
				chckbxUserF = true;
			}
			if(roleBB.isWorker(user)) {
				chckbxWorkerF = true;
			}
			if(roleBB.isAdmin(user)) {
				chckbxAdminF = true;
			}
		}
	}
	
	public String saveData() {
		HttpSession session = (HttpSession) extcontext.getSession(true);
		user = (User) session.getAttribute("editId");
		admin = (User) session.getAttribute("user");
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		
		try {
			if(user != null) {
				if(passwordF.equals(passwordFR)) {
					for(User u: userDAO.getFullList()) {
						if(u.getLogin().equals(loginF) && u.getIduser() != user.getIduser()) {
							fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Istnieje użytkownik o takiej nazwie", null));
							return navigationBB.stayTheSame();
						}
					}
					user.setLogin(loginF);
					user.setPassword(passwordF);
					user.setUser2(admin);
					user.setWhenlastmodified(currentTimestamp);
					
					userDAO.update(user);
					
					if(chckbxUserF) {
						if(roleBB.doesItExist(user, roleDAO.get(3)) != null) {
							Permission permission = roleBB.doesItExist(user, roleDAO.get(3));
							permission.setWhenrevoked(null);
							permissionDAO.update(permission);
						} else {
							Permission permission = new Permission();
							permission.setRole(roleDAO.get(3));
							permission.setUser(user);
							permission.setWhengiven(currentTimestamp);
							permissionDAO.insert(permission);
						}
					} else {
						if(roleBB.doesItExist(user, roleDAO.get(3)) != null) {
							Permission permission = roleBB.doesItExist(user, roleDAO.get(3));
							if(permission.getWhenrevoked() == null) {
								permission.setWhenrevoked(currentTimestamp);
								permissionDAO.update(permission);
							}
						}
					}
					if(chckbxWorkerF) {
						if(roleBB.doesItExist(user, roleDAO.get(2)) != null) {
							Permission permission = roleBB.doesItExist(user, roleDAO.get(2));
							permission.setWhenrevoked(null);
							permissionDAO.update(permission);
						} else {
							Permission permission = new Permission();
							permission.setRole(roleDAO.get(2));
							permission.setUser(user);
							permission.setWhengiven(currentTimestamp);
							permissionDAO.insert(permission);
						}
					} else {
						if(roleBB.doesItExist(user, roleDAO.get(2)) != null) {
							Permission permission = roleBB.doesItExist(user, roleDAO.get(2));
							if(permission.getWhenrevoked() == null) {
								permission.setWhenrevoked(currentTimestamp);
								permissionDAO.update(permission);
							}
						}
					}
					if(chckbxAdminF) {
						if(roleBB.doesItExist(user, roleDAO.get(1)) != null) {
							Permission permission = roleBB.doesItExist(user, roleDAO.get(1));
							permission.setWhenrevoked(null);
							permissionDAO.update(permission);
						} else {
							Permission permission = new Permission();
							permission.setRole(roleDAO.get(1));
							permission.setUser(user);
							permission.setWhengiven(currentTimestamp);
							permissionDAO.insert(permission);
						}
					} else {
						if(roleBB.doesItExist(user, roleDAO.get(1)) != null) {
							Permission permission = roleBB.doesItExist(user, roleDAO.get(1));
							if(permission.getWhenrevoked() == null) {
								permission.setWhenrevoked(currentTimestamp);
								permissionDAO.update(permission);
							}
						}
					}
					
					String message = "Poprawnie edytowano użytkownika";
					flash.put("message", message);
					session.removeAttribute("editId");
				} else {
					fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Podane hasła się nie zgadzają", null));
					return navigationBB.stayTheSame();
				}
			} else {
				if(passwordF.equals(passwordFR)) {
					for(User u: userDAO.getFullList()) {
						if(u.getLogin().equals(loginF)) {
							fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Istnieje użytkownik o takiej nazwie", null));
							return navigationBB.stayTheSame();
						}
					}
					newUser.setLogin(loginF);
					newUser.setPassword(passwordF);
					newUser.setUser1(admin);
					newUser.setUser2(admin);

					newUser.setWhencreated(currentTimestamp);
					newUser.setWhenlastmodified(currentTimestamp);
					
					userDAO.insert(newUser);
					
					if(chckbxUserF) {
						Permission permission = new Permission();
						permission.setRole(roleDAO.get(3));
						permission.setUser(newUser);
						permission.setWhengiven(currentTimestamp);
						permissionDAO.insert(permission);
					}
					if(chckbxWorkerF) {
						Permission permission = new Permission();
						permission.setRole(roleDAO.get(2));
						permission.setUser(newUser);
						permission.setWhengiven(currentTimestamp);
						permissionDAO.insert(permission);
					}
					if(chckbxAdminF) {
						Permission permission = new Permission();
						permission.setRole(roleDAO.get(1));
						permission.setUser(newUser);
						permission.setWhengiven(currentTimestamp);
						permissionDAO.insert(permission);
					}
					
					String message = "Poprawnie dodano użytkownika";
					flash.put("message", message);
				} else {
					fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Podane hasła się nie zgadzają", null));
					return navigationBB.stayTheSame();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd podczas zapisu", null));
			return navigationBB.stayTheSame();
		}

		return navigationBB.redirectToUser();
	}
	
	public void deleteUser(User userToDelete) {
	    try {
	        for (User u : userDAO.getFullList()) {
	            boolean modified = false;
	            if (u.getUser1() != null && u.getUser1().getIduser() == userToDelete.getIduser()) {
	                u.setUser1(null);
	                modified = true;
	            }
	            if (u.getUser2() != null && u.getUser2().getIduser() == userToDelete.getIduser()) {
	                u.setUser2(null);
	                modified = true;
	            }
	            if (modified) {
	                userDAO.update(u);
	            }
	        }

	        userDAO.delete(userToDelete);
	        fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Poprawnie usunięto użytkownika", null));
	    } catch (Exception e) {
	        e.printStackTrace();
	        fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie można usunąć użytkownika (posiada aktywne powiązania lub wypożyczenia)", null));
	    }
	}
}