package in.jamuna.hms.dto;

public class BillGroupSummaryItemDTO {
	private int id;
	private int tid;
	private String patient;
	private String guardian;
	private String name;
	private int rate;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public BillGroupSummaryItemDTO(int id, int tid, String patient, String guardian, String name, int rate) {
		super();
		this.id = id;
		this.tid = tid;
		this.patient = patient;
		this.guardian = guardian;
		this.name = name;
		this.rate = rate;
	}
	
	
	
}
