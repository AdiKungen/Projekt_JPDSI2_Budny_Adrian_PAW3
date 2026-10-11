package jsf.course.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jsf.course.dao.UserDAO;
import jsf.course.entities.User;

@Named
@RequestScoped
public class UserListBB {
	
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

	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	UserDAO userDAO;
	
	public List<User> getFullList(){
		return userDAO.getFullList();
	}
	
	public List<User> getList(){
		List<User> list = null;
		
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (login != null && login.length() > 0){
			searchParams.put("login", login);
		}
		
		list = userDAO.getList(searchParams);
		
		return list;
	}
}
