package in.jamuna.hms.dto.reports;

import in.jamuna.hms.dto.common.CommonIdAndNameDto;

import java.util.List;

public class LabReportByCategoryDTO extends CommonIdAndNameDto{
	private List<TestDTO> tests;

	public List<TestDTO> getTests() {
		return tests;
	}

	public void setTests(List<TestDTO> tests) {
		this.tests = tests;
	} 
	
}
