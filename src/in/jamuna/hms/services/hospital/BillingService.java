package in.jamuna.hms.services.hospital;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.jamuna.hms.dao.hospital.BillGroupsDAO;
import in.jamuna.hms.dao.hospital.DoctorRateDAO;
import in.jamuna.hms.dao.hospital.EmployeeDAO;
import in.jamuna.hms.dao.hospital.PatientDAO;
import in.jamuna.hms.dao.hospital.VisitBillDAO;
import in.jamuna.hms.dao.hospital.VisitDAO;
import in.jamuna.hms.entities.hospital.BillGroupsEntity;
import in.jamuna.hms.entities.hospital.DoctorRateEntity;
import in.jamuna.hms.entities.hospital.EmployeeEntity;
import in.jamuna.hms.entities.hospital.PatientEntity;
import in.jamuna.hms.entities.hospital.VisitBillEntity;
import in.jamuna.hms.entities.hospital.VisitTypeEntity;

@Service
public class BillingService {

	@Autowired
	VisitBillDAO visitBillDAO;
	@Autowired
	EmployeeDAO employeeDAO;
	@Autowired
	VisitDAO visitDAO;
	@Autowired
	DoctorRateDAO doctorRateDAO;
	@Autowired
	PatientDAO patientDAO;
	@Autowired
	BillGroupsDAO billGroupsDAO;
	
	private static Logger LOGGER=Logger.getLogger(BillingService.class.getName());
	
	public int getVisitRate(int id, int empId, int visitId) {
		//get last visit of id where empId and visitId 
		EmployeeEntity doctor=employeeDAO.findById(empId);
		VisitTypeEntity visit=visitDAO.findById(visitId);
		PatientEntity patient=patientDAO.getPatientById(id);
		LOGGER.info("at 45");
		VisitBillEntity bill=new VisitBillEntity();
		bill=visitBillDAO.getLastVisitBillByDoctorAndVisit(doctor,visit,patient);
		LOGGER.info("at 47");
		int rate=-1;
		
		if(bill!=null) {
			
			//check difference bw dates and validity
			LocalDate billDate=bill.getBillingDate().toInstant()
				      .atZone(ZoneId.systemDefault())
				      .toLocalDate();
					
	        LocalDate today = LocalDate.now();
	        
	        Period age = Period.between(billDate, today);
	        int days = age.getDays();
	        
	        Set<Integer> validitiesByVisit=visit.getValidities().stream().
	        		filter( validity -> validity.getDoctor().getId() == empId )
	        		.map(validity -> validity.getDays()).collect( Collectors.toSet() );
	        int dayValidity=(int) validitiesByVisit.toArray()[0];
	        
	        if( days>dayValidity ) {
	        	DoctorRateEntity doctorRateEntity= doctorRateDAO.getRateByDoctorAndVisitAndTime( doctor,visit,new Date().getTime() );
	        	if(doctorRateEntity!=null)
	        		rate=doctorRateEntity.getRate();
	        }else{
	        	rate = 0;
	        	
	        }
	     
		}
		else {
			DoctorRateEntity doctorRateEntity= doctorRateDAO.getRateByDoctorAndVisitAndTime( doctor,visit,new Date().getTime() );
        	if(doctorRateEntity!=null)
        		rate=doctorRateEntity.getRate();
		}
		
		  LOGGER.info("rate="+rate); 
		return rate;
	}

	public int savePatientVisit(int pid, int empId, int visitId, int rate) {
		return (int)visitDAO.saveVisit(
				patientDAO.getPatientById(pid),
				employeeDAO.findById(empId),
				visitDAO.findById(visitId),
				rate
				);
		
	}

	public List<BillGroupsEntity> getAllBillGroups() {
		// TODO Auto-generated method stub
		return billGroupsDAO.findAll();
	}

	public void addBillingGroup(String name) {
		billGroupsDAO.addBillGroup(name);
		
	}

	public void toggleEnableGroup(int id, String action) {
		if(action.equals("enable")) {
			billGroupsDAO.setEnabledById(id,true);
		}
		else {
			billGroupsDAO.setEnabledById(id,false);
		}
		
	}
	
	
}
