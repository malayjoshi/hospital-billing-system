package in.jamuna.hms.controllers.hospital.lab;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import in.jamuna.hms.services.hospital.LabService;
import in.jamuna.hms.services.hospital.PatientService;

@Controller
@RequestMapping("/lab")
public class FindReportController {
	
	@Autowired
	LabService labService;
	
	private static final Logger LOGGER=
			Logger.getLogger(FindReportController.class.getName());
	
	@RequestMapping("/find-report-page")
	public String findReportPage() {
		return "/Lab/FindReport";
	}
	
	@RequestMapping("/find-report")
	public String findReportByNameAndAge(Model model,HttpServletRequest request) {
		try {
			String fname=request.getParameter("fname");
			String lname=request.getParameter("lname");
			int age=Integer.parseInt(request.getParameter("age"));
			model.addAttribute("bills",labService.findReportsByNameAndAge(fname,lname,age));
			
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		return "/Lab/FindReport";
	}
	
}
