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
@Table(name="procedure_bill_items")
public class ProcedureBillItemEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="tid")
	private ProcedureBillEntity bill;
	
	@ManyToOne
	@JoinColumn(name="procedure_id")
	private ProcedureRatesEntity procedure;
	
	@Column(name="rate")
	private int rate;

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

	public ProcedureRatesEntity getProcedure() {
		return procedure;
	}

	public void setProcedure(ProcedureRatesEntity procedure) {
		this.procedure = procedure;
	}

	public int getRate() {
		return rate;
	} 

	public void setRate(int rate) {
		this.rate = rate;
	}
	
	
	
}
