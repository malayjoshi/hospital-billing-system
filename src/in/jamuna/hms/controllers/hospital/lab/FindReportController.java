package in.jamuna.hms.controllers.hospital.lab;

import in.jamuna.hms.services.hospital.LabService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Controller
@RequestMapping("/lab")
public class FindReportController {
	
	final
	LabService labService;
	
	private static final Logger LOGGER=
			Logger.getLogger(FindReportController.class.getName());

	public FindReportController(LabService labService) {
		this.labService = labService;
	}

	@RequestMapping("/find-report-page")
	public String findReportPage() {
		return "/Common/GetPatientByDetailsAndMore";
	}
	
	@RequestMapping("/find-report-by-{para}")
	public String findReportByParameters(Model model,@PathVariable String para,
										 HttpServletRequest request) {
		try {
			model.addAttribute("bills",labService.findReportsByPara(request,para));
			
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		return "/Common/GetPatientByDetailsAndMore";
	}
	
}
