package in.jamuna.hms.entities.hospital;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="attendance")
public class AttendanceEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="emp_id")
	private EmployeesTotalEntity employee;
	
	@ManyToOne
	@JoinColumn(name="day")
	private PresetValuesEntity day;
	
	@ManyToOne
	@JoinColumn(name="night")
	private PresetValuesEntity night;
	
	@Column(name="date")
	private Date date=new Date();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EmployeesTotalEntity getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeesTotalEntity employee) {
		this.employee = employee;
	}

	public PresetValuesEntity getDay() {
		return day;
	}

	public void setDay(PresetValuesEntity day) {
		this.day = day;
	}

	public PresetValuesEntity getNight() {
		return night;
	}

	public void setNight(PresetValuesEntity night) {
		this.night = night;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
