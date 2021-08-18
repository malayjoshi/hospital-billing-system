package in.jamuna.hms.entities.hospital;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="lab_category")
public class LabCategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy = "category",fetch = FetchType.EAGER)
	private Set<ProcedureRatesEntity> procedures;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ProcedureRatesEntity> getProcedures() {
		return procedures;
	}

	public void setProcedures(Set<ProcedureRatesEntity> procedures) {
		this.procedures = procedures;
	}
	
	
	
}
