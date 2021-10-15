package in.jamuna.hms.services.hospital;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.jamuna.hms.dao.hospital.EmployeeTotalDAO;
import in.jamuna.hms.entities.hospital.EmployeesTotalEntity;

@Service
public class PayrollService {
	
	@Autowired
	private EmployeeTotalDAO employeeTotalDAO;

	private static final Logger LOGGER=Logger.getLogger(PayrollService.class.getName());

	public List<EmployeesTotalEntity> getEmployees() {
		// TODO Auto-generated method stub
		return employeeTotalDAO.getEmployees();
	}

	public void addEmployee(String name, String mobile) {
		try {
			employeeTotalDAO.addEmployee(name,mobile);
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		
	}

	public void enableDisableEmployee(boolean b, int id) {
		try {
			employeeTotalDAO.setEnabled(b,id);
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
	}
	
}
