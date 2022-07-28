package in.jamuna.hms.controllers.hospital.receptionist;

import in.jamuna.hms.services.hospital.BillingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/receptionist")
public class BillRefundController {

	final
	BillingService billingService;

	public BillRefundController(BillingService billingService) {
		this.billingService = billingService;
	}


	@RequestMapping("/refund-bill")
	public String refundPage() {

		return "/Receptionist/Billing/Refund";
	}
	
	@RequestMapping("/refund/{billFor}")
	public String checkEligibility(@PathVariable String billFor,
			@RequestParam(name="tid") int tid,@RequestParam(name="amount") int amount
			,Model model) {
		
		boolean result=billingService.refund(
				billFor,tid,amount
				);
		
		if(result)
			model.addAttribute("successMessage", "Refund successful.");
		else
			model.addAttribute("errorMessage", "Not eligible for refund.");
		
		return "/Receptionist/Billing/Refund";
	}
}
