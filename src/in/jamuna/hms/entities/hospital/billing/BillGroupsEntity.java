package in.jamuna.hms.entities.hospital.billing;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="bill_groups")
public class BillGroupsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="group_name")
	private String name;
	
	@Column(name="enabled")
	private boolean enabled=true;
	
	@OneToMany(mappedBy = "billGroup")
	private Set<ProcedureRatesEntity> procedures;
	
	


	public Set<ProcedureRatesEntity> getProcedures() {
		return procedures;
	}

	public void setProcedures(Set<ProcedureRatesEntity> procedures) {
		this.procedures = procedures;
	}

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	
}
