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
public class AddCategoryToTestController {
	@Autowired
	LabService labService;
	
	private static final Logger LOGGER=
			Logger.getLogger(AddCategoryToTestController.class.getName());
	
	@RequestMapping("/add-category-test-page")
	public String AddCategoryTestPage(Model model) {
		try {
			model.addAttribute("tests",labService.getAllTestsWithCategory());
			model.addAttribute("categories",labService.getAllLabCategories());
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return "/Lab/AddCatToTest";
	}
	
	@RequestMapping("/add-category-to-test/{testId}")
	public String addCatToTest(@PathVariable int testId,HttpServletRequest req) {
		try {
			labService.addCatToTest(testId, Integer.parseInt(req.getParameter("category")) );
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return "redirect:/lab/add-category-test-page";
	}
	
}
