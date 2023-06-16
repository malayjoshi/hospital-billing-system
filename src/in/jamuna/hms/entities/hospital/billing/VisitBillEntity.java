package in.jamuna.hms.entities.hospital.billing;

import in.jamuna.hms.entities.hospital.employees.EmployeeEntity;
import in.jamuna.hms.entities.hospital.patient.PatientEntity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="visit_bill")
public class VisitBillEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tid")
	private int tid;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pid")
	private PatientEntity patient;
	
	
	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="visit_id")
	private VisitTypeEntity visitType;
	
	public VisitTypeEntity getVisitType() {
		return visitType;
	}

	public void setVisitType(VisitTypeEntity visitType) {
		this.visitType = visitType;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="doctor_id")
	private EmployeeEntity doctor;
	
	@Column(name="date_of_billing")
	private Date billingDate=new Date();
	
	@Column(name="fees")
	private int fees;
	
	@OneToOne(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="refund_tid",nullable = true)
	private VisitBillEntity refundBill;

	@OneToOne(mappedBy="refundBill",orphanRemoval = true,fetch=FetchType.LAZY)
	private VisitBillEntity refund;

		

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	

	public EmployeeEntity getDoctor() {
		return doctor;
	}

	public void setDoctor(EmployeeEntity doctor) {
		this.doctor = doctor;
	}

	public Date getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

	public int getFees() {
		return fees;
	}

	public void setFees(int fees) {
		this.fees = fees;
	}


	public VisitBillEntity getRefundBill() {
		return refundBill;
	}

	public void setRefundBill(VisitBillEntity refundBill) {
		this.refundBill = refundBill;
	}

	public VisitBillEntity getRefund() {
		return refund;
	}

	public void setRefund(VisitBillEntity refund) {
		this.refund = refund;
	}
	
}
