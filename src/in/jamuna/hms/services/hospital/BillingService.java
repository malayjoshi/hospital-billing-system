package in.jamuna.hms.services.hospital;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.jamuna.hms.dao.hospital.BillGroupsDAO;
import in.jamuna.hms.dao.hospital.DoctorRateDAO;
import in.jamuna.hms.dao.hospital.EmployeeDAO;
import in.jamuna.hms.dao.hospital.PatientDAO;
import in.jamuna.hms.dao.hospital.ProceduresDAO;
import in.jamuna.hms.dao.hospital.VisitBillDAO;
import in.jamuna.hms.dao.hospital.VisitDAO;
import in.jamuna.hms.entities.hospital.BillGroupsEntity;
import in.jamuna.hms.entities.hospital.DoctorRateEntity;
import in.jamuna.hms.entities.hospital.EmployeeEntity;
import in.jamuna.hms.entities.hospital.PatientEntity;
import in.jamuna.hms.entities.hospital.ProcedureRatesEntity;
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
	@Autowired
	ProceduresDAO proceduresDAO;
	
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

	public List<BillGroupsEntity> getAllEnabledBillGroups() {
		return getAllBillGroups().stream().filter(group-> group.isEnabled() )
				.collect(Collectors.toList());
		
	}

	public void addProcedure(int groupId, String procedure, int rate) {
		try {
			BillGroupsEntity group=billGroupsDAO.findById(groupId);
			LOGGER.info("grp name: "+group.getName()+" proc:"+procedure+" rate:"+rate);
			proceduresDAO.addProcedure(
					group,
					procedure,rate
					);	
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
	}

	public List<ProcedureRatesEntity> getAllProcedures() {
		return proceduresDAO.getAllProcedures();
	}

	public void saveProcedureRate(int id, int rate) {
		proceduresDAO.saveProcedureRate(id,rate);
	}

	public void toggleEnableProcedure(int id, String action) {
		if(action.equals("enable"))
			proceduresDAO.enableDisableProcedure(id,true);
		else
			proceduresDAO.enableDisableProcedure(id,false);
	}
	
	
}
