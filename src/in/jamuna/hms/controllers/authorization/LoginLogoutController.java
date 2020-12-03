package in.jamuna.hms.controllers.authorization;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dto.login.CredentialsDto;
import in.jamuna.hms.dto.login.SessionDto;
import in.jamuna.hms.services.hospital.EmployeeService;

@Controller
public class LoginLogoutController {
	@Autowired
	EmployeeService employeeService;
	
	private static final Logger LOGGER=Logger.getLogger(LoginLogoutController.class.getName());
	
	@RequestMapping("/")
	public String loginPage(Model model) {
		model.addAttribute("roles",employeeService.getAllRoles());
		return "/Login/login";
	}
	
	@RequestMapping("/authenticate")
	public String authenticate(@Valid @ModelAttribute("credentials") CredentialsDto credentials,
			Model model,HttpServletRequest request) {
		
		String page="/Login/login";
		SessionDto session=employeeService.checkCredentials(credentials);
		
		if( session!=null ) {
			HttpSession httpSession=request.getSession();
			httpSession.setAttribute("user", session);
			if(session.getRole().equals("MANAGER")) {
				page=GlobalValues.getAdminhomepage();
				LOGGER.info(page);
			}
			else if( session.getRole().equals("RECEPTIONIST") ) {
				page=GlobalValues.getReceptionisthomepage();
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
