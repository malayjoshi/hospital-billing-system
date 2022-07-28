package in.jamuna.hms.controllers.authorization;


import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dto.login.CredentialsDto;
import in.jamuna.hms.dto.login.SessionDto;
import in.jamuna.hms.services.hospital.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginLogoutController {
	@Autowired
	EmployeeService employeeService;
	//private static final Logger LOGGER = Logger.getLogger(LoginLogoutController.class.getName());
	@RequestMapping("/")
	public String loginPage(Model model) {
		model.addAttribute("roles",employeeService.getAllRoles());
		return "/Login/login";
	}
	
	@RequestMapping("/authenticate")
	public String authenticate(@ModelAttribute("credentials") CredentialsDto credentials,
			Model model,HttpServletRequest request) {
		
		String page="/Login/login";
		SessionDto session=employeeService.checkCredentials(credentials);
		
		if( session!=null ) {
			//LOGGER.info(session.toString());
			HttpSession httpSession=request.getSession();
			httpSession.setAttribute("user", session);

			switch (session.getRole()) {
				case "MANAGER":
					page = GlobalValues.getAdminhomepage();
					break;
				case "RECEPTIONIST":
					page = GlobalValues.getReceptionisthomepage();
					break;
				case "LAB TECH":
					page = GlobalValues.getLabhomepage();
					break;
			}
			
			
			
		}else {
			model.addAttribute("errorMessage","Wrong credentials");
			model.addAttribute("roles",employeeService.getAllRoles());
		}
		
		return page;
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest response) {

		HttpSession httpSession = response.getSession();
		httpSession.invalidate();
		
		return "redirect:/";
	}
	
}
