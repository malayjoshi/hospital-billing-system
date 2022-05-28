package in.jamuna.hms.controllers.hospital.manager.reports;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.services.hospital.BillingService;
import in.jamuna.hms.services.hospital.EmployeeService;

@Controller
@RequestMapping("/manager/reports")
public class ReportsController {

	@Autowired
	BillingService billingService;
	@Autowired
	EmployeeService employeeService;
	
	private static final Logger LOGGER=Logger.getLogger(ReportsController.class.getName());
	private static final String PAGE_BILL_GROUP="/Manager/Reports/BillGroups";
	private static final String PAGE_CONSULTATIONS="/Manager/Reports/Visit";
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    sdf.setLenient(true);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@RequestMapping("/bill-group-report-page")
	public String billGroupsPage(Model model) {
		try {
			model.addAttribute("groups",billingService.getAllBillGroups());
			model.addAttribute("summaryType", GlobalValues.getSummaryType());
			model.addAttribute("doctors", employeeService.getAllDoctors());
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return PAGE_BILL_GROUP;
	}
	
	@RequestMapping("/get-bill-group-report")
	public String getBillGroupReport(@RequestParam(name="group_id") int groupId,@RequestParam(name="date") Date date,
			@RequestParam(name="doctor_id") int empId, @RequestParam(name="type") String type, Model model) {
		try {
			model.addAttribute("rows", billingService.getBillGroupReportByGroupIdAndDateAndDoctorAndType(groupId,date,empId,type) );
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}

		return billGroupsPage(model);
	}
	
	
	@RequestMapping("/visit-report-page")
	public String visitPage(Model model) {
		try {
			model.addAttribute("summaryType", GlobalValues.getSummaryType());
			model.addAttribute("doctors", employeeService.getAllDoctors());
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return PAGE_CONSULTATIONS;
	}
	
	
	@RequestMapping("/get-visits-report")
	public String getVisitReport(@RequestParam(name="date") Date date,
			@RequestParam(name="doctor_id") int empId, @RequestParam(name="type") String type, Model model) {
		
		try {
			model.addAttribute("report", billingService.getVisitReportByDoctorAndDateAndType(empId,date,type));
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return visitPage(model);
	}
	
	
	
	
	
	
}
