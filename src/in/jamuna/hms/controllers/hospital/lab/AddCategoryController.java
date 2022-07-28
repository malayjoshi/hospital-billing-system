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
public class AddCategoryController {

	@Autowired
	LabService labService;
	
	private static final Logger LOGGER=
			Logger.getLogger(AddCategoryController.class.getName());
	
	@RequestMapping("/add-category-page")
	public String addCategoryPage(Model model) {
		try {
			model.addAttribute("categories",labService.getAllLabCategories());
		}catch (Exception e) {
			LOGGER.info(e.toString());
		}
		
		return "/Lab/AddCategory";
	} 
	
	@RequestMapping("/add-category")
	public String addCategory(HttpServletRequest request) {
		try {
			labService.addCategory(request.getParameter("category"));
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return "redirect:/lab/add-category-page";
	}
	
}
