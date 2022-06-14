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
public class EditTestController {
	@Autowired
	LabService labService;
	
	private static final Logger LOGGER=
			Logger.getLogger(EditTestController.class.getName());
	
	@RequestMapping("/edit-test-page")
	public String EditTestPage(Model model) {
		try {
			model.addAttribute("tests",labService.getAllTests());
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return "/Lab/EditTest";
	}
	
	@RequestMapping("/edit-test/get-test")
	public String getTest(Model model,HttpServletRequest req) {
		try {
			model.addAttribute("test",labService.getTestByTestId( Integer.parseInt(req.getParameter("test")) ));
			model.addAttribute("parameters",
					labService.getParametersByTestId(Integer.parseInt(req.getParameter("test"))) );

			model.addAttribute("tests",labService.getAllTests());
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return "/Lab/EditTest";
	}
	
	@RequestMapping("/edit-test/{type}/{testId}/{paraId}")
	public String editTest(
			@PathVariable String type,@PathVariable int testId,@PathVariable int paraId,HttpServletRequest req) {
		try {
		
			labService.saveParameterById(type,paraId,req);
			
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return "redirect:/lab/edit-test/get-test?test="+testId;
	}
	
	
	
}
