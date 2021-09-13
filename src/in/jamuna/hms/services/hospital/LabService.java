package in.jamuna.hms.services.hospital;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dao.hospital.LabCategoryDAO;
import in.jamuna.hms.dao.hospital.ProcedureBillDAO;
import in.jamuna.hms.dao.hospital.ProceduresDAO;
import in.jamuna.hms.dao.hospital.TestParametersDAO;
import in.jamuna.hms.dao.hospital.TestsDAO;
import in.jamuna.hms.entities.hospital.LabCategoryEntity;
import in.jamuna.hms.entities.hospital.PatientEntity;
import in.jamuna.hms.entities.hospital.ProcedureBillEntity;
import in.jamuna.hms.entities.hospital.ProcedureBillItemEntity;
import in.jamuna.hms.entities.hospital.ProcedureRatesEntity;
import in.jamuna.hms.entities.hospital.TestParametersEntity;
import in.jamuna.hms.entities.hospital.TestsEntity;

@Service
public class LabService {

	@Autowired
	ProceduresDAO proceduresDAO;
	@Autowired
	TestParametersDAO testParametersDAO;
	@Autowired
	ProcedureBillDAO procedureBillDAO;
	@Autowired
	TestsDAO testsDAO;
	@Autowired
	LabCategoryDAO labCategoryDAO; 
	@Autowired
	PatientService patientService;
	@Autowired
	BillingService billingService;
	
	private static final Logger LOGGER=
			Logger.getLogger(LabService.class.getName());
	
	public List<ProcedureRatesEntity> getAllTests() {
		
		return proceduresDAO.findByBillGroupId(GlobalValues.getLabGroupId());
	}
	
	@Transactional
	public void addTest(HttpServletRequest request) {
		try {
			
			//get number of rows
			int rows=Integer.parseInt(request.getParameter("rows"));
			int testId=Integer.parseInt( request.getParameter("test") );
			
			ProcedureRatesEntity test=proceduresDAO.findById(testId);
			
			for(int i=0;i<rows;i++ ) {
				String parameter=request.getParameter("para_"+i);
				String unit=request.getParameter("unit_"+i);
				Float lower=null;
				Float high=null;
				
				char refNeeded='N';
				
				if(!unit.equals(""))
				{
					lower=Float.parseFloat(request.getParameter("low_"+i));
					high=Float.parseFloat(request.getParameter("high_"+i));
					refNeeded='Y';
				}
				LOGGER.info("row: "+rows+" test:"+testId+" para:"+parameter+" unit:"+unit+" low:"+lower + "high:"+high);
				testParametersDAO.saveTest(
						test,
						parameter,
						unit,
						lower,
						high,
						refNeeded
						);
				
			}
			
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		
	}

	public List<ProcedureRatesEntity> getTestsByTid(int tid) {
		
		Set<ProcedureBillItemEntity> items=procedureBillDAO.findByTid(tid).getBillItems();
		
		List<ProcedureRatesEntity> tests=new ArrayList<ProcedureRatesEntity>();
		
		//get all items with bill group id as LAB
		for(ProcedureBillItemEntity item:items) {
			int groupId=item.getProcedure().getBillGroup().getId();
			if(groupId== GlobalValues.getLabGroupId()) {
				tests.add( item.getProcedure() );
			}
		}
		
		return tests;
	}

	@Transactional
	public boolean saveReport(HttpServletRequest request) {
		try {
			
			int tid=Integer.parseInt(request.getParameter("tid"));
			ProcedureBillEntity bill=procedureBillDAO.findByTid(tid);
			
			//check if test aleady saved
			List<TestsEntity> tests=new ArrayList<TestsEntity>();
			tests=testsDAO.findByBill(bill);
			
			if(tests.size()==0) {
				
				List<ProcedureRatesEntity> labProcedures=getTestsByTid(tid);
				for(ProcedureRatesEntity test:labProcedures) {
					
					for( TestParametersEntity para:test.getParameters()) {
						int paraId=para.getId();
						
						testsDAO.saveValue(
								bill,
								para,
								request.getParameter("value_"+paraId)
								);
					}
					
				}
				
				return true;
			}
			
			
			
			
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		
		return false;
		
	}

	public List<TestsEntity> getTestValuesByTid(int tid) {
		ProcedureBillEntity bill=procedureBillDAO.findByTid(tid);
		return testsDAO.findByBill(bill);
	}

	
	public void changeValueOfTestParameterById(int id, String value) {
		try {
			testsDAO.changeValueById(id,value);
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
	}

	public boolean checkIfTestCompletedByTid(int tid) {
		//check if test aleady saved
		ProcedureBillEntity bill=procedureBillDAO.findByTid(tid);
		List<TestsEntity> tests=new ArrayList<TestsEntity>();
		tests=testsDAO.findByBill(bill);
		
		if(tests.size()>0) {
			return true;
		}
		
		return false;
	}

	public List<LabCategoryEntity> getAllLabCategories() {
		
		return labCategoryDAO.getLabCategories();
	}

	@Transactional
	public void addCategory(String parameter) {
		try {
			labCategoryDAO.saveCategory(parameter);
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
	}

	@Transactional
	public void addCatToTest(int testId, int categoryId) {
		try {
			
			proceduresDAO.setCategory( labCategoryDAO.findById(categoryId),testId );
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
	}


	public Set<LabCategoryEntity> getCategoriesFromTestList(List<ProcedureRatesEntity> tests) {
		Set<LabCategoryEntity> cats=new HashSet<>();
		for(ProcedureRatesEntity test:tests) {
			cats.add(test.getCategory());
		}
		
		return cats;
	}

	public List<TestParametersEntity> getParametersByTestId(int testId) {
		
		return proceduresDAO.findById(testId).getParameters();
	}

	public ProcedureRatesEntity getTestByTestId(int parseInt) {
		
		return proceduresDAO.findById(parseInt);
	}

	@Transactional
	public void saveParameterById(String type, int paraId, HttpServletRequest req) {
		TestParametersEntity para=testParametersDAO.findById(paraId);
		
		if(type.equals("parameter")) {
			String parameterName=req.getParameter("parameter");
			para.setName(parameterName);
		}
		else if(type.equals("unit")){
			
			String unit=req.getParameter("unit");
			para.setUnit(unit);
		}
		else if(type.equals("high")) {
			
			Float high=Float.parseFloat(req.getParameter("high"));
			para.setUpperRange(high);
		}
		else if(type.equals("low")) {
			Float low=Float.parseFloat(req.getParameter("low"));
			para.setLowerRange(low);
		}
		
		testParametersDAO.saveParameter(para);
		
	}

	@Transactional
	public List<ProcedureBillEntity> findReportsByNameAndAge(String fname, String lname, int age) {
		List<ProcedureBillEntity> bills=new ArrayList<>();
		try {
			//find patient with this age and name
			List<PatientEntity> patients=patientService.patientDAO.getPatientByName(fname, lname );
			
			for(PatientEntity patient:patients) {
			  //if age == updatedAge add to list
				int originalAge=patient.getAge();
				if( patientService.updateAge(patient).getAge() == age ) {
					patient.setAge(originalAge);
					
					for(ProcedureBillEntity bill: billingService.getLabBillByPatient(patient) ) {
						bills.add(bill);
					}
				}
					
			}
			
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		
		
		return bills;
	}

	

	
}
