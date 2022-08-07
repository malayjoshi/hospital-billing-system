package in.jamuna.hms.entities.hospital.billing;

import in.jamuna.hms.entities.hospital.lab.TestParametersEntity;
import in.jamuna.hms.entities.hospital.lab.LabCategoryEntity;
import in.jamuna.hms.entities.hospital.stock.ProcedureProductMappingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="procedure_rates")
public class ProcedureRatesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="procedure_name")
	private String procedure;
	
	@Column(name="rate")
	private int rate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bill_group")
	private BillGroupsEntity billGroup;
	

	@Column(name="enabled")
	private boolean enabled=true;
	
	@OneToMany(mappedBy = "test")
	private List<TestParametersEntity> parameters;

	@Getter
	@Setter
	@Column(name = "stock_tracking")
	private boolean stockEnabled = false;


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="lab_category")
	private LabCategoryEntity category;


	@Setter
	@Getter
	@OneToMany(mappedBy = "test")
	private List<ProcedureProductMappingEntity> mappings;



	public LabCategoryEntity getCategory() {
		return category;
	}

	public void setCategory(LabCategoryEntity category) {
		this.category = category;
	}

	public List<TestParametersEntity> getParameters() {
		return parameters;
	}

	public void setParameters(List<TestParametersEntity> parameters) {
		this.parameters = parameters;
	}

	public String getProcedure() {
		return procedure;
	}

	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public BillGroupsEntity getBillGroup() {
		return billGroup;
	}

	public void setBillGroup(BillGroupsEntity billGroup) {
		this.billGroup = billGroup;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
	
	
	
	
}
