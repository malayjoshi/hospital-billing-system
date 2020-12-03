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
@Table(name="visit_bill")
public class VisitBillEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tid")
	private int tid;
	
	@ManyToOne
	@JoinColumn(name="pid")
	private PatientEntity patient;
	
	
	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}

	@ManyToOne
	@JoinColumn(name="visit_id")
	private VisitTypeEntity visitType;
	
	public VisitTypeEntity getVisitType() {
		return visitType;
	}

	public void setVisitType(VisitTypeEntity visitType) {
		this.visitType = visitType;
	}

	@ManyToOne
	@JoinColumn(name="doctor_id")
	private EmployeeEntity doctor;
	
	@Column(name="date_of_billing")
	private Date billingDate=new Date();
	
	@Column(name="fees")
	private int fees;
	
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="refund_against",nullable = true)
	private VisitBillEntity refundAgainstBill;

	@OneToOne(mappedBy="refundAgainstBill",orphanRemoval = true)
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

	public VisitBillEntity getRefundAgainstBill() {
		return refundAgainstBill;
	}

	public void setRefundAgainstBill(VisitBillEntity refundAgainstBill) {
		this.refundAgainstBill = refundAgainstBill;
	}

	public VisitBillEntity getRefund() {
		return refund;
	}

	public void setRefund(VisitBillEntity refund) {
		this.refund = refund;
	}
	
}
