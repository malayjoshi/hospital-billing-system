package in.jamuna.hms.entities.hospital;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="role_id")
	private RolesEntity role;
	
	@OneToMany(mappedBy="doctor")
	private List<DoctorRateEntity> rates;
	
	@OneToMany(mappedBy="doctor")
	private Set<VisitBillEntity> visitBills;

	public Set<VisitBillEntity> getVisitBills() {
		return visitBills;
	}

	public void setVisitBills(Set<VisitBillEntity> visitBills) {
		this.visitBills = visitBills;
	}

	public List<DoctorRateEntity> getRates() {
		return rates;
	}

	public void setRates(List<DoctorRateEntity> rates) {
		this.rates = rates;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public RolesEntity getRole() {
		return role;
	}

	public void setRole(RolesEntity role) {
		this.role = role;
	}

	
	
	
	
}
