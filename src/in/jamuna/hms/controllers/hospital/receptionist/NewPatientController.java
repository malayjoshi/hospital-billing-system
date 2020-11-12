package in.jamuna.hms.controllers.hospital.receptionist;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import in.jamuna.hms.config.GlobalValues;

@Controller
@RequestMapping("/receptionist")
public class NewPatientController {
	
	@RequestMapping("/add-patient-form")
	public String newPatientPage(Model model) {
		
		model.addAttribute("sexes",GlobalValues.getSexes());
		
		return "Receptionist/Patient/NewPatientForm";
	}
}
