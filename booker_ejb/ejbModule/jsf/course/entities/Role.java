package jsf.course.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idrole;

	private String isactive;

	private String name;

	private Timestamp whencreated;

	private Timestamp whendisabled;

	//bi-directional many-to-one association to Permission
	@OneToMany(mappedBy="role")
	private List<Permission> permissions;

	public Role() {
	}

	public int getIdrole() {
		return this.idrole;
	}

	public void setIdrole(int idrole) {
		this.idrole = idrole;
	}

	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getWhencreated() {
		return this.whencreated;
	}

	public void setWhencreated(Timestamp whencreated) {
		this.whencreated = whencreated;
	}

	public Timestamp getWhendisabled() {
		return this.whendisabled;
	}

	public void setWhendisabled(Timestamp whendisabled) {
		this.whendisabled = whendisabled;
	}

	public List<Permission> getPermissions() {
		return this.permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public Permission addPermission(Permission permission) {
		getPermissions().add(permission);
		permission.setRole(this);

		return permission;
	}

	public Permission removePermission(Permission permission) {
		getPermissions().remove(permission);
		permission.setRole(null);

		return permission;
	}

}