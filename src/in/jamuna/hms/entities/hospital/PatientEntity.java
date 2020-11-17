package in.jamuna.hms.entities.hospital;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="patients")
public class PatientEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="pid")
	private int id;
	
	@Column(name="fname")
	private String fname;
	
	@Column(name="lname")
	private String lname;
	
	@Column(name="age")
	private int age;
	
	@Column(name="sex")
	private String sex;
	
	@Column(name="guardian")
	private String guardian;
	
	@Column(name="address")
	private String address;
	
	@Column(name="first_date_of_visit")
	private Date firstDateOfVisit=new Date();
	
	@Column(name="mobile")
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

	
	@Override
	public String toString() {
		return "PatientEntity [id=" + id + ", fname=" + fname + ", lname=" + lname + ", age=" + age + ", sex=" + sex
				+ ", guardian=" + guardian + ", address=" + address + ", firstDateOfVisit=" + firstDateOfVisit
				+ ", mobile=" + mobile + "]";
	}

	public Date getFirstDateOfVisit() {
		return firstDateOfVisit;
	}

	public void setFirstDateOfVisit(Date firstDateOfVisit) {
		this.firstDateOfVisit = firstDateOfVisit;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	
}
