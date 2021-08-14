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
@Table(name="tests")
public class TestsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="tid")
	private ProcedureBillEntity bill;
	
	@ManyToOne
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
