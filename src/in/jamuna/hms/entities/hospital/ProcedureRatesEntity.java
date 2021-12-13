package in.jamuna.hms.entities.hospital;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	
	@ManyToOne
	@JoinColumn(name="bill_group")
	private BillGroupsEntity billGroup;
	

	@Column(name="enabled")
	private boolean enabled=true;
	
	@OneToMany(mappedBy = "test",fetch = FetchType.EAGER)
	private List<TestParametersEntity> parameters;
	
	@ManyToOne
	@JoinColumn(name="lab_category")
	private LabCategoryEntity category;
	
	@Column(name="stock_tracking")
	private Boolean stockTracking=false;
	


	public Boolean getStockTracking() {
		return stockTracking;
	}

	public void setStockTracking(Boolean stockTracking) {
		this.stockTracking = stockTracking;
	}

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
