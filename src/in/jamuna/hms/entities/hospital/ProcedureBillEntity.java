package in.jamuna.hms.entities.hospital;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="procedure_bill")
public class ProcedureBillEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tid")
	private int tid;
	
	@ManyToOne
	@JoinColumn(name="pid")
	PatientEntity patient;
	
	@ManyToOne
	@JoinColumn(name="doctor_id")
	EmployeeEntity doctor;
	
	@Column(name="date")
	private Date date=new Date();
	
	@Column(name="total")
	private int total;
	
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="refund_tid",nullable = true)
	private ProcedureBillEntity refundBill;

	@OneToOne(mappedBy="refundBill",orphanRemoval = true)
	private ProcedureBillEntity refund;
	
	
	public ProcedureBillEntity getRefundBill() {
		return refundBill;
	}

	public void setRefundBill(ProcedureBillEntity refundBill) {
		this.refundBill = refundBill;
	}

	public ProcedureBillEntity getRefund() {
		return refund;
	}

	public void setRefund(ProcedureBillEntity refund) {
		this.refund = refund;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}

	public EmployeeEntity getDoctor() {
		return doctor;
	}

	public void setDoctor(EmployeeEntity doctor) {
		this.doctor = doctor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	
	
	
}
