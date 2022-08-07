package in.jamuna.hms.entities.hospital.billing;

import in.jamuna.hms.entities.hospital.lab.TestsEntity;
import in.jamuna.hms.entities.hospital.employees.EmployeeEntity;
import in.jamuna.hms.entities.hospital.patient.PatientEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="procedure_bill")
public class ProcedureBillEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tid")
	private int tid;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pid")
	PatientEntity patient;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="doctor_id")
    EmployeeEntity doctor;
	
	@Column(name="date")
	private Date date=new Date();
	
	@Column(name="total")
	private int total;
	
	@OneToOne(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="refund_tid",nullable = true)
	private ProcedureBillEntity refundBill;

	@OneToOne(mappedBy="refundBill",orphanRemoval = true,fetch=FetchType.LAZY)
	private ProcedureBillEntity refund;
	
	@OneToMany(mappedBy = "bill")
	Set<ProcedureBillItemEntity> billItems;
	
	@OneToMany(mappedBy = "bill")
	Set<TestsEntity> tests;
	

	public Set<TestsEntity> getTests() {
		return tests;
	}

	public void setTests(Set<TestsEntity> tests) {
		this.tests = tests;
	}



	public Set<ProcedureBillItemEntity> getBillItems() {
		return billItems;
	}

	public void setBillItems(Set<ProcedureBillItemEntity> billItems) {
		this.billItems = billItems;
	}

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
