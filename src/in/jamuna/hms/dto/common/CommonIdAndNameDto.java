package in.jamuna.hms.dto.common;

public class CommonIdAndNameDto {
	@Override
	public String toString() {
		return "CommonIdAndNameDto [id=" + id + ", name=" + name + "]";
	}
	private int id;
	private String name;
	
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
	public CommonIdAndNameDto(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public CommonIdAndNameDto() {}
	
}
