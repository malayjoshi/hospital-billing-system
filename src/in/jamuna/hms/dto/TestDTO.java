package in.jamuna.hms.dto;

import in.jamuna.hms.dto.common.CommonIdAndNameDto;

import java.util.List;

public class TestDTO extends CommonIdAndNameDto {
	private CommonIdAndNameDto category;
	private List<TestParameterDTO> parameters;
	private List<CommonIdAndNameDto> values;
	
	public List<CommonIdAndNameDto> getValues() {
		return values;
	}

	public void setValues(List<CommonIdAndNameDto> values) {
		this.values = values;
	}

	public List<TestParameterDTO> getParameters() {
		return parameters;
	}

	public void setParameters(List<TestParameterDTO> parameters) {
		this.parameters = parameters;
	}

	public CommonIdAndNameDto getCategory() {
		return category;
	}

	public void setCategory(CommonIdAndNameDto category) {
		this.category = category;
	}
}
