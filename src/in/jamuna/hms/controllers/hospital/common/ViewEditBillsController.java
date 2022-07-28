package in.jamuna.hms.controllers.hospital.common;


import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.services.hospital.BillingService;
import in.jamuna.hms.services.hospital.EmployeeService;
import in.jamuna.hms.services.hospital.PatientService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@Controller
@RequestMapping("/common/bills")
public class ViewEditBillsController {

	final
	PatientService patientService;
	
	final
	EmployeeService employeeService;
	
	final
	BillingService billingService;

	private static final String CONSOLIDATED_BILL_PAGE ="Receptionist/Billing/ConsolidatedBillPage";
	
	private static final Logger LOGGER=Logger.getLogger(ViewEditBillsController.class.getName());

	public ViewEditBillsController(PatientService patientService, EmployeeService employeeService, BillingService billingService) {
		this.patientService = patientService;
		this.employeeService = employeeService;
		this.billingService = billingService;
	}

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
	
	
	
	@RequestMapping("/get-bills-pid/{type}")
	public String getBillsByPid(@PathVariable String type,
			@RequestParam(name="pid") int pid,
			Model model) {
		String page="Common/VisitBills";
		try {
			model.addAttribute("doctors",employeeService.getAllDoctors());
			
			if(type.equals("visit")) {
				model.addAttribute("visitTypes",employeeService.getAllVisitTypes());
				model.addAttribute("minRate", GlobalValues.getMinimumrate());
				
				model.addAttribute("bills", 
						billingService.getVisitBillsByPid(pid));
			}
			else if(type.equals("procedures")) {
				
				page="/Common/ProcedureBills";
				model.addAttribute("bills",billingService.getProcedureBillByPid( pid ) );
				
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
	
	@RequestMapping("/bill-groups-summary-page")
	public String billGroupsSummaryPage(Model model) {
		
		model.addAttribute("doctors", employeeService.getAllDoctors());
		model.addAttribute("groups", billingService.getAllBillGroups());
		
		return "Common/BillGroupsSummary";
	}
	
	@RequestMapping("get-bill-group-summary")
	public String billGroupSummary(@RequestParam(name="doctor_id") int empId,
			@RequestParam(name="group_id") int groupId,
			@RequestParam(name="date") Date date,Model model) {
		
		try {
			model.addAttribute("doctors", employeeService.getAllDoctors());
			model.addAttribute("groups", billingService.getAllBillGroups());
			model.addAttribute("total",billingService.
					getTotalOfProcedureBillsByBillGroupAndDoctorAndDate(
							empId,groupId,date));
			model.addAttribute("items", billingService.
					getProcedureBillsByBillGroupAndDoctorAndDate(
							empId,groupId,date));
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return "Common/BillGroupsSummary";
	}
	
	@RequestMapping("/bills-by-procedure-page")
	public String billsByProcedurePage(Model model) {
		try {
			model.addAttribute("procedures",billingService.getAllEnabledProcedures());
			model.addAttribute("doctors", employeeService.getAllDoctors());
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		return "Common/BillsByProcedure";
	}
	
	@RequestMapping("/get-bills-by-procedure")
	public String getBillsByProcedure(@RequestParam(name="doctor_id") int doctorId,
			@RequestParam(name="procedure") String procedure, @RequestParam(name="date") Date date, Model model) {
		try {
			model.addAttribute("procedures",billingService.getAllEnabledProcedures());
			model.addAttribute("doctors", employeeService.getAllDoctors());
			model.addAttribute("items",billingService.getProcedureBillByProcedureAndDoctorAndDate(procedure,doctorId,date) );
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return "Common/BillsByProcedure";
	}

	@RequestMapping("/consolidated-bills-by-pid")
	public  String consolidatedBillPage(){
		return CONSOLIDATED_BILL_PAGE;
	}

	@RequestMapping("/check-consoldated-bill-req")
	public String checkReq(@RequestParam("pid") int pid,@RequestParam("startDate") Date startDate,@RequestParam("endDate") Date endDate,Model model){
		try{
			model.addAttribute("bills",billingService.getTotalBillByDatesAndPid(pid,startDate,endDate));
			model.addAttribute("heading",GlobalValues.getHeading());
			model.addAttribute("subHeader",GlobalValues.getSubheader());
		}
		catch (Exception e){
			LOGGER.info(e.toString());
		}
		return CONSOLIDATED_BILL_PAGE;
	}
	
}
