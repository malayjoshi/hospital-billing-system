package in.jamuna.hms.services.hospital;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.jamuna.hms.dao.hospital.EmployeeDAO;
import in.jamuna.hms.dao.hospital.RolesDAO;
import in.jamuna.hms.dto.login.CredentialsDto;
import in.jamuna.hms.dto.login.SessionDto;
import in.jamuna.hms.entities.hospital.EmployeeEntity;
import in.jamuna.hms.entities.hospital.RolesEntity;

 
@Service
public class EmployeeService {
	@Autowired
	private EmployeeDAO employeeDAO;
	@Autowired
	private RolesDAO rolesDAO;
	
	private static final Logger LOGGER=Logger.getLogger(EmployeeService.class.getName());
	
	public List<RolesEntity> getAllRoles() {
		// TODO Auto-generated method stub
		
		return rolesDAO.getAllRoles();
	}

	public SessionDto checkCredentials(CredentialsDto cred) {
		SessionDto result=new SessionDto();
		
		try {
			
			RolesEntity role=rolesDAO.findByRoleId(cred.getRoleId());
			LOGGER.info("role:"+role.getRole());
			List<EmployeeEntity> list=employeeDAO.findByMobileAndPasswordAndRole(cred.getMobile(),cred.getPassword(),role);
			
			if (list.size()==1) {
				
				EmployeeEntity employee=list.get(0);
				if(employee.isEnabled()) {
					result.setEmpId(employee.getId());
					result.setName(employee.getName());
					result.setRole(employee.getRole().getRole());
				}
				
			}
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		
			return result;
	}
 

 }

