package in.jamuna.hms.controllers.hospital.lab;

import in.jamuna.hms.services.hospital.BillingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;


@Controller
@RequestMapping("/lab")
public class ViewTestsController {

	final
	BillingService billingService;
	
	private static final Logger LOGGER=Logger.getLogger(ViewTestsController.class.getName());

	public ViewTestsController(BillingService billingService) {
		this.billingService = billingService;
	}


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
