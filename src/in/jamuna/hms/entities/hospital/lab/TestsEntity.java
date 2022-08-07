package in.jamuna.hms.entities.hospital.lab;

import in.jamuna.hms.entities.hospital.billing.ProcedureBillEntity;
import in.jamuna.hms.entities.hospital.lab.TestParametersEntity;

import javax.persistence.*;

@Entity
@Table(name="tests")
public class TestsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tid")
	private ProcedureBillEntity bill;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parameter_id")
	private TestParametersEntity parameter;
	
	@Column(name="value")
	private String value;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProcedureBillEntity getBill() {
		return bill;
	}

	public void setBill(ProcedureBillEntity bill) {
		this.bill = bill;
	}

	public TestParametersEntity getParameter() {
		return parameter;
	}

	public void setParameter(TestParametersEntity parameter) {
		this.parameter = parameter;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	
}
