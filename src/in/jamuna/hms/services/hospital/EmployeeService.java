package in.jamuna.hms.services.hospital;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.jamuna.hms.dao.hospital.DoctorRateDAO;
import in.jamuna.hms.dao.hospital.EmployeeDAO;
import in.jamuna.hms.dao.hospital.RolesDAO;
import in.jamuna.hms.dao.hospital.VisitDAO;
import in.jamuna.hms.dto.doctorrate.DoctorRateDTO;
import in.jamuna.hms.dto.employee.EmployeeInfo;
import in.jamuna.hms.dto.employee.NewEmployeeDTO;
import in.jamuna.hms.dto.login.CredentialsDto;
import in.jamuna.hms.dto.login.SessionDto;
import in.jamuna.hms.entities.hospital.EmployeeEntity;
import in.jamuna.hms.entities.hospital.RolesEntity;
import in.jamuna.hms.entities.hospital.VisitTypeEntity;

 
@Service
public class EmployeeService {
	@Autowired
	private EmployeeDAO employeeDAO;
	@Autowired
	private RolesDAO rolesDAO;
	@Autowired
	private VisitDAO visitDAO;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private DoctorRateDAO doctorRateDAO;
	
	private static final Logger LOGGER=Logger.getLogger(EmployeeService.class.getName());
	
	public List<RolesEntity> getAllRoles() {
		List<RolesEntity> list=rolesDAO.getAllRoles();
		for(RolesEntity role:list) {
			LOGGER.info("role:"+role.toString());
		}
		return list;
	}

	public SessionDto checkCredentials(CredentialsDto cred) {
		SessionDto result=new SessionDto();
		
		try {
			
			RolesEntity role=rolesDAO.findByRoleId(cred.getRoleId());
			LOGGER.info("role:"+role.getRole());
			List<EmployeeEntity> list=employeeDAO.findByMobileAndRoleAndPasswordOptional(cred.getMobile(),role ,true,cred.getPassword() );
			
			if (list.size()==1) {
				
				EmployeeEntity employee=list.get(0);
				result.setEmpId(employee.getId());
				result.setName(employee.getName());
				result.setRole(employee.getRole().getRole());
				
			}
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		
			return result;
	}

	public boolean addEmployee(NewEmployeeDTO employee) {
		
		List<EmployeeEntity> employees=employeeDAO.findByMobileAndRoleAndPasswordOptional(
				employee.getMobile(),rolesDAO.findByRoleId(employee.getRoleId())
				, false, "");
				
		boolean result=true;
		if(employees.size()>0) {
			result=false;
		}else {
			employeeDAO.addEmployee( mapper.map(employee,EmployeeEntity.class) );
		}
		
		return result;
	}

	public int getTotalEmployees() {
		
		return employeeDAO.getAllEmployees().size();
	}

	public	List<EmployeeInfo> getEmployeesByPage(Integer pageNum, int perpage) {
		
		return employeeDAO.getEmployeesByPage(pageNum,perpage).stream().
				map(employee-> mapper.map(employee, EmployeeInfo.class) ).collect(Collectors.toList());
	}

	public void deleteEmployee(int id) {
		employeeDAO.deleteEmployee(id);
	}

	public List<VisitTypeEntity> getAllVisitTypes() {
		
		return visitDAO.getAllVisitTypes();
	}

	public Set<EmployeeEntity>  getAllDoctors() {
		return rolesDAO.findByRole("DOCTOR");
	}

	public void saveDoctorRate(DoctorRateDTO rate) {
		DateFormat dateFormat = new SimpleDateFormat("hh:mm");
		
		try {
			doctorRateDAO.saveDoctorRate(
					visitDAO.findById(rate.getVisitId()),employeeDAO.findById(rate.getEmpId()),
					dateFormat.parse(rate.getStartTime()),dateFormat.parse(rate.getEndTime()),rate.getRate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}

	
 

 }

