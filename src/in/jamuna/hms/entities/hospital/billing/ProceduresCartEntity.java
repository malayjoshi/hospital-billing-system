package in.jamuna.hms.entities.hospital.billing;

import in.jamuna.hms.entities.hospital.patient.PatientEntity;

import javax.persistence.*;

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
