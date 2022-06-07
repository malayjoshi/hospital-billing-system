package in.jamuna.hms.services.hospital;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.jamuna.hms.dao.hospital.DoctorRateDAO;
import in.jamuna.hms.dao.hospital.EmployeeDAO;
import in.jamuna.hms.dao.hospital.RolesDAO;
import in.jamuna.hms.dao.hospital.VisitDAO;
import in.jamuna.hms.dto.RolesDTO;
import in.jamuna.hms.dto.common.CommonIdAndNameDto;
import in.jamuna.hms.dto.doctorrate.DoctorRateDTO;
import in.jamuna.hms.dto.employee.EmployeeInfo;
import in.jamuna.hms.dto.employee.NewEmployeeDTO;
import in.jamuna.hms.dto.login.CredentialsDto;
import in.jamuna.hms.dto.login.SessionDto;
import in.jamuna.hms.entities.hospital.DoctorRateEntity;
import in.jamuna.hms.entities.hospital.EmployeeEntity;
import in.jamuna.hms.entities.hospital.RolesEntity;

 
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
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private ConverterService converter;
	
	private static final Logger LOGGER=Logger.getLogger(EmployeeService.class.getName());
	
	public List<RolesDTO> getAllRoles() {
		List<RolesDTO> list=new ArrayList<>();
		for(RolesEntity role:rolesDAO.getAllRoles()) {
			list.add(mapper.map(role, RolesDTO.class));
		}
		return list;
	}

	public SessionDto checkCredentials(CredentialsDto cred) {
		
		SessionDto session=null;
		
		
		try {
			LOGGER.info(cred.getMobile()+" "+cred.getPassword()+" "+cred.getRoleId());
			
			List<EmployeeEntity> employees=employeeDAO.
					findByMobileAndRoleAndPasswordOptional( 
							cred.getMobile(), rolesDAO.findByRoleId(cred.getRoleId()), false, "");
			
			if(employees.size()==1 && encoder.matches(cred.getPassword(), employees.get(0).getPassword())) {
				session=new SessionDto();
				session.setEmpId(employees.get(0).getId());
				session.setName(session.getName());
				session.setRole(employees.get(0).getRole().getRole());
			}
			
		}catch(Exception e) {
			LOGGER.info("exception:"+e.getMessage());
			
		}
		
		return session;
	}
	

	public boolean addEmployee(NewEmployeeDTO employee) {
		
		employee.setPassword(encoder.encode(employee.getPassword()));
		
		List<EmployeeEntity> employees=employeeDAO.findByMobileAndRoleAndPasswordOptional(
				employee.getMobile(),rolesDAO.findByRoleId(employee.getRoleId())
				, false, "");
				
		boolean result=true;
		if( !employees.isEmpty()) {
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

	public List<CommonIdAndNameDto> getAllVisitTypes() {
		
		return visitDAO.getAllVisitTypes().stream().map(
				visit -> converter.convert(visit)).collect(Collectors.toList());
	}

	public Set<CommonIdAndNameDto>  getAllDoctors() {
		return rolesDAO.findByRole("DOCTOR").stream().map(
				doc -> mapper.map(doc, CommonIdAndNameDto.class)).collect(Collectors.toSet());
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

	public List<DoctorRateDTO> getAllDoctorRates() {
		List<DoctorRateEntity> rates=doctorRateDAO.getAllDoctorRates();
		List<DoctorRateDTO> list=new ArrayList<>();
		for(DoctorRateEntity rate:rates) {
			DoctorRateDTO dto=new DoctorRateDTO();
			dto.setDoctor(rate.getDoctor().getName());
			dto.setEndTime( new SimpleDateFormat("hh.mm a").format(rate.getEndTime()));
			dto.setStartTime( new SimpleDateFormat("hh.mm a").format(rate.getStartTime()));
			dto.setId(rate.getRate_id());
			dto.setRate(rate.getRate());
			dto.setVisit(rate.getVisitType().getVisit());
			list.add(dto);
		}
		return list;
	}

	public void deleteDoctorRate(int rateId) {
		doctorRateDAO.deleteDoctorRate(rateId);
		
	}

	
 

 }

