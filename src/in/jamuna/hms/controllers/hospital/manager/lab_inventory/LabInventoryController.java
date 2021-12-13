package in.jamuna.hms.controllers.hospital.manager.lab_inventory;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import in.jamuna.hms.services.hospital.BillingService;

@Controller
@RequestMapping("/manager/lab-inventory")
public class LabInventoryController {
	
	@Autowired
	BillingService billingService;

	private static final Logger LOGGER=Logger.getLogger(LabInventoryController.class.getName());
	private static final String PROCEDURE_INV_PAGE="/Manager/LabInventory/ProceduresToggle";
	
	@RequestMapping("/inventory-toggle-page")
	public String labInventoryPage(Model model) {
		
		try {
			model.addAttribute("procedures", billingService.getAllEnabledProcedures() );
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return PROCEDURE_INV_PAGE; 
		
	}
	
	@RequestMapping("/procedure-stock-tracking/{id}/{toggle}")
	public String toggleStockTracking(@PathVariable(name="id") int id,@PathVariable(name="toggle") String toggle,Model model) {
		
		try {
			if(toggle.equals("enable")) {
				billingService.toggleStockTrackingForProcedure(id,true);
			}
			else if(toggle.equals("disable")) {
				billingService.toggleStockTrackingForProcedure(id,false);
			}
				
			model.addAttribute("procedures", billingService.getAllEnabledProcedures() );
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return PROCEDURE_INV_PAGE;
		
	}
	
}
