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
	
	@OneToMany(mappedBy = "billGroup",fetch = FetchType.EAGER)
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
