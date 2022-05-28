package in.jamuna.hms.dto.payroll;

import in.jamuna.hms.entities.hospital.EmployeesTotalEntity;

public class AttendancePerEmployeeDTO {
	EmployeesTotalEntity employee;
	long fulls;
	long halfs;
	
	public EmployeesTotalEntity getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeesTotalEntity employee) {
		this.employee = employee;
	}
	public long getFulls() {
		return fulls;
	}
	public void setFulls(long l) {
		this.fulls = l;
	}
	public long getHalfs() {
		return halfs;
	}
	public void setHalfs(long halfs) {
		this.halfs = halfs;
	}
	
	
	
}
