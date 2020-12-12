package in.jamuna.hms.services.hospital;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dao.hospital.BillGroupsDAO;
import in.jamuna.hms.dao.hospital.DoctorRateDAO;
import in.jamuna.hms.dao.hospital.EmployeeDAO;
import in.jamuna.hms.dao.hospital.PatientDAO;
import in.jamuna.hms.dao.hospital.ProcedureBillDAO;
import in.jamuna.hms.dao.hospital.ProcedureBillItemDAO;
import in.jamuna.hms.dao.hospital.ProceduresCartDAO;
import in.jamuna.hms.dao.hospital.ProceduresDAO;
import in.jamuna.hms.dao.hospital.VisitBillDAO;
import in.jamuna.hms.dao.hospital.VisitDAO;
import in.jamuna.hms.dto.cart.CartItemDTO;
import in.jamuna.hms.entities.hospital.BillGroupsEntity;
import in.jamuna.hms.entities.hospital.DoctorRateEntity;
import in.jamuna.hms.entities.hospital.EmployeeEntity;
import in.jamuna.hms.entities.hospital.PatientEntity;
import in.jamuna.hms.entities.hospital.ProcedureBillEntity;
import in.jamuna.hms.entities.hospital.ProcedureRatesEntity;
import in.jamuna.hms.entities.hospital.ProceduresCartEntity;
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
	@Autowired
	ProceduresCartDAO proceduresCartDAO;
	@Autowired
	ProcedureBillDAO procedureBillDAO;
	@Autowired
	ProcedureBillItemDAO procedureBillItemDAO;
	
	private static Logger LOGGER=Logger.getLogger(BillingService.class.getName());
	
	public int getVisitRate(int id, int empId, int visitId) {
		//get last visit of id where empId and visitId 
		EmployeeEntity doctor=employeeDAO.findById(empId);
		VisitTypeEntity visit=visitDAO.findById(visitId);
		PatientEntity patient=patientDAO.getPatientById(id);
		LOGGER.info("at 45");
		VisitBillEntity bill=new VisitBillEntity();
		bill=visitBillDAO.getLastVisitBillByDoctorAndVisitAndFeesAndRefund(
				doctor,visit,patient,GlobalValues.getMinimumrate(),null);
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

	public List<ProcedureRatesEntity> searchProcedure(String term) {
		
		return proceduresDAO.
				findByNameAndEnabledWithLimit(term,GlobalValues.getSearchlimit());
	}

	public void editCart(Integer pid, int id,String operation) {
		try {
			
			if(operation.equals("add"))
				proceduresCartDAO.saveItem(
					patientDAO.getPatientById(pid),
					proceduresDAO.findById(id)
					);
			else {
				LOGGER.info("delete item:"+id);
				proceduresCartDAO.deleteItem(id);
				
			}
			
			
		}catch(Exception e) {
			e.getMessage();
		}
		
	}

	public List<CartItemDTO> findCartItemsByPid(int pid) {
		
		return proceduresCartDAO.findByPatient(
				patientDAO.getPatientById(pid)
				).stream().map(item->convertToCartItemDTO(item)).collect(Collectors.toList());
	}

	private CartItemDTO convertToCartItemDTO(ProceduresCartEntity item) {
		CartItemDTO dto=new CartItemDTO();
		dto.setId(item.getId());
		dto.setName(item.getProcedure().getProcedure());
		dto.setRate(item.getProcedure().getRate());
		return dto;
	}

	@Transactional
	public ProcedureBillEntity saveProcedureBillAndDeleteFromCart(int empId,int pid, HttpServletRequest request) {
		//get total
		List<Integer> rates=new ArrayList<Integer>();
		ProcedureBillEntity bill=null;
		
		try {
			
			PatientEntity patient=patientDAO.getPatientById(pid);
			List<ProceduresCartEntity> items=proceduresCartDAO.findByPatient(patient);
			
			int total=0;
			for(ProceduresCartEntity item:items) {
				int rate=Integer.parseInt( request.getParameter("rate_"+item.getId()) );
				total+=rate;
				rates.add(rate);
			}
			
			bill=procedureBillDAO.saveBill(patient, employeeDAO.findById(empId), total );
			
			int i=0;
			for(ProceduresCartEntity item:items) {
				
				procedureBillItemDAO.saveItem(
						procedureBillDAO.findByTid(bill.getTid()),
						item.getProcedure(),
						rates.get(i));
				
				proceduresCartDAO.deleteItem(item.getId());
				
				i++;
			}
			
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		
		return bill;
	}

	@Transactional
	public boolean refund(String billFor, int tid, int amount) {
		boolean result=false;
		try {

			if(billFor.equals("visit")) {
				//get bill
				VisitBillEntity bill=visitBillDAO.findById(tid);
				//check if bill amount >0 and bill.refund==null
				if(bill.getFees()>0 && bill.getRefundBill()==null && amount<=bill.getFees()) {
					
					result=true;
					visitBillDAO.refundBill(tid,amount);
				}
			}else {
				//get bill
				ProcedureBillEntity bill=procedureBillDAO.findByTid(tid);
				//check if bill amount >0 and bill.refund==null
				if(bill.getTotal()>0 && bill.getRefundBill()==null && amount<=bill.getTotal()) {
					
					result=true;
					procedureBillDAO.refundBill(tid,amount);
				}
			}
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		return result;
	}
	
	
}
