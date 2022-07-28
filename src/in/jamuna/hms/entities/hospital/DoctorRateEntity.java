package in.jamuna.hms.entities.hospital;


import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="doctors_rate")
public class DoctorRateEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rate_id")
	private int rate_id;
	
	@Column(name="start_time")
	private Date startTime;
	
	@Column(name="end_time")
	private Date endTime;
	
	@Column(name="rate")
	private int rate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="emp_id")
	private EmployeeEntity doctor;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="visit_id")
	private VisitTypeEntity visitType;

	public int getRate_id() {
		return rate_id;
	}

	public void setRate_id(int rate_id) {
		this.rate_id = rate_id;
	}

	
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}



	public EmployeeEntity getDoctor() {
		return doctor;
	}

	public void setDoctor(EmployeeEntity doctor) {
		this.doctor = doctor;
	}

	public VisitTypeEntity getVisitType() {
		return visitType;
	}

	public void setVisitType(VisitTypeEntity visitType) {
		this.visitType = visitType;
	}
	
	
}
