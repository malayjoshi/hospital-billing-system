package in.jamuna.hms.dto.cart;

import lombok.Getter;
import lombok.Setter;

public class CartItemDTO {
	private int id;
	private String name;
	private int rate;
	private boolean enabled;


	@Getter
	@Setter
	private boolean lowStock;

	@Getter
	@Setter
	private boolean stockTracking;
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	
	
}
