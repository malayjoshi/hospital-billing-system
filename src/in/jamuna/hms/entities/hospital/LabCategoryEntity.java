package in.jamuna.hms.entities.hospital;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="lab_category")
public class LabCategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy = "category")
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
