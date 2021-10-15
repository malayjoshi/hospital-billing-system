package in.jamuna.hms.controllers.payroll;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.jamuna.hms.services.hospital.PayrollService;


@Controller
@RequestMapping("/payroll")
public class ManageEmployeeController {
	
	@Autowired
	private PayrollService payrollService;
	
	private static final Logger LOGGER=Logger.getLogger(ManageEmployeeController.class.getName());
	private static final String MAIN_PAGE="/Payroll/Employees";
	
	@RequestMapping("/manage-employees-page")
	public String manageEmployeesPage(Model model) {
		try {
			model.addAttribute("employees",payrollService.getEmployees());
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return MAIN_PAGE;
	}
	
	@RequestMapping("/add-employee")
	public String addEmployee(@RequestParam(name="name") String name,@RequestParam(name="mobile") String mobile,Model model) {
		try {
			payrollService.addEmployee(name,mobile);
			
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}

		return manageEmployeesPage(model);
	}
	
	@RequestMapping("/{enable}-employee/{id}")
	public String enableDisableEmployee(@PathVariable String enable,@PathVariable int id, Model model) {
		try {
			
			if(enable.equals("enable")) {
				payrollService.enableDisableEmployee(true,id);
			}
			else if( enable.equals("disable") ){
				payrollService.enableDisableEmployee(false,id);
			}
			
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return manageEmployeesPage(model);
	}
}
