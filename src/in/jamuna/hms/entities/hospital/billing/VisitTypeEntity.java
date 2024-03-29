package in.jamuna.hms.entities.hospital.billing;

import in.jamuna.hms.entities.hospital.employees.DaysValidityByVisit;
import in.jamuna.hms.entities.hospital.employees.DoctorRateEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="visit_types")
public class VisitTypeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="visit")
	private String visit;
	
	@OneToMany(mappedBy="visitType")
	private List<DoctorRateEntity> rates;

	@OneToMany(mappedBy="visitType")
	private Set<VisitBillEntity> visitBills;
	
	public Set<VisitBillEntity> getVisitBills() {
		return visitBills;
	}

	public void setVisitBills(Set<VisitBillEntity> visitBills) {
		this.visitBills = visitBills;
	}

	@OneToMany(mappedBy="visit")
	private Set<DaysValidityByVisit> validities;
	
	public Set<DaysValidityByVisit> getValidities() {
		return validities;
	}

	public void setValidities(Set<DaysValidityByVisit> validities) {
		this.validities = validities;
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

	public String getVisit() {
		return visit;
	}

	public void setVisit(String visit) {
		this.visit = visit;
	}
	
	
}
