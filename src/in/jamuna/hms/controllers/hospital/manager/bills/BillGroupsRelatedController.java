package in.jamuna.hms.controllers.hospital.manager.bills;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.services.hospital.BillingService;
import in.jamuna.hms.services.hospital.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;


@Controller
@RequestMapping("/manager/bills")
public class BillGroupsRelatedController {
	// http://localhost:8080/hms-spring/manager/bills/bill-groups-page
	final
	BillingService billingService;

	@Autowired
	private
	LabService labService;
	
	private static final Logger LOGGER=Logger.getLogger(BillGroupsRelatedController.class.getName());
	
	private static final String redirectProceduresPage="redirect:/manager/bills/procedures-page";


	public BillGroupsRelatedController(BillingService billingService) {
		this.billingService = billingService;
	}

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
	
	@RequestMapping("/procedures-page")
	public String proceduresPage(Model model) {
		try {
			model.addAttribute("groups", billingService.getAllEnabledBillGroups());
			model.addAttribute("procedures", billingService.getAllProcedures());
			model.addAttribute("filterTypes", GlobalValues.getProcedureFilterTypes());
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		return "/Manager/Billing/Procedures";
	}

	@RequestMapping("/procedure/{type}")
	public String proceduresByFilter( @PathVariable String type ,Model model) {
		try {
			model.addAttribute("groups", billingService.getAllEnabledBillGroups());
			model.addAttribute("procedures", billingService.getAllProceduresByFilter(type));
			model.addAttribute("filterTypes", GlobalValues.getProcedureFilterTypes());
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		return "/Manager/Billing/Procedures";
	}

	
	@RequestMapping("/add-procedure")
	public String addProcedure(@RequestParam(name="billGroup") int groupId,
			@RequestParam(name="procedure") String procedure,
			@RequestParam(name="rate") int rate ) {
		try {
			billingService.addProcedure(groupId,procedure,rate);
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return redirectProceduresPage;
	}
	
	@RequestMapping("/edit-procedure/{id}")
	public String saveProcedureRate( @PathVariable int id,@RequestParam(name="rate") int rate ) {
		try {
			billingService.saveProcedureRate(id,rate);
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return redirectProceduresPage;
	}
	
	@RequestMapping("procedure/{id}/{action}")
	public String toggleEnableProcedure(@PathVariable int id,@PathVariable String action) {
		try {
			billingService.toggleEnableProcedure(id, action);
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return redirectProceduresPage;
	}

	
}
