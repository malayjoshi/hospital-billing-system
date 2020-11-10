package in.jamuna.hms.controllers.hospital.manager.dashboard;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class ManagerDashboardController {
	
	private static final Logger LOGGER=Logger.getLogger(ManagerDashboardController.class.getName());
	
	@RequestMapping("/dashboard")
	public String dashboardPage() {
		return "/Manager/Dashboard/Dashboard";
	}
	
}
