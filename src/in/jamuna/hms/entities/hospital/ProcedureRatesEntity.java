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
@Table(name="procedure_rates")
public class ProcedureRatesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="procedure_name")
	private String procedure;
	
	@Column(name="rate")
	private int rate;
	
	@ManyToOne
	@JoinColumn(name="bill_group")
	private BillGroupsEntity billGroup;
	

	@Column(name="enabled")
	private boolean enabled=true;
	
	public String getProcedure() {
		return procedure;
	}

	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public BillGroupsEntity getBillGroup() {
		return billGroup;
	}

	public void setBillGroup(BillGroupsEntity billGroup) {
		this.billGroup = billGroup;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
	
	
	
	
}
