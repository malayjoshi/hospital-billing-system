package in.jamuna.hms.entities.hospital;

import javax.persistence.*;

@Entity
@Table(name="procedure_bill_items")
public class ProcedureBillItemEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tid")
	private ProcedureBillEntity bill;
	
	@ManyToOne(fetch=FetchType.LAZY)
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
