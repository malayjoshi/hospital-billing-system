package in.jamuna.hms.services.hospital;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dao.hospital.ProceduresDAO;
import in.jamuna.hms.dao.hospital.TestParametersDAO;
import in.jamuna.hms.entities.hospital.ProcedureRatesEntity;

@Service
public class LabService {

	@Autowired
	ProceduresDAO proceduresDAO;
	@Autowired
	TestParametersDAO testParametersDAO;
	
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

	
}
