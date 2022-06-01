package in.jamuna.hms.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.jamuna.hms.dto.cart.CartItemDTO;



public class BillDTO {
	private int tid;
	private String doctor;
	private List<CartItemDTO> billItems = new ArrayList<CartItemDTO>();
	
	
	public List<CartItemDTO> getBillItems() {
		return billItems;
	}
	public void setBillItems(List<CartItemDTO> billItems) {
		this.billItems = billItems;
	}
	public BillDTO() {}
	public BillDTO(int tid, String doctor, String patient, String guardian, Date billingDate, BillDTO refund,
			int fees) {
		super();
		this.tid = tid;
		this.doctor = doctor;
		this.patient = patient;
		this.guardian = guardian;
		this.billingDate = billingDate;
		this.refund = refund;
		this.fees = fees;
	}
	
	
	public BillDTO(int tid, String doctor, List<CartItemDTO> billItems, String patient, String guardian,
			Date billingDate, BillDTO refund, int fees) {
		super();
		this.tid = tid;
		this.doctor = doctor;
		this.billItems = billItems;
		this.patient = patient;
		this.guardian = guardian;
		this.billingDate = billingDate;
		this.refund = refund;
		this.fees = fees;
	}
	
	public BillDTO(int tid, String doctor, List<CartItemDTO> billItems, String patient, String guardian,
			Date billingDate,  int fees) {
		super();
		this.tid = tid;
		this.doctor = doctor;
		this.billItems = billItems;
		this.patient = patient;
		this.guardian = guardian;
		this.billingDate = billingDate;
		
		this.fees = fees;
	}
	
	public BillDTO(int tid, String doctor, String patient, String guardian, Date billingDate,
			int fees) {
		super();
		this.tid = tid;
		this.doctor = doctor;
		this.patient = patient;
		this.guardian = guardian;
		this.billingDate = billingDate;
	
		this.fees = fees;
	}
	
	private String patient;
	private String guardian;
	private Date billingDate;
	private BillDTO refund=null;
	private int fees;
	
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public String getPatient() {
		return patient;
	}
	public void setPatient(String patient) {
		this.patient = patient;
	}
	public String getGuardian() {
		return guardian;
	}
	public void setGuardian(String guardian) {
		this.guardian = guardian;
	}
	public Date getBillingDate() {
		return billingDate;
	}
	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}
	public BillDTO getRefund() {
		return refund;
	}
	public void setRefund(BillDTO refund) {
		this.refund = refund;
	}
	public int getFees() {
		return fees;
	}
	public void setFees(int fees) {
		this.fees = fees;
	}
}
