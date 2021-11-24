package in.jamuna.hms.controllers.hospital.manager.reports;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.jamuna.hms.services.hospital.BillingService;

@Controller
@RequestMapping("/manager/reports")
public class ReportsController {

	@Autowired
	BillingService billingService;
	
	private static final Logger LOGGER=Logger.getLogger(ReportsController.class.getName());
	private static final String MAIN_PAGE="/Manager/Reports/BillGroups";
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    sdf.setLenient(true);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@RequestMapping("/bill-group-report-page")
	public String billGroupsPage(Model model) {
		try {
			model.addAttribute("groups",billingService.getAllBillGroups());
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return MAIN_PAGE;
	}
	
	@RequestMapping("/get-bill-group-report")
	public String getBillGroupReport(@RequestParam(name="group_id") int groupId,@RequestParam(name="date") Date date,Model model) {
		try {
			model.addAttribute("rows", billingService.getBillGroupReportByGroupIdAndDate(groupId,date) );
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}

		return billGroupsPage(model);
	}
	
}
