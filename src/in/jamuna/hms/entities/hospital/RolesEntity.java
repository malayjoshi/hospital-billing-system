package in.jamuna.hms.entities.hospital;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="roles")
public class RolesEntity {
	@Override
	public String toString() {
		return "RolesEntity [roleId=" + roleId + ", role=" + role + ", employees=" + employees + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="role_id")
	private int roleId;
	
	@Column(name="role")
	private String role;
	
	@OneToMany(mappedBy="role")
	Set<EmployeeEntity> employees;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<EmployeeEntity> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<EmployeeEntity> employees) {
		this.employees = employees;
	}
	
	
	
}
