package in.jamuna.hms.dto;

import java.util.List;

import in.jamuna.hms.dto.common.CommonIdAndNameDto;

public class LabReportByCategoryDTO extends CommonIdAndNameDto{
	private List<TestDTO> tests;

	public List<TestDTO> getTests() {
		return tests;
	}

	public void setTests(List<TestDTO> tests) {
		this.tests = tests;
	} 
	
}
