package in.jamuna.hms.controllers.hospital.manager.bills;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.jamuna.hms.services.hospital.BillingService;


@Controller
@RequestMapping("/manager/bills")
public class BillGroupsRelatedController {
	// http://localhost:8080/hms-spring/manager/bills/bill-groups-page
	@Autowired
	BillingService billingService;
	
	private static final Logger LOGGER=Logger.getLogger(BillGroupsRelatedController.class.getName());
	
	@RequestMapping("/bill-groups-page")
	public String billGroupsPage(Model model) {
		try {
			model.addAttribute("groups",billingService.getAllBillGroups());
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return "/Manager/Billing/BillGroups";
	}
	
	@PostMapping("/add-bill-group")
	public String addBillGroup(@RequestParam(name="name") String name,Model model) {
		LOGGER.info("here");
		try {
			billingService.addBillingGroup(name);
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return "redirect:/manager/bills/bill-groups-page";
	}
	
	@RequestMapping("/bill-group/{id}/{action}")
	public String toggleEnableGroup(@PathVariable int id,@PathVariable String action) {
		try {
			billingService.toggleEnableGroup(id,action);
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		

		return "redirect:/manager/bills/bill-groups-page";
	}
	
}
