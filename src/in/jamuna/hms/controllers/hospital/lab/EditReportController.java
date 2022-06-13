package in.jamuna.hms.controllers.hospital.lab;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import in.jamuna.hms.services.hospital.LabService;

@Controller
@RequestMapping("/lab")
public class EditReportController {
	
	@Autowired
	LabService labService;
	
	
	private static final Logger LOGGER=
			Logger.getLogger(EditReportController.class.getName());
	
	@RequestMapping("/edit-report-page")
	public String editReportPage() {
		return "/Lab/EditReport";
	}
	
	@RequestMapping("edit-report/get-tests")
	public String getTests(Model model,HttpServletRequest request) {
		
		try {
			int tid=Integer.parseInt(request.getParameter("tid"));
			model.addAttribute("tid",tid);
			model.addAttribute("tests",labService.getTestsWithParametersByTid(tid) );
			
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return "/Lab/EditReport";
	}
	
	@RequestMapping("/edit-report/change-{id}/tid-{tid}")
	public String changeValue(@PathVariable int id,@PathVariable int tid,
			Model model,HttpServletRequest request) {
		
		try {
			labService.changeValueOfTestParameterById(id,request.getParameter("value"));
			
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return "redirect:/lab/edit-report/get-tests?tid="+tid;
	}
}
