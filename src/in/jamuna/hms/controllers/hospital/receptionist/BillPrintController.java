package in.jamuna.hms.controllers.hospital.receptionist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.services.hospital.BillingService;

@Controller
@RequestMapping("/receptionist")
public class BillPrintController {

	@Autowired
	BillingService billingService;
	
	@RequestMapping("/print-page")
	public String printPage() {
		return "Receptionist/Print/PrintBills";
	}
	
	@RequestMapping("/check-tid/{type}")
	public String checkTid(@PathVariable String type,@RequestParam(name = "tid") int tid,
			Model model) {
		
		boolean result=false;
		if(type.equals("visit"))
			result=billingService.findVisitBillByTid(tid)==null ? false:true;
		else if(type.equals("procedure"))
			result=billingService.findProcedureBillByTid(tid)==null ? false:true;
		
		
		if(result)
		{
			model.addAttribute("type",type);
			model.addAttribute("tid", tid);
		}else {
			model.addAttribute("errorMessage","Bill not found!");
		}
		
		return "Receptionist/Print/PrintBills";
	}
	
	@RequestMapping("/print-slip/{tid}")
	public String printSlip(@PathVariable int tid, Model model) {
		model.addAttribute("heading",GlobalValues.getHeading());
		model.addAttribute("subHeader",GlobalValues.getSubheader());
		model.addAttribute("footer", GlobalValues.getFooter());
		model.addAttribute("bill",billingService.findVisitBillByTid(tid));
		return "Receptionist/Print/Slip";
	}
	
	@RequestMapping("/print-bill/{type}/{tid}")
	public String printBill(@PathVariable String type,@PathVariable int tid,
			Model model){
		
		model.addAttribute("type", type);
		model.addAttribute("heading",GlobalValues.getHeading());
		
		if(type.equals("visit"))
			model.addAttribute("bill", billingService.findVisitBillByTid(tid));
		else if(type.equals("procedure"))
			model.addAttribute("bill", billingService.findProcedureBillByTid(tid));
		
		return "Receptionist/Print/Bill";
	}
}
