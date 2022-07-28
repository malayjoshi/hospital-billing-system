package in.jamuna.hms.controllers.hospital.manager.staffmanagement;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dto.common.InfoOfPage;
import in.jamuna.hms.dto.doctorrate.DoctorRateDTO;
import in.jamuna.hms.dto.employee.NewEmployeeDTO;
import in.jamuna.hms.services.hospital.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;


@Controller
@RequestMapping("/manager/staff-management")
public class StaffManagementController {

	private final EmployeeService employeeService;
	
	private static final Logger LOGGER=Logger.getLogger(StaffManagementController.class.getName());
	
	private final String addEmployeePage="/Manager/Employee/AddEmployee";

	public StaffManagementController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@RequestMapping("/add-employee-page")
	public String addEmployeePage(Model model) {
		model.addAttribute("roles",employeeService.getAllRoles());
		model.addAttribute("minRate",GlobalValues.getMinimumrate());
		
		return addEmployeePage;
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
		model.addAttribute("minRate",GlobalValues.getMinimumrate());
		
		return addEmployeePage;
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
	
	@RequestMapping("/delete-employee/{pageNo}/{id}")
	public String deleteEmployee(@PathVariable int pageNo,@PathVariable int id,HttpServletRequest request) {
		employeeService.deleteEmployee(id);
		return "redirect: "+request.getContextPath()+"/manager/staff-management/show-all-employees/"+pageNo;
	}
	
	@RequestMapping("/edit-doctor-rate-page")
	public String doctorRatePage(Model model) {
		
		try {
			model.addAttribute("minRate",GlobalValues.getMinimumrate());
			model.addAttribute("doctorRates",employeeService.getAllDoctorRates());
			model.addAttribute("visitTypes",employeeService.getAllVisitTypes());
			model.addAttribute("doctors",employeeService.getAllDoctors());
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return "/Manager/Employee/EditDoctorRate";
	}
	
	@RequestMapping("/save-doctor-rate")
	public String saveDoctorRate(DoctorRateDTO rate,Model model,HttpServletRequest request) {
		try {
			employeeService.saveDoctorRate(rate);
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		
		return "redirect:/manager/staff-management/edit-doctor-rate-page";
	}
	
	@RequestMapping("/delete-doctor-rate/{rateId}")
	public String deleteDoctorRate(@PathVariable int rateId) {
		employeeService.deleteDoctorRate(rateId);
		return "redirect:/manager/staff-management/edit-doctor-rate-page";
	}
}
