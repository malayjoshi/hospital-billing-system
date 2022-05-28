package in.jamuna.hms.controllers.payroll;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.jamuna.hms.services.hospital.PayrollService;

@Controller
@RequestMapping("/payroll")
public class AddAttendanceController {

	@Autowired
	private PayrollService payrollService;
	
	private static final Logger LOGGER=Logger.getLogger(AddAttendanceController.class.getName());
	
	private static final String MAIN_PAGE="/Payroll/AddAttendance";
	
	
	// for not recognizing date
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    sdf.setLenient(true);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	
	@RequestMapping("/add-attendance-page")
	public String addAttendancePage() {
		return MAIN_PAGE;
	}
	
	@RequestMapping("/add-attendance/get-employees")
	public String getEmployees(@RequestParam(name="date") String date,Model model) {
		
		try {
			model.addAttribute("date", date);
			model.addAttribute("presets", payrollService.getPresets());
			model.addAttribute("employees", payrollService.getEnabledEmployees());
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return MAIN_PAGE;
	}
	
	@RequestMapping("/add-attendance-sheet")
	public String addAttendance(@RequestParam(name="date") Date date,HttpServletRequest req,Model model) {

		try {
			boolean added=payrollService.addAttendance(req,date);
			
			if(added) {
				model.addAttribute("success", "Time-sheet added!");
			}
			else {
				model.addAttribute("error", "Time-sheet already exists for the given date!");
			}
			
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return MAIN_PAGE;
	}
	
	
}
