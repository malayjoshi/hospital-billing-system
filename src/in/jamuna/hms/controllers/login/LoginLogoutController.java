package in.jamuna.hms.controllers.login;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import in.jamuna.hms.dto.login.CredentialsDto;
import in.jamuna.hms.dto.login.SessionDto;
import in.jamuna.hms.entities.hospital.RolesEntity;
import in.jamuna.hms.services.hospital.EmployeeService;


@Controller
public class LoginLogoutController {

	@Autowired
	private EmployeeService employeeService;


	private String loginPage="/Login/login";
	

	private static final Logger logger = Logger.getLogger(LoginLogoutController.class.getName());
	
	
	@RequestMapping(value = "/")
	public String loginPage(Model model) {
		List<RolesEntity> roles=employeeService.getAllRoles();
		model.addAttribute("roles",roles);
		
		return loginPage;
	}
	

	
	@RequestMapping(value = "/login")
	public String login( CredentialsDto cred, Model model,
			HttpServletRequest request) {

		SessionDto result = employeeService.checkCredentials(cred);
		if (result != null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", result);
				return "redirect:/user/home-page/";
		} 
		else {
		
			model.addAttribute("messageError", "wrong username or password");
			return loginPage;
			
			}
			

		}


	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest response) {

		HttpSession httpSession = response.getSession();
		httpSession.invalidate();
		
		return loginPage;
	}

	
}
