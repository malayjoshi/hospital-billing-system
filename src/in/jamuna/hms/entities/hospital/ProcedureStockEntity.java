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
@Table(name="procedure_stock")
public class ProcedureStockEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="procedure_id")
	private ProcedureRatesEntity procedure;
	
	@Column(name="qty")
	private int qty;
	
	@Column(name="date")
	private Date date=new Date();

	
	public int getId() {
		return id;
	}

	public ProcedureRatesEntity getProcedure() {
		return procedure;
	}

	public void setProcedure(ProcedureRatesEntity procedure) {
		this.procedure = procedure;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	
}
