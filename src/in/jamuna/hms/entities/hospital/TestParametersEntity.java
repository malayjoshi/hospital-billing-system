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
@Table(name="test_parameters")
public class TestParametersEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="parameter_id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="test_id")
	private ProcedureRatesEntity test;
	
	@Column(name="parameter_name")
	private String name;
	
	@Column(name="ref_needed")
	private Character refNeeded;
	
	@Column(name="unit",nullable=true)
	private String unit;
	
	@Column(name="lower_range",nullable=true)
	private Float lowerRange;
	
	@Column(name="upper_range",nullable=true)
	private Float upperRange;
	

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public float getLowerRange() {
		return lowerRange;
	}

	public void setLowerRange(float lowerRange) {
		this.lowerRange = lowerRange;
	}

	public float getUpperRange() {
		return upperRange;
	}

	public void setUpperRange(float upperRange) {
		this.upperRange = upperRange;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProcedureRatesEntity getTest() {
		return test;
	}

	public void setTest(ProcedureRatesEntity test) {
		this.test = test;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Character getRefNeeded() {
		return refNeeded;
	}

	public void setRefNeeded(Character refNeeded) {
		this.refNeeded = refNeeded;
	}
	
	
	
	
}
