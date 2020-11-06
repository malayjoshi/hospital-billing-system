package in.jamuna.hms.dto.login;

import java.io.Serializable;

public class SessionDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int empId;
	private String name;
	private String role;
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	

}
