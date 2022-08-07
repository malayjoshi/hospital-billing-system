package in.jamuna.hms.services.hospital;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dao.hospital.stock.ProcedureProductDAO;
import in.jamuna.hms.dao.hospital.billing.*;
import in.jamuna.hms.dao.hospital.employee.EmployeeDAO;
import in.jamuna.hms.dao.hospital.employee.PatientDAO;
import in.jamuna.hms.dto.cart.BillDTO;
import in.jamuna.hms.dto.cart.BillGroupSummaryItemDTO;
import in.jamuna.hms.dto.cart.CartItemDTO;
import in.jamuna.hms.dto.common.CommonIdAndNameDto;
import in.jamuna.hms.dto.reports.BillGroupReportItemDTO;
import in.jamuna.hms.dto.reports.VisitReportDTO;
import in.jamuna.hms.entities.hospital.billing.*;
import in.jamuna.hms.entities.hospital.employees.DaysValidityByVisit;
import in.jamuna.hms.entities.hospital.employees.DoctorRateEntity;
import in.jamuna.hms.entities.hospital.employees.EmployeeEntity;
import in.jamuna.hms.entities.hospital.patient.PatientEntity;
import in.jamuna.hms.entities.hospital.stock.ProcedureProductMappingEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class BillingService {

	final
	VisitBillDAO visitBillDAO;
	final
	EmployeeDAO employeeDAO;
	final
	VisitDAO visitDAO;
	final
	DoctorRateDAO doctorRateDAO;
	final
	PatientDAO patientDAO;
	final
    BillGroupsDAO billGroupsDAO;
	final
	ProceduresDAO proceduresDAO;
	final
	ProceduresCartDAO proceduresCartDAO;
	final
	ProcedureBillDAO procedureBillDAO;
	final
	ProcedureBillItemDAO procedureBillItemDAO;

	@Autowired
	private TestStockService testStockService;
	final
	ModelMapper mapper;
	private final ConverterService converter;

	@Autowired
	private ProcedureProductDAO procedureProductDAO;
	
	private static final Logger LOGGER=Logger.getLogger(BillingService.class.getName());

	public BillingService(EmployeeDAO employeeDAO, VisitBillDAO visitBillDAO, ConverterService converter, VisitDAO visitDAO, DoctorRateDAO doctorRateDAO, PatientDAO patientDAO, BillGroupsDAO billGroupsDAO, ProceduresDAO proceduresDAO, ProceduresCartDAO proceduresCartDAO, ProcedureBillDAO procedureBillDAO, ProcedureBillItemDAO procedureBillItemDAO, ModelMapper mapper) {
		this.employeeDAO = employeeDAO;
		this.visitBillDAO = visitBillDAO;
		this.converter = converter;
		this.visitDAO = visitDAO;
		this.doctorRateDAO = doctorRateDAO;
		this.patientDAO = patientDAO;
		this.billGroupsDAO = billGroupsDAO;
		this.proceduresDAO = proceduresDAO;
		this.proceduresCartDAO = proceduresCartDAO;
		this.procedureBillDAO = procedureBillDAO;
		this.procedureBillItemDAO = procedureBillItemDAO;
		this.mapper = mapper;
	}

	public int getVisitRate(int id, int empId, int visitId) {
		//get last visit of id where empId and visitId 
		EmployeeEntity doctor=employeeDAO.findById(empId);
		VisitTypeEntity visit=visitDAO.findById(visitId);
		PatientEntity patient=patientDAO.getPatientById(id);
		LOGGER.info("at 45");
		VisitBillEntity bill;
		bill=visitBillDAO.getLastVisitBillByDoctorAndVisitAndFeesAndRefund(
				doctor,visit,patient,GlobalValues.getMinimumrate());
		
		int rate=-1;
		
		if(bill!=null) {
			//check difference bw dates and validity
			
					
	        Date today = new Date();
	        long diffInMillies = Math.abs(today.getTime() - bill.getBillingDate().getTime());
	        long days = (int)TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

			int dayValidity=(int) visit.getValidities().stream().
					filter(validity -> validity.getDoctor().getId() == empId)
					.map(DaysValidityByVisit::getDays).distinct().toArray()[0];
	        
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
		return visitDAO.saveVisit(
				patientDAO.getPatientById(pid),
				employeeDAO.findById(empId),
				visitDAO.findById(visitId),
				rate
				);
		
	}

	public List<CommonIdAndNameDto> getAllBillGroups() {

		return billGroupsDAO.findAll().stream().map(converter::convert).collect(Collectors.toList());
	}

	public void addBillingGroup(String name) {
		billGroupsDAO.addBillGroup(name);
		
	}

	public void toggleEnableGroup(int id, String action) {
		billGroupsDAO.setEnabledById(id, action.equals("enable"));
		
	}

	public List<CommonIdAndNameDto> getAllEnabledBillGroups() {
		return billGroupsDAO.findAll().stream().filter(BillGroupsEntity::isEnabled).
				map(converter::convert)
				.collect(Collectors.toList());
		
	}

	public void addProcedure(int groupId, String procedure, int rate) {
		try {
			BillGroupsEntity group=billGroupsDAO.findById(groupId);
			LOGGER.info("grp name: "+group.getName()+" proc:"+procedure+" rate:"+rate);
			if(proceduresDAO.findByNameAndEnabledWithLimit(procedure, 1).size() == 0)
				proceduresDAO.addProcedure(
						group,
						procedure,rate
						);	
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
	}

	public List<CartItemDTO> getAllProcedures() {
		return proceduresDAO.getAllProcedures()
				.stream().map(
						proc -> {
							CartItemDTO dto = new CartItemDTO();
							dto.setId(proc.getId());
							dto.setEnabled(proc.isEnabled());
							dto.setName(proc.getProcedure());
							dto.setRate(proc.getRate());
							dto.setStockTracking(proc.isStockEnabled());
							return dto;
						})
				.collect(Collectors.toList());
	}

	public void saveProcedureRate(int id, int rate) {
		proceduresDAO.saveProcedureRate(id,rate);
	}

	public void toggleEnableProcedure(int id, String action) {
		if( action.equals("enable") || action.equals("disable") )
			proceduresDAO.enableDisableProcedure(id, action.equals("enable"));
		else if( action.equals("stock-enable") || action.equals("stock-disable") )
		{
			proceduresDAO.enableDisableStockTrack(id, action.equals("stock-enable"));
		}
	}

	public List<CartItemDTO> searchProcedure(String term) {
		try{
			return proceduresDAO.
					findByNameAndEnabledWithLimit(term,GlobalValues.getSearchlimit()).stream()
					.map(item -> {
						CartItemDTO dto = mapper.map(item, CartItemDTO.class);
						dto.setName(item.getProcedure());
						if(item.isStockEnabled() && GlobalValues.isSTOCK_MANAGEMENT()){
							dto.setLowStock( testStockService.checkLowStock(item) );
						}
						return dto;
					}).collect(Collectors.toList());
		}catch (Exception e){
			LOGGER.info(e.getMessage());
		}
		return new ArrayList<>();

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
			LOGGER.info(e.toString());
		}
		
	}

	public List<CartItemDTO> findCartItemsByPid(int pid) {
		
		return proceduresCartDAO.findByPatient(
				patientDAO.getPatientById(pid)
				).stream().map(this::convertToCartItemDTO).collect(Collectors.toList());
	}

	private CartItemDTO convertToCartItemDTO(ProceduresCartEntity item) {
		CartItemDTO dto=new CartItemDTO();
		dto.setId(item.getId());
		dto.setName(item.getProcedure().getProcedure());
		dto.setRate(item.getProcedure().getRate());
		if( GlobalValues.isSTOCK_MANAGEMENT() )
			dto.setLowStock(testStockService.checkLowStock( proceduresDAO.findById(item.getProcedure().getId()) ) );

		return dto;
	}

	@Transactional
	public ProcedureBillEntity saveProcedureBillAndDeleteFromCart(int empId, int pid) {
		//get total
		List<Integer> rates= new ArrayList<>();
		ProcedureBillEntity bill=null;
		
		try {
			//first check if stock available
			boolean proceed = true;

			
			PatientEntity patient=patientDAO.getPatientById(pid);
			LOGGER.info("line 233");
			List<ProceduresCartEntity> items=proceduresCartDAO.findByPatient(patient);
			for(ProceduresCartEntity item:items){
				for(ProcedureProductMappingEntity mapping: item.getProcedure().getMappings()){
					if(testStockService.checkLowStock(item.getProcedure() ) ){
						//means low
						proceed = false;
						break;
					}
					if(!proceed)
						break;
				}
			}
			if(proceed){

				int total=0;
				for(ProceduresCartEntity item:items) {

					int rate=item.getProcedure().getRate();
					LOGGER.info("rate:"+rate);
					total+=rate;
					rates.add(rate);
				}
				LOGGER.info("line 242");
				bill=procedureBillDAO.saveBill(patient, employeeDAO.findById(empId), total );
				LOGGER.info("line 244");
				int i=0;
				for(ProceduresCartEntity item:items) {

				ProcedureBillItemEntity billItem =	procedureBillItemDAO.saveItem(
							procedureBillDAO.findByTid(bill.getTid()),
							item.getProcedure(),
							rates.get(i));
				testStockService.addStockSpent(billItem);

					proceduresCartDAO.deleteItem(item.getId());

					i++;
				}

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
			}else if(billFor.equals("procedure")){
				//get bill
				ProcedureBillEntity bill=procedureBillDAO.findByTid(tid);
				//check if bill amount >0 and bill.refund==null
				if(bill.getTotal()>0 && bill.getRefundBill()==null && amount<=bill.getTotal()) {
					LOGGER.info("line 288");
					result=true;
					procedureBillDAO.refundBill(tid,amount);
				}
			}
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		return result;
	}

	public List<BillDTO> getVisitBillsByDateAndDoctorAndVisit(int empId, int visitId, Date date) {
		
		return visitBillDAO.
				getVisitBillsByDoctorAndVisitAndDate(
						employeeDAO.findById(empId),
						visitDAO.findById(visitId),
						date).stream().map(converter::convert).collect(Collectors.toList());
	}

	public int getTotalOfVisitBillsByDateAndAll(int empId, int visitId, Date date) {
		
		return getVisitBillsByDateAndDoctorAndVisit(empId, visitId, date).
				stream().map(BillDTO::getFees).reduce(0, Integer::sum);
	}


	public BillDTO findVisitBillByTid(int tid) {
		//LOGGER.info(visitBillDAO.findById(tid).getRefund()+"");
		return converter.convert(visitBillDAO.findById(tid));
	}

	@Transactional
	public boolean changeBillFees(int tid, int fees) {
		
		visitBillDAO.findById(tid).setFees(fees);
		
		return true;
	}

	public List<BillDTO> getProcedureBillsByDateAndDoctor(int empId, Date date) {
		
		return procedureBillDAO.findByDoctorAndDate(
				employeeDAO.findById(empId),
				date
				).stream().map(converter::convert).collect(Collectors.toList());
	}

	public int getTotalOfProcedureBillsByDateAndDoctor(int empId, Date date) {
		
		return getProcedureBillsByDateAndDoctor(empId, date).
				stream().map(BillDTO::getFees).reduce(0, Integer::sum);
	}

	@Transactional
	public boolean changeRateOfBillItems(int tid, HttpServletRequest request) {
		
		//get rates of bill items
		ProcedureBillEntity bill=procedureBillDAO.findByTid(tid);
		//set rates
		int total=0;
		for(ProcedureBillItemEntity item: bill.getBillItems() ) {
			int rate=Integer.parseInt( request.getParameter("rate_"+item.getId()) );
			item.setRate( rate );
			total+=rate;
		}
		
		
		bill.setTotal(total);
		
		return true;
	}

	public BillDTO findProcedureBillByTid(int tid) {
		
		return converter.convert(procedureBillDAO.findByTid(tid));
	}


	public int getTotalOfProcedureBillsByBillGroupAndDoctorAndDate(
			int empId, int groupId, Date date) {
		
		int total=0;
		for( BillGroupSummaryItemDTO item:
			getProcedureBillsByBillGroupAndDoctorAndDate(empId, groupId, date) )
			total+=item.getRate();
		
		return total;
		
	}

	public List<BillGroupSummaryItemDTO> 
	getProcedureBillsByBillGroupAndDoctorAndDate(int empId, int groupId, Date date) {
		BillGroupsEntity group=billGroupsDAO.findById(groupId);
		// list of all procedures under particular group
		EmployeeEntity doc = employeeDAO.findById(empId);
		
		  
		
		return procedureBillItemDAO.findItemsByGroupAndDoctorAndDate(group,doc,date).stream()
				.map(item -> new BillGroupSummaryItemDTO(item.getId(),
						item.getBill().getTid(),
						item.getBill().getPatient().getFname()+" "+item.getBill().getPatient().getLname(),
						item.getBill().getPatient().getGuardian(),
						item.getProcedure().getProcedure(),
						item.getRate())).collect(Collectors.toList());
		
	}

	public List<BillDTO> getLabBillOfLastHours(int hrs) {
		
		List<ProcedureBillEntity> list;
		List<BillDTO> bills = new ArrayList<>();
		try {
			//get date 24 hrs back
			
			Date date = new Date(System.currentTimeMillis() - ((long) hrs * 60 * 60 * 1000));
			 list=procedureBillDAO.findByFromDate(date);
			 
			 for(ProcedureBillEntity bill: list ) {
				if(bill.getBillItems().stream().anyMatch(item -> item.getProcedure().getBillGroup().getId() == GlobalValues.getLabGroupId()))
					bills.add( converter.convert(bill) );
			}
			 
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return bills;
	}

	public List<ProcedureBillEntity> getLabBillByPatient(PatientEntity patient) {
		
		//get procedure bills by patient
		return procedureBillDAO.findByPatient(patient);
		
	}

	public List<BillDTO> getProcedureBillByPid(int pid) {
		
		return procedureBillDAO.findByPatient(patientDAO.getPatientById(pid))
				.stream().map(converter::convert).collect(Collectors.toList());
	}

	public List<BillDTO> getVisitBillsByPid(int pid) {
		
		return visitBillDAO.findByPatient( patientDAO.getPatientById(pid) ).stream().map(converter::convert).collect(Collectors.toList());
	}

	public List<CartItemDTO> getAllEnabledProcedures() {
		return proceduresDAO.getAllProcedures().stream().filter(ProcedureRatesEntity::isEnabled).map(procedure -> {
			CartItemDTO dto = new CartItemDTO();
			dto.setId(procedure.getId());
			dto.setName(procedure.getProcedure());
			return dto;
		})
				.collect(Collectors.toList());
	}

	public List<BillGroupSummaryItemDTO> getProcedureBillByProcedureAndDoctorAndDate(String procedure, int doctorId, Date date) {
		
		List<BillGroupSummaryItemDTO> items=new ArrayList<>();
		
		try {
			items=procedureBillItemDAO.getItemsByProcedureAndDoctorAndDate(
					proceduresDAO.findByNameAndEnabledWithLimit(procedure, 1).get(0),
					employeeDAO.findById(doctorId),
					date).stream().map(item ->
							new BillGroupSummaryItemDTO(item.getId(),
									item.getBill().getTid(),
									item.getBill().getPatient().getFname()+" "+item.getBill().getPatient().getLname(),
									item.getBill().getPatient().getGuardian(),
									item.getProcedure().getProcedure(),
									item.getRate())
							).collect(Collectors.toList());
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return items;
	}

	
	

	public List<BillGroupReportItemDTO>  getBillGroupReportByGroupIdAndDateAndDoctorAndType(int groupId, Date date, int empId, String type) {
		
		BillGroupsEntity group = billGroupsDAO.findById(groupId);
		
		List<BillGroupReportItemDTO> list= new ArrayList<>();
		
		try {
			
			for( ProcedureRatesEntity proc : group.getProcedures() ) {
				BillGroupReportItemDTO item= new BillGroupReportItemDTO();
				
				item.setName(proc.getProcedure());
				List<ProcedureBillItemEntity> items;
				items = procedureBillItemDAO.getItemsByProcedureAndDateAndTypeDoctor(proc, date,type, employeeDAO.findById(empId) );
				item.setCount(
						items.size()
						);
				item.setTotal( items.stream().map(ProcedureBillItemEntity::getRate).reduce(0, Integer::sum) );
				
				if( item.getCount() > 0 )
					list.add(item);
			}
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return list;
		
	}

	public VisitReportDTO getVisitReportByDoctorAndDateAndType(int empId, Date date, String type) {
		VisitReportDTO report=new VisitReportDTO();
		try {
			
			//get list of all visits by type & date
			if(type.equals("Daily")) {
				
				//hardcoding here
				List<VisitBillEntity> listOpds = visitBillDAO.getVisitBillsByDoctorAndVisitAndDate( employeeDAO.findById(empId) , visitDAO.findById(1), date);
				report.setOpds(listOpds.size());
				
				List<VisitBillEntity> listEmergency = visitBillDAO.getVisitBillsByDoctorAndVisitAndDate( employeeDAO.findById(empId) , visitDAO.findById(2), date);
				report.setEmergencies(listEmergency.size());
				
				//List<VisitBillEntity> listFollowups=visitBillDAO.getVisitBillsByDoctorAndFeesAndDate(employeeDAO.findById(empId), 0, date);
			}
			else if(type.equals("Monthly")) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int month=calendar.get(Calendar.MONTH)+1;
				int year=calendar.get(Calendar.YEAR);
				//hardcoding here
				List<VisitBillEntity> listOpds = visitBillDAO.getVisitBillsByDoctorAndVisitAndMonthAndYear( employeeDAO.findById(empId) , visitDAO.findById(1), month,year);
				report.setOpds(listOpds.size());
				
				List<VisitBillEntity> listEmergency = visitBillDAO.getVisitBillsByDoctorAndVisitAndMonthAndYear( employeeDAO.findById(empId) , visitDAO.findById(2), month,year);
				report.setEmergencies(listEmergency.size());
				
				//List<VisitBillEntity> listFollowups=visitBillDAO.getVisitBillsByDoctorAndFeesAndMonthAndYear(employeeDAO.findById(empId), 0, month,year);
			}
			
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		return report;
	}


    public List<BillDTO> getTotalBillByDatesAndPid(int pid, Date startDate, Date endDate) {
		List<BillDTO> list = null;
		try{
			//first get visit bills
			list = procedureBillDAO.findByPatientAndStartAndEndDate(
					patientDAO.getPatientById(pid),
					startDate, endDate
			).stream().map(converter::convert).collect(Collectors.toList());

			for (VisitBillEntity b : visitBillDAO.findByPatientAndStartAndEndDate(
					patientDAO.getPatientById(pid),
					startDate, endDate
			)) {
				list.add(converter.convert(b));
			}

		}catch (Exception e){
			LOGGER.info(e.toString());
		}
		return list;
    }

    public List<CartItemDTO> getAllProductMappingsByProcedureId(int id) {
		try{
			return procedureProductDAO.findByProcedure( proceduresDAO.findById(id) ).stream().map(converter::convert).collect(Collectors.toList());
		}catch (Exception e){
			LOGGER.info(e.getMessage());
		}
		return new ArrayList<>();
    }
}
