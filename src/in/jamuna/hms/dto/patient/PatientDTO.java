package in.jamuna.hms.dto.patient;


import lombok.Getter;
import lombok.Setter;

public class PatientDTO {
	
	private int id;
	
	private String fname;
	
	private String lname;

	@Getter
	@Setter
	private String marital;
	
	private int age;
	
	private String sex;
	
	private String guardian;
	
	private String address;
	
	private String firstDateOfVisit;
	
	private String mobile;
	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getGuardian() {
		return guardian;
	}

	public void setGuardian(String guardian) {
		this.guardian = guardian;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFirstDateOfVisit() {
		return firstDateOfVisit;
	}

	public void setFirstDateOfVisit(String firstDateOfVisit) {
		this.firstDateOfVisit = firstDateOfVisit;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
