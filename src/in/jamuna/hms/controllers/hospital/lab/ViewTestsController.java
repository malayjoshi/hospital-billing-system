package in.jamuna.hms.controllers.hospital.lab;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import in.jamuna.hms.services.hospital.BillingService;


@Controller
@RequestMapping("/lab")
public class ViewTestsController {

	@Autowired
	BillingService billingService;
	
	private static final Logger LOGGER=Logger.getLogger(ViewTestsController.class.getName());
	
	
	@RequestMapping("/view-tests")
	public String ViewTestPage(Model model) {
		try {
			int hours=24;
			//get all bills of last 24 hours
			model.addAttribute("bills",billingService.getLabBillOfLastHours(hours));
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return "/Lab/ViewTests";
	} 
	
}
