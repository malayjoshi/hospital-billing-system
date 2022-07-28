package in.jamuna.hms.controllers.hospital.lab;

import in.jamuna.hms.services.hospital.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Controller
@RequestMapping("/lab")
public class AddTestController {
	
	@Autowired
	LabService labService;
	
	private static final Logger LOGGER=
			Logger.getLogger(AddTestController.class.getName());
	
	@RequestMapping("/add-test-page")
	public String addTestPage(Model model) {
		try {
			model.addAttribute("tests", labService.getAllTests() );
		}catch(Exception e) {
			
			LOGGER.info(e.toString());
		}
		
		return "/Lab/AddTest";
	}
	
	@RequestMapping("add-test")
	public String addTest(Model model,HttpServletRequest request) {
		try {
			labService.addTest(request);
			model.addAttribute("message","Test added successfully");
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return addTestPage(model);
	}
	
}
