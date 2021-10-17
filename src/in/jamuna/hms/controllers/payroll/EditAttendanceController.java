package in.jamuna.hms.controllers.payroll;

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

import in.jamuna.hms.services.hospital.PayrollService;

@Controller
@RequestMapping("/payroll")
public class EditAttendanceController {
	@Autowired
	private PayrollService payrollService;
	
	private static final Logger LOGGER=Logger.getLogger(EditAttendanceController.class.getName());
	
	private static final String MAIN_PAGE="/Payroll/EditAttendance";
	
	// for not recognizing date
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    sdf.setLenient(true);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@RequestMapping("/edit-attendance-page")
	public String editAttendancePage() {
		return MAIN_PAGE;
	}
	
	@RequestMapping("/get-attendance")
	public String getAttendance(@RequestParam(name="date") Date date, Model model) {
		try {
			model.addAttribute("attendances", payrollService.getAttendanceByDate(date));
			model.addAttribute("presets", payrollService.getPresets());
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return MAIN_PAGE;
	}
}