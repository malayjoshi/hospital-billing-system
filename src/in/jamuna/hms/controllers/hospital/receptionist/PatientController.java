package in.jamuna.hms.controllers.hospital.receptionist;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dto.patient.PatientDTO;
import in.jamuna.hms.services.hospital.PatientService;

@Controller
@RequestMapping("/receptionist")
public class PatientController {
	
	@Autowired
	PatientService patientService;
	
	@RequestMapping("/add-patient-form")
	public String newPatientPage(Model model) {
		
		model.addAttribute("sexes",GlobalValues.getSexes());
		
		return "Receptionist/Patient/NewPatientForm";
	}
	
	@RequestMapping("/save-patient")
	public String savePatient(PatientDTO patient,Model model) {
		
		String page="Receptionist/Patient/NewPatientForm";
		
		int id=patientService.savePatient(patient);
		model.addAttribute("id",id);
		model.addAttribute("sexes",GlobalValues.getSexes());
		
		return page;
	}
	
}
