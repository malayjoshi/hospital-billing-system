package in.jamuna.hms.entities.hospital;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="employees")
public class EmployeeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="emp_id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="password")
	private String password;
	
	@Column(name="enabled")
	private boolean enabled=true;//0- not reg, 1-reg
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private RolesEntity role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public RolesEntity getRole() {
		return role;
	}

	public void setRole(RolesEntity role) {
		this.role = role;
	}

	
	
	
	
}
