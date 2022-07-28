package in.jamuna.hms.controllers.hospital.receptionist;


import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dto.patient.PatientDTO;
import in.jamuna.hms.services.hospital.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/receptionist")
public class PatientController {
	
	final
	PatientService patientService;
	
	private static final String SEXES_KEY="sexes";

	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	@RequestMapping("/add-patient-form")
	public String newPatientPage(Model model) {
		
		model.addAttribute(SEXES_KEY,GlobalValues.getSexes());
		model.addAttribute("heading","New Patient Form");
		model.addAttribute("operation","add");
		return "Receptionist/Patient/PatientDetailsForm";
	}
	
	@RequestMapping("/save-patient")
	public String savePatient(PatientDTO patient,Model model) {
		
		String page="Receptionist/Patient/PatientDetailsForm";
		
		int id=patientService.savePatient(patient);
		model.addAttribute("successMessage","Patient added. PID:"+id);
		model.addAttribute("pid",id);
		model.addAttribute(SEXES_KEY,GlobalValues.getSexes());
		
		return page;
	}
	
	@RequestMapping({"/edit-patient","/new-visit"})
	public String commonPageForVisitAndEdit() {
		return "Common/GetPatientByDetailsAndMore";
	}
	
	@RequestMapping("/get-patient-by-{criteria}")
	public String getPatientsByDetails(PatientDTO patient,@PathVariable String criteria,Model model) {
		
		model.addAttribute("patients",patientService.getPatientsByCriteriaWithLimit(patient,criteria));
		
		return "Common/GetPatientByDetailsAndMore";
	}
	
	@RequestMapping("/edit-patient/{id}")
	public String editPatientPage(@PathVariable int id,Model model) {
		
		model.addAttribute(SEXES_KEY,GlobalValues.getSexes());
		model.addAttribute("heading","Edit Patient Details");
		model.addAttribute("operation","edit");
		PatientDTO patient=new PatientDTO();
		patient.setId(id);
		model.addAttribute("patient", patientService.getPatientsByCriteriaWithLimit(patient, "id").get(0) );
		
		return "Receptionist/Patient/PatientDetailsForm";
	}
	
	
	@RequestMapping("edit-patient-save/{id}")
	public String saveEditedPatient(PatientDTO patient,@PathVariable int id,Model model) {
		
		String page="Receptionist/Patient/PatientDetailsForm";
		
		patientService.saveEditedPatient(patient,id);
		model.addAttribute("successMessage","Changes Saved");
		model.addAttribute(SEXES_KEY,GlobalValues.getSexes());
		
		return page;
	}
	
	

}
