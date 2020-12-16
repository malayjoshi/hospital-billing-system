package in.jamuna.hms.controllers.hospital.common;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.services.hospital.BillingService;
import in.jamuna.hms.services.hospital.EmployeeService;
import in.jamuna.hms.services.hospital.PatientService;

@Controller
@RequestMapping("/common/bills")
public class ViewEditBillsController {

	@Autowired
	PatientService patientService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	BillingService billingService;
	
	private static final Logger LOGGER=Logger.getLogger(ViewEditBillsController.class.getName());
	
	// for not recognizing date
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    sdf.setLenient(true);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@RequestMapping("/visit-bills-page")
	public String visitBillsPage(Model model) {
		model.addAttribute("doctors",employeeService.getAllDoctors());
		model.addAttribute("visitTypes",employeeService.getAllVisitTypes());
		
		return "Common/VisitBills";
	}
	
	@RequestMapping("/get-bills/{type}")
	public String getBills(@PathVariable String type,
			@RequestParam(name="doctor_id") int empId,
			@RequestParam(name="visit_id",required = false) Integer visitId,
			@RequestParam(name="date") Date date,Model model) {
		
		String page="Common/VisitBills";
		
		try {
			
			model.addAttribute("doctors",employeeService.getAllDoctors());
			
			LOGGER.info("empId:"+empId+" visitId:"+visitId+" date"+date);
			
			if(type.equals("visit")) {
				
				model.addAttribute("visitTypes",employeeService.getAllVisitTypes());
				model.addAttribute("minRate", GlobalValues.getMinimumrate());
				
				model.addAttribute("bills", 
						billingService.getVisitBillsByDateAndDoctorAndVisit(
								empId,visitId,date));
				model.addAttribute("total",billingService.
						getTotalOfVisitBillsByDateAndAll(empId,visitId,date) );
			}
			else if(type.equals("procedures")) {
				
				page="/Common/ProcedureBills";
				model.addAttribute("bills",billingService.
						getProcedureBillsByDateAndDoctor(empId,date) );
				model.addAttribute("total",billingService.
						getTotalOfProcedureBillsByDateAndDoctor(empId,date) );
				
			}
				
		
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return page;
	}
	
	@RequestMapping("/edit-bill-page/{type}/{tid}")
	public String editRatePage(@PathVariable String type,
			@PathVariable int tid,Model model) {
		
		try {
			model.addAttribute("type", type);
			
			if(type.equals("visit"))
				model.addAttribute("bill",billingService.findVisitBillByTid(tid));
			else if(type.equals("procedure"))
				{
					model.addAttribute("bill", billingService.findProcedureBillByTid(tid));
					model.addAttribute("billItems", billingService.findBillItemsByTid(tid));
				}
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return "Common/EditBill";
	}
	
	@RequestMapping("/edit-visit-bill/{tid}")
	public String changeBillFees( @RequestParam(name="fees") int fees,
			@PathVariable int tid, Model model ) {
		
		boolean result=billingService.changeBillFees( tid,fees );
		
		if(result) {
			model.addAttribute("successMessage","success");
		}
		else {
			model.addAttribute("errorMessage","error");
		}
		
		return "Common/EditBill";
	}
	
	@RequestMapping("/procedure-bills-page")
	public String procedureBillsPage(Model model) {

		model.addAttribute("doctors",employeeService.getAllDoctors());
		
		return "Common/ProcedureBills";
	}
	
	@RequestMapping("edit-procedure-bill/{tid}")
	public String editBillItems(@PathVariable int tid, Model model,HttpServletRequest request) {
		
		boolean result=billingService.changeRateOfBillItems(tid,request);
		
		if(result) {
			model.addAttribute("successMessage","success");
		}
		else {
			model.addAttribute("errorMessage","error");
		}
		
		return "Common/EditBill";
	}
}
