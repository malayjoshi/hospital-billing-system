package in.jamuna.hms.controllers.hospital.manager.staffmanagement;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dto.common.InfoOfPage;
import in.jamuna.hms.dto.employee.NewEmployeeDTO;
import in.jamuna.hms.services.hospital.EmployeeService;


@Controller
@RequestMapping("/manager/staff-management")
public class StaffManagementController {

	@Autowired
	private EmployeeService employeeService;
	
	private static final Logger LOGGER=Logger.getLogger(StaffManagementController.class.getName());
	
	private String AddEmployeePage="/Manager/Employee/AddEmployee";
	
	@RequestMapping("/add-employee-page")
	public String addEmployeePage(Model model) {
		model.addAttribute("roles",employeeService.getAllRoles());
		
		return AddEmployeePage;
	}
	
	@RequestMapping("/add-employee")
	public String addEmployee(NewEmployeeDTO employee, Model model) {
		
		boolean result=employeeService.addEmployee(employee);
		if(!result) {
			model.addAttribute("errorMessage","Another employee with same mobile and role exists.");
		}
		else {
			model.addAttribute("successMessage","Employee added!");
		}
		
		model.addAttribute("roles",employeeService.getAllRoles());
		
		return AddEmployeePage;
	}
	
	@RequestMapping(value={"/show-all-employees","/show-all-employees/{pageNum}"})
	public String employeeByPage(@PathVariable(required = false) Integer pageNum,Model model) {
		
		InfoOfPage info=new InfoOfPage();
		if(pageNum==null)
			pageNum=1;
		
		info.setCurrentPage(pageNum);
		info.setPerPage(GlobalValues.getPerpage());
		info.setTotalPages( (int)Math.ceil(employeeService.getTotalEmployees()*1.0/GlobalValues.getPerpage() ) );

		LOGGER.info("total pages:"+info.getTotalPages());
		model.addAttribute("employees",employeeService.getEmployeesByPage(pageNum,GlobalValues.getPerpage()));
		model.addAttribute("info",info);
		
		return "/Manager/Employee/ShowAllEmployees";
	}
	
}
