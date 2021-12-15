package in.jamuna.hms.controllers.hospital.manager.lab_inventory;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.jamuna.hms.services.hospital.BillingService;
import in.jamuna.hms.services.hospital.ProcedureInventoryService;

@Controller
@RequestMapping("/manager/lab-inventory")
public class LabInventoryController {
	
	@Autowired
	BillingService billingService;
	@Autowired
	ProcedureInventoryService inventoryService;
	
	
	private static final Logger LOGGER=Logger.getLogger(LabInventoryController.class.getName());
	private static final String PROCEDURE_INV_PAGE="/Manager/LabInventory/ProceduresToggle";
	private static final String STOCK_EDIT_PAGE="/Manager/LabInventory/EditStock";
	
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
	
	@RequestMapping("/stock-edit-page")
	public String stockEditPage(Model model) {
		try {
			model.addAttribute("procedures",billingService.getAllStockEnabledAndEnableProcedures());
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		return STOCK_EDIT_PAGE;
	}
	
	@RequestMapping("/add-stock")
	public String addStock(@RequestParam(name="procedure") int id,@RequestParam(name="qty") int qty,Model model) {
		try {
			long batchId=inventoryService.addStock(id,qty);
			model.addAttribute("batchId",batchId);
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return stockEditPage(model);  
	}
	
}
