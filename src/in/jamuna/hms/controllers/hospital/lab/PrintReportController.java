package in.jamuna.hms.controllers.hospital.lab;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.services.hospital.BillingService;
import in.jamuna.hms.services.hospital.LabService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Controller
@RequestMapping("/lab")
public class PrintReportController {

	final
	LabService labService;
	final
	BillingService billingService;
	
	private static final Logger LOGGER=
			Logger.getLogger(PrintReportController.class.getName());

	public PrintReportController(LabService labService, BillingService billingService) {
		this.labService = labService;
		this.billingService = billingService;
	}

	@RequestMapping("/print-report-page")
	public String PrintReportPage() {
		
		return "/Lab/PrintReport";
	}
	
	
	@RequestMapping("/print-report/{tid}")
	public String PrintReport(@PathVariable int tid,Model model) {
		try {
			try {
				model.addAttribute("tid",tid);
				model.addAttribute("bill",billingService.findProcedureBillByTid(tid));
				
				model.addAttribute("heading",GlobalValues.getLabReportHeading());
				model.addAttribute("subHeading",GlobalValues.getLabReportSubHeading());
				model.addAttribute("categories",labService.getReportPrintDTOByTid(tid) );
			}catch(Exception e) {
				LOGGER.info(e.toString());
			}
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return "/Lab/Report";
	}
	
	@RequestMapping("/print-report/check-tid")
	public String checkIfTestCompleted(Model model,HttpServletRequest request) {
		
		try {
			int tid=Integer.parseInt( request.getParameter("tid"));
			if( labService.checkIfTestCompletedByTid( tid ) ) {
				model.addAttribute("tid",tid);
			}
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return "/Lab/PrintReport";
	}
	
}
