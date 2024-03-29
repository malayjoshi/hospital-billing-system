package in.jamuna.hms.entities.hospital.lab;

import in.jamuna.hms.entities.hospital.billing.ProcedureRatesEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="test_parameters")
public class TestParametersEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="parameter_id")
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="test_id")
	private ProcedureRatesEntity test;
	
	@Column(name="parameter_name")
	private String name;
	
	@Column(name="ref_needed")
	private Character refNeeded;
	
	@Column(name="unit")
	private String unit = "";
	
	@Column(name="lower_range")
	private Float lowerRange = (float) 0;
	
	@Column(name="upper_range",nullable=true)
	private Float upperRange = (float) 0;
	
	@OneToMany(mappedBy = "parameter")
	private List<TestsEntity> tests;
	
	
	
	public List<TestsEntity> getTests() {
		return tests;
	}

	public void setTests(List<TestsEntity> tests) {
		this.tests = tests;
	}

	public void setLowerRange(Float lowerRange) {
		this.lowerRange = lowerRange;
	}

	public void setUpperRange(Float upperRange) {
		this.upperRange = upperRange;
	}

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
