package in.jamuna.hms.services.hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dao.hospital.LabCategoryDAO;
import in.jamuna.hms.dao.hospital.PatientDAO;
import in.jamuna.hms.dao.hospital.ProcedureBillDAO;
import in.jamuna.hms.dao.hospital.ProcedureBillItemDAO;
import in.jamuna.hms.dao.hospital.ProceduresDAO;
import in.jamuna.hms.dao.hospital.TestParametersDAO;
import in.jamuna.hms.dao.hospital.TestsDAO;
import in.jamuna.hms.dto.BillDTO;
import in.jamuna.hms.dto.LabReportByCategoryDTO;
import in.jamuna.hms.dto.TestDTO;
import in.jamuna.hms.dto.TestParameterDTO;
import in.jamuna.hms.dto.common.CommonIdAndNameDto;
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
	ProcedureBillItemDAO procedureBillItemDAO;
	@Autowired
	ProceduresDAO proceduresDAO;
	@Autowired
	PatientDAO patientDAO;
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
	@Autowired
	ConverterService converter;
	@Autowired
	ModelMapper mapper;
	
	private static final Logger LOGGER=
			Logger.getLogger(LabService.class.getName());
	
	public List<CommonIdAndNameDto> getAllTests() {
		
		return proceduresDAO.findByBillGroupId(GlobalValues.getLabGroupId()).stream()
				.map(item -> {
					return new CommonIdAndNameDto(item.getId(), item.getProcedure());
				}).collect(Collectors.toList());
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

	public List<CommonIdAndNameDto> getTestValuesByTid(int tid) {
		ProcedureBillEntity bill=procedureBillDAO.findByTid(tid);
		return testsDAO.findByBill(bill).stream().map(item -> {
			return new CommonIdAndNameDto(item.getId(), item.getValue());
		}).collect(Collectors.toList());
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

	public List<CommonIdAndNameDto> getAllLabCategories() {
		
		return labCategoryDAO.getLabCategories().stream()
				.map(item->converter.mapper.map(item, CommonIdAndNameDto.class))
				.collect(Collectors.toList());
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


	
	public List<TestParameterDTO> getParametersByTestId(int testId) {
		
		return proceduresDAO.findById(testId).getParameters().stream().
				map(para -> converter.mapper.map(para, TestParameterDTO.class )).collect(Collectors.toList());
	}

	public CommonIdAndNameDto getTestByTestId(int parseInt) {
		ProcedureRatesEntity proc =proceduresDAO.findById(parseInt);
		return new CommonIdAndNameDto(proc.getId(),proc.getProcedure());
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
	public List<BillDTO> findReportsByNameAndAge(String fname, String lname, int age) {
		List<BillDTO> bills=new ArrayList<>();
		try {
			//find patient with this age and name
			List<PatientEntity> patients=patientDAO.getPatientByName(fname, lname );
			
			for(PatientEntity patient:patients) {
			  //if age == updatedAge add to list
				int originalAge=patient.getAge();
				if( patientService.updateAge(patient).getAge() == age ) {
					patient.setAge(originalAge);
					
					for(ProcedureBillEntity bill: billingService.getLabBillByPatient(patient) ) {
						if( bill.getBillItems().stream().filter(item -> item.getProcedure().getBillGroup().getId() == GlobalValues.getLabGroupId()).count() > 0 )
							bills.add( converter.convert(bill) );
					}
				}
					
			}
			
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		
		
		return bills;
	}

	public List<TestDTO> getAllTestsWithCategory() {
		
		return proceduresDAO.findByBillGroupId(GlobalValues.getLabGroupId()).stream()
				.map(item -> converter.convert(item)).collect(Collectors.toList());
	}

	public List<TestDTO>  getTestsWithParametersByTid(int tid) {
		
		try {
			List<ProcedureRatesEntity> set = proceduresDAO.findByTidAndBillGroup(
					tid,GlobalValues.getLabGroupId());
			
			return set.stream().map(test -> {
						TestDTO dto = new TestDTO();
						dto.setName(test.getProcedure());
						dto.setId(test.getId());
						LOGGER.info(test.getProcedure()+"");
						LOGGER.info(test.getParameters().size()+"");
						if(test.getParameters()!=null) {
							List<TestParameterDTO> paras = test.getParameters().stream()
									.map(para -> mapper.map(para, TestParameterDTO.class))
									.collect(Collectors.toList()); 
							LOGGER.info(paras.size()+"");
									dto.setParameters(paras);
						}
						return dto;
					}).collect(Collectors.toList());

		}catch(Exception e) {
			LOGGER.info(e.toString());
		} 
		return new ArrayList<TestDTO>();
	}

	public List<LabReportByCategoryDTO> getReportPrintDTOByTid(int tid) {
		try {
			ProcedureBillEntity bill = procedureBillDAO.findByTid(tid);
			List<TestsEntity> values = testsDAO.findByBill(bill);
			List<LabCategoryEntity> categories = labCategoryDAO.findCategoriesOfCompletedTestsByBill(bill);
			
			List<ProcedureRatesEntity> tests = proceduresDAO.findProceduresFromTestValuesByBill(bill)
					.stream().collect(Collectors.toList());
			
			List<LabReportByCategoryDTO> list = new ArrayList<LabReportByCategoryDTO>();
			LOGGER.info("tests  "+tests.size());
			
			
			for(LabCategoryEntity cat:categories) {
				LabReportByCategoryDTO dto = new LabReportByCategoryDTO();
				dto.setId(cat.getId());
				dto.setName(cat.getName());
				dto.setTests(
						tests.stream().filter(
								test -> test.getCategory().getId() == cat.getId()
								).map(test -> {
									
									TestDTO t = new TestDTO();
									t.setId(test.getId());
									t.setName(test.getProcedure());
									t.setParameters(
											test.getParameters().stream()
											.map(para -> mapper.map(para, TestParameterDTO.class))
											.collect(Collectors.toList())
											);
									t.setValues( 
											values.stream().filter( v -> v.getParameter().getTest().getId() == test.getId() )
											.map(v -> {
												return new CommonIdAndNameDto(v.getId(), v.getValue());
											}).collect(Collectors.toList())
											);
									
									return t;
								}).collect(Collectors.toList())
						);
				
				
				list.add(dto);
				
			}
			
			
			return list;
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return new ArrayList<LabReportByCategoryDTO>();
	}

	

	
}
