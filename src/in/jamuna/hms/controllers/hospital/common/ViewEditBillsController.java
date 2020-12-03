package in.jamuna.hms.controllers.hospital.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import in.jamuna.hms.services.hospital.BillingService;
import in.jamuna.hms.services.hospital.EmployeeService;
import in.jamuna.hms.services.hospital.PatientService;

@Controller
@RequestMapping({"/manager/bills","/receptionist/bills"})
public class ViewEditBillsController {

	@Autowired
	PatientService patientService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	BillingService billingService;
	
	@RequestMapping("/visit-bills-page")
	public String visitBillsPage(Model model) {
		model.addAttribute("doctors",employeeService.getAllDoctors());
		model.addAttribute("visitTypes",employeeService.getAllVisitTypes());
		
		return "Common/VisitBills";
	}
	
}
