package in.jamuna.hms.entities.hospital;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="procedures_cart")
public class ProceduresCartEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pid")
	PatientEntity patient;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="procedure_id")
	ProcedureRatesEntity procedure;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}

	public ProcedureRatesEntity getProcedure() {
		return procedure;
	}

	public void setProcedure(ProcedureRatesEntity procedure) {
		this.procedure = procedure;
	}
	
	
	
}
