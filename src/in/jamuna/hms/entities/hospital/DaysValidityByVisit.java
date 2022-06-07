package in.jamuna.hms.entities.hospital;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="days_visit_doctor")
public class DaysValidityByVisit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="visit_id")
	private VisitTypeEntity visit;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="emp_id")
	private EmployeeEntity doctor;
	
	@Column(name="days")
	private int days;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public VisitTypeEntity getVisit() {
		return visit;
	}

	public void setVisit(VisitTypeEntity visit) {
		this.visit = visit;
	}

	public EmployeeEntity getDoctor() {
		return doctor;
	}

	public void setDoctor(EmployeeEntity doctor) {
		this.doctor = doctor;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}
	
	
	
}
