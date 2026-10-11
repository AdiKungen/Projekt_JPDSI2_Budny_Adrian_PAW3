package jsf.course.utils;

import java.util.ArrayList;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jsf.course.dao.PermissionDAO;
import jsf.course.dao.RoleDAO;
import jsf.course.dao.UserDAO;
import jsf.course.entities.Permission;
import jsf.course.entities.Role;
import jsf.course.entities.User;

@Named
@RequestScoped
public class RoleBB {
	
	@Inject
	ExternalContext extcontext;
	
	@EJB
	RoleDAO roleDAO;
	
	@EJB
	UserDAO userDAO;
	
	@EJB
	PermissionDAO permissionDAO;
	
	public List<Role> whatRoles(User user) {
		Integer id = user.getIduser();
		
		List<Permission> lista = permissionDAO.getListForUser(id);
		
		List<Role> role = new ArrayList<>();
		
		
		for(Permission p: lista) {
			if(p.getWhenrevoked() == null) {
				if(p.getRole().getIsactive().equals("1")) {
					role.add(p.getRole());
				}
				
			}
		}
		
		return role;
	}
	
	public boolean isAdmin(User user) {
		if(user != null) {
			List<Role> role = whatRoles(user);
			
			for(Role r: role) {
				if(r.getIdrole() == 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isWorker(User user) {
		if(user != null) {
			List<Role> role = whatRoles(user);
			
			for(Role r: role) {
				if(r.getIdrole() == 2) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isUser(User user) {
		if(user != null) {
			List<Role> role = whatRoles(user);
			
			for(Role r: role) {
				if(r.getIdrole() == 3) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Permission doesItExist(User user, Role role) {
		for(Permission p: permissionDAO.getFullList()) {
			if(p.getRole().getIdrole() == role.getIdrole() && p.getUser().getIduser() == user.getIduser()) {
				return p;
			}
		}
		
		return null;
	}
}
