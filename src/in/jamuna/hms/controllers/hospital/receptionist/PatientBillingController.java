package in.jamuna.hms.controllers.hospital.receptionist;


import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.jamuna.hms.dto.patient.PatientDTO;
import in.jamuna.hms.entities.hospital.PatientEntity;
import in.jamuna.hms.services.hospital.BillingService;
import in.jamuna.hms.services.hospital.EmployeeService;
import in.jamuna.hms.services.hospital.PatientService;

@Controller
@RequestMapping("/receptionist")
public class PatientBillingController {
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	BillingService billingService;
	
	private static final Logger LOGGER=Logger.getLogger(PatientBillingController.class.getName());
	
	@RequestMapping("/new-visit/{id}")
	public String newPatientVisitPage(@PathVariable int id,Model model) {
		model=addCommonAttributes(id, model);
		
		return "Receptionist/Billing/NewVisit";
	}
	
	@RequestMapping("/get-visit-rate/{id}")
	public String getVisitRate(@PathVariable int id,@RequestParam("empId") int empId, @RequestParam("visitId") int visitId,Model model) {
		try {
			PatientDTO patient=new PatientDTO();
			patient.setId(id);
			
			model=addCommonAttributes(id, model);
			
			model.addAttribute("rate",billingService.getVisitRate(id,empId,visitId));
			
			model.addAttribute("currentVisit",visitId);
			model.addAttribute("currentDoctor",empId);
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		
		return "Receptionist/Billing/NewVisit";
	}
	
	@RequestMapping("/save-visit/{id}")
	public String savePatientVisit(@PathVariable int id,@RequestParam("empId") int empId, @RequestParam("visitId") int visitId,
			@RequestParam("rate") int rate,Model model)
	{
		try {
			model=addCommonAttributes(id, model);
			PatientDTO patient=new PatientDTO();
			int tid=billingService.savePatientVisit(id,empId,visitId,rate);
			model.addAttribute("tid",tid);
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		
		return "Receptionist/Billing/NewVisit";
	}
	
	public Model addCommonAttributes(int id,Model model) {
		
		PatientDTO patient=new PatientDTO();
		patient.setId(id);
		model.addAttribute("patient",patientService.getPatientsByCriteriaWithLimit(patient, "id").get(0));
		model.addAttribute("visitTypes",employeeService.getAllVisitTypes());
		model.addAttribute("doctors",employeeService.getAllDoctors());
		
		return model;
	}
	
	
	@RequestMapping("/new-procedure-bill")
	public String procedureBillingPage(Model model) {
		
		return "Receptionist/Billing/ProcedureBillPage";
	}
	
	@RequestMapping("/search-procedure/{id}")
	public String searchProcedure(@RequestParam(name="procedure") String term,@PathVariable int id,
			Model model) {
		try {
			model.addAttribute("procedures", billingService.searchProcedure(term));
			model.addAttribute("pid", id);
			model.addAttribute("items",billingService.findCartItemsByPid(id) ); 
			model.addAttribute("doctors",employeeService.getAllDoctors());
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return "Receptionist/Billing/ProcedureBillPage";
	}
	
	@RequestMapping("/check-pid")
	public String checkPid( @RequestParam(name="pid") int pid,Model model ) {
		try {
		
			PatientDTO patient=new PatientDTO();
			patient.setId(pid);
			
			List<PatientEntity> patients=patientService.
					getPatientsByCriteriaWithLimit(patient,"id");
			
				model.addAttribute("pid",patients.get(0).getId());
			
			
		}catch( Exception e ) {
			LOGGER.info(e.getMessage());
			model.addAttribute("patientNotFound", "yes");
		}
		
		return "Receptionist/Billing/ProcedureBillPage";
	}
	
	
	@RequestMapping({"/{operation}-item/pid-{pid}/item-id-{id}"})
	public String editToCart(@PathVariable String operation,
			@PathVariable Integer pid, @PathVariable int id,Model model ) {
		try {
				billingService.editCart( pid,id,operation );
			
				model.addAttribute("items",billingService.findCartItemsByPid(pid) ); 
				model.addAttribute("pid", pid);
				model.addAttribute("doctors",employeeService.getAllDoctors());
				
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		return "Receptionist/Billing/ProcedureBillPage";
	}
	
	
	@RequestMapping("save-procedures-bill/{pid}")
	public String saveBill(@PathVariable int pid,@RequestParam("doctor_id") int empId,Model model) {
		
		try {
			model.addAttribute("bill",billingService.
					saveProcedureBillAndDeleteFromCart(empId, pid));
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		return "Receptionist/Billing/ProcedureBillPage";
	}
	
}
