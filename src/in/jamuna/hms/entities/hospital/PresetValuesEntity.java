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
@Table(name="preset_values")
public class PresetValuesEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="type")
	private String type;
	
	@Column(name="value")
	private float value;
	
	@OneToMany(mappedBy = "day",fetch = FetchType.LAZY)
	Set<AttendanceEntity> days;
	
	@OneToMany(mappedBy = "night",fetch = FetchType.LAZY)
	Set<AttendanceEntity> nights;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public Set<AttendanceEntity> getDays() {
		return days;
	}

	public void setDays(Set<AttendanceEntity> days) {
		this.days = days;
	}

	public Set<AttendanceEntity> getNights() {
		return nights;
	}

	public void setNights(Set<AttendanceEntity> nights) {
		this.nights = nights;
	}


	
	
}
