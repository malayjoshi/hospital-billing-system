package in.jamuna.hms.controllers.hospital.lab;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import in.jamuna.hms.services.hospital.LabService;

@Controller
@RequestMapping("/lab")
public class AddReportController {
	
	@Autowired
	LabService labService;
	
	private static final Logger LOGGER=
			Logger.getLogger(AddReportController.class.getName());

	@RequestMapping("/add-report-page")
	public String addReportPage() {
		
		return "/Lab/AddReport";
	}
	
	@RequestMapping("/add-report/get-tests")
	public String getTestsByTid(Model model,HttpServletRequest request) {
		
		try {
			int tid=Integer.parseInt(request.getParameter("tid"));
			//LOGGER.info(tid+"");
			model.addAttribute("tid",tid);
			model.addAttribute("tests",labService.getTestsWithParametersByTid(tid) );
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return "/Lab/AddReport";
	}
	
	@RequestMapping("/add-report")
	public String addReport(Model model,HttpServletRequest request) {
		
		try {
			boolean saved=labService.saveReport(request);
			if(saved)
				model.addAttribute("successMessage", "Report saved!");
			else
				model.addAttribute("errorMessage", "A report is already saved!");
			model.addAttribute("tid",request.getParameter("tid"));
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return "/Lab/AddReport";
	}
	
}
