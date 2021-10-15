package in.jamuna.hms.entities.hospital;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="employees_total")
public class EmployeesTotalEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="full_name")
	private String name;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="enabled")
	private boolean enabled=true;

	@OneToMany(mappedBy = "employee",fetch = FetchType.LAZY)
	Set<AttendanceEntity> attendances;
	
	
	public Set<AttendanceEntity> getAttendances() {
		return attendances;
	}

	public void setAttendances(Set<AttendanceEntity> attendances) {
		this.attendances = attendances;
	}

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	
}
