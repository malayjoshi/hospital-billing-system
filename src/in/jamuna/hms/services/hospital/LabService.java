package in.jamuna.hms.services.hospital;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dao.hospital.billing.ProcedureBillDAO;
import in.jamuna.hms.dao.hospital.billing.ProcedureBillItemDAO;
import in.jamuna.hms.dao.hospital.billing.ProceduresDAO;
import in.jamuna.hms.dao.hospital.employee.PatientDAO;
import in.jamuna.hms.dao.hospital.lab.LabCategoryDAO;
import in.jamuna.hms.dao.hospital.lab.TestParametersDAO;
import in.jamuna.hms.dao.hospital.lab.TestsDAO;
import in.jamuna.hms.dto.cart.BillDTO;
import in.jamuna.hms.dto.reports.LabReportByCategoryDTO;
import in.jamuna.hms.dto.reports.TestDTO;
import in.jamuna.hms.dto.reports.TestParameterDTO;
import in.jamuna.hms.dto.common.CommonIdAndNameDto;
import in.jamuna.hms.entities.hospital.billing.ProcedureBillEntity;
import in.jamuna.hms.entities.hospital.billing.ProcedureBillItemEntity;
import in.jamuna.hms.entities.hospital.billing.ProcedureRatesEntity;
import in.jamuna.hms.entities.hospital.lab.LabCategoryEntity;
import in.jamuna.hms.entities.hospital.lab.TestParametersEntity;
import in.jamuna.hms.entities.hospital.lab.TestsEntity;
import in.jamuna.hms.entities.hospital.patient.PatientEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class LabService {
	final
    ProcedureBillItemDAO procedureBillItemDAO;
	final
    ProceduresDAO proceduresDAO;
	final
	PatientDAO patientDAO;
	final
	TestParametersDAO testParametersDAO;
	final
    ProcedureBillDAO procedureBillDAO;
	final
	TestsDAO testsDAO;
	final
	LabCategoryDAO labCategoryDAO;
	final
	PatientService patientService;
	final
	BillingService billingService;
	final
	ConverterService converter;
	final
	ModelMapper mapper;
	
	private static final Logger LOGGER=
			Logger.getLogger(LabService.class.getName());

	public LabService(PatientDAO patientDAO, ProcedureBillItemDAO procedureBillItemDAO, ProceduresDAO proceduresDAO, TestParametersDAO testParametersDAO, ProcedureBillDAO procedureBillDAO, TestsDAO testsDAO, LabCategoryDAO labCategoryDAO, PatientService patientService, BillingService billingService, ConverterService converter, ModelMapper mapper) {
		this.patientDAO = patientDAO;
		this.procedureBillItemDAO = procedureBillItemDAO;
		this.proceduresDAO = proceduresDAO;
		this.testParametersDAO = testParametersDAO;
		this.procedureBillDAO = procedureBillDAO;
		this.testsDAO = testsDAO;
		this.labCategoryDAO = labCategoryDAO;
		this.patientService = patientService;
		this.billingService = billingService;
		this.converter = converter;
		this.mapper = mapper;
	}

	public List<CommonIdAndNameDto> getAllTests() {
		
		return proceduresDAO.findByBillGroupId(GlobalValues.getLabGroupId()).stream()
				.map(item -> new CommonIdAndNameDto(item.getId(), item.getProcedure())
				).collect(Collectors.toList());
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
		
		List<ProcedureRatesEntity> tests=new ArrayList<>();
		
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
			List<TestsEntity> tests;
			tests=testsDAO.findByBill(bill);
			
			if(tests.isEmpty()) {
				
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

		return !testsDAO.findByBill(bill).isEmpty();
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
				map(para -> mapper.map(para, TestParameterDTO.class )).collect(Collectors.toList());
	}

	public CommonIdAndNameDto getTestByTestId(int parseInt) {
		ProcedureRatesEntity proc =proceduresDAO.findById(parseInt);
		return new CommonIdAndNameDto(proc.getId(),proc.getProcedure());
	}

	@Transactional
	public void saveParameterById(String type, int paraId, HttpServletRequest req) {
		TestParametersEntity para=testParametersDAO.findById(paraId);

		switch (type) {
			case "parameter":
				String parameterName = req.getParameter("parameter");
				para.setName(parameterName);
				break;
			case "unit":

				String unit = req.getParameter("unit");
				para.setUnit(unit);
				break;
			case "high":

				Float high = Float.parseFloat(req.getParameter("high"));
				para.setUpperRange(high);
				break;
			case "low":
				Float low = Float.parseFloat(req.getParameter("low"));
				para.setLowerRange(low);
				break;
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
				if( patient.getAge()+patientService.addYearsToAge(patient) == age ) {
					
					
					for(ProcedureBillEntity bill: billingService.getLabBillByPatient(patient) ) {
						if(bill.getBillItems().stream().anyMatch(item -> item.getProcedure().getBillGroup().getId() == GlobalValues.getLabGroupId()))
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
				.map(converter::convert).collect(Collectors.toList());
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
							
									dto.setParameters(paras);
							
							//get values
							List<CommonIdAndNameDto> values = testsDAO.findByTestAndTid(test, tid ).stream()
									.map(v -> 
										 new CommonIdAndNameDto(v.getId(), v.getValue())
									).collect(Collectors.toList());
							dto.setValues(values);
						}
						
						
						
						return dto;
					}).collect(Collectors.toList());

		}catch(Exception e) {
			LOGGER.info(e.toString());
		} 
		return new ArrayList<>();
	}

	public List<LabReportByCategoryDTO> getReportPrintDTOByTid(int tid) {
		try {
			ProcedureBillEntity bill = procedureBillDAO.findByTid(tid);
			List<TestsEntity> values = testsDAO.findByBill(bill);
			List<LabCategoryEntity> categories = labCategoryDAO.findCategoriesOfCompletedTestsByBill(bill);
			
			List<ProcedureRatesEntity> tests = new ArrayList<>(proceduresDAO.findProceduresFromTestValuesByBill(bill));
			
			List<LabReportByCategoryDTO> list = new ArrayList<>();
			
			
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
											.map(v -> new CommonIdAndNameDto(v.getId(), v.getValue())
											).collect(Collectors.toList())
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
		
		return new ArrayList<>();
	}

	public void saveChangesOfEditReport(int tid, HttpServletRequest request) {
		for( TestsEntity val: testsDAO.findByBill( procedureBillDAO.findByTid(tid) ) ){
			if( !request.getParameter("value-"+val.getId()).equals("") ) {
				changeValueOfTestParameterById(val.getId(), request.getParameter("value-"+val.getId()));
			}
		}
		
	}

	

	
}
