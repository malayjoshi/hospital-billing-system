package in.jamuna.hms.services.hospital;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dao.hospital.AttendanceDAO;
import in.jamuna.hms.dao.hospital.EmployeeTotalDAO;
import in.jamuna.hms.dao.hospital.PresetsDAO;
import in.jamuna.hms.dto.payroll.AttendancePerEmployeeDTO;
import in.jamuna.hms.entities.hospital.AttendanceEntity;
import in.jamuna.hms.entities.hospital.EmployeesTotalEntity;
import in.jamuna.hms.entities.hospital.PresetValuesEntity;

@Service
public class PayrollService {
	
	@Autowired
	private EmployeeTotalDAO employeeTotalDAO;
	@Autowired
	private PresetsDAO presetsDAO;
	@Autowired
	private AttendanceDAO attendanceDAO;

	private static final Logger LOGGER=Logger.getLogger(PayrollService.class.getName());

	public List<EmployeesTotalEntity> getEmployees() {
		
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

	public List<PresetValuesEntity> getPresets() {
		
		return presetsDAO.getPresets();
	}

	public List<EmployeesTotalEntity> getEnabledEmployees() {
		
		return employeeTotalDAO.getEmployees().stream().filter( emp -> emp.isEnabled() ).collect(Collectors.toList());
	}

	@Transactional
	public boolean addAttendance(HttpServletRequest req, Date date) {
		boolean added=true;
		try {
			//check if attendance for the given date exists
			if( attendanceDAO.getAttendanceByDate(date).size()==0 ) {
				
				//get all emp ids
				List<EmployeesTotalEntity> employees=getEnabledEmployees();
				for(EmployeesTotalEntity emp:employees) {
					
					String presetId=req.getParameter("day_"+emp.getId());
					PresetValuesEntity day=presetsDAO.findById( Integer.parseInt(presetId) );
					
					presetId=req.getParameter("night_"+emp.getId());
					PresetValuesEntity night=presetsDAO.findById( Integer.parseInt(presetId) );
					
					attendanceDAO.saveAttendance(emp,day,night,date);
				}
				
			}
			else {
				added=false;
			}
			
			
			
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		return added;
	}

	public List<AttendanceEntity> getAttendanceByDate(Date date) {
		
		return attendanceDAO.getAttendanceByDate(date);
	}

	public void updateAttendanceById(int id, int day, int night) {
		try {
			attendanceDAO.updateAttendanceById(
					id,
					presetsDAO.findById(day),
					presetsDAO.findById(night));
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
	}

	public List<AttendancePerEmployeeDTO> getAttendanceDatailsByMonthYear(Date date) {

		List<AttendancePerEmployeeDTO> attendances=new ArrayList<>();
		try {
			
			// get enabled employees
			List<EmployeesTotalEntity> emps = employeeTotalDAO.getEmployees().stream().filter( emp -> emp.isEnabled() ).collect(Collectors.toList());
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int month=calendar.get(Calendar.MONTH)+1;
			int year=calendar.get(Calendar.YEAR);
			
			for(EmployeesTotalEntity emp:emps) {
				AttendancePerEmployeeDTO dto=new AttendancePerEmployeeDTO();
				dto.setEmployee(emp);
				LOGGER.info(month+" "+year);
				//get days for that month for that employee
				dto.setFulls( 
						attendanceDAO.getCountOfDayAttendanceByPresetAndMonthYearAndEmployee(
						presetsDAO.findById(GlobalValues.getTypePresentId()),
						month,
						year,
						emp
						) 
						+
						attendanceDAO.getCountOfNightAttendanceByPresetAndMonthYearAndEmployee(
						presetsDAO.findById(GlobalValues.getTypePresentId()),
						month,
						year,
						emp
						)
						);
				
				dto.setHalfs(
						attendanceDAO.getCountOfDayAttendanceByPresetAndMonthYearAndEmployee(
								presetsDAO.findById(GlobalValues.getTypeHalfId()),
								month,
								year,emp
								) 
								+
								attendanceDAO.getCountOfNightAttendanceByPresetAndMonthYearAndEmployee(
								presetsDAO.findById(GlobalValues.getTypeHalfId()),
								month,
								year,emp
								)
						);
				attendances.add(dto);
			}
			
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		
		return attendances;
	}

	
	
}
