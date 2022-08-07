package in.jamuna.hms.services.hospital;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dao.hospital.employee.PatientDAO;
import in.jamuna.hms.dto.patient.PatientDTO;
import in.jamuna.hms.entities.hospital.patient.PatientEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class PatientService {
	
	private final PatientDAO patientDAO;
	private final ModelMapper mapper;

	public PatientService(PatientDAO patientDAO, ModelMapper mapper) {
		this.patientDAO = patientDAO;
		this.mapper = mapper;
	}


	public int savePatient(PatientDTO patient) {
		return patientDAO.savePatient(
				patient.getFname(),patient.getLname(),patient.getGuardian(),patient.getMobile(),
				patient.getAddress(),patient.getAge(),patient.getSex()
				);
		
	}
	
	public void saveEditedPatient(PatientDTO patient,int id) {
		patientDAO.saveEditedPatient(id,
				patient.getFname(),patient.getLname(),patient.getGuardian(),patient.getMobile(),
				patient.getAddress(),patient.getAge(),patient.getSex()
				);
	}

	
	
	public int addYearsToAge(PatientEntity patientEntity) {
		LocalDate firstDateOfVisit=patientEntity.getFirstDateOfVisit().toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
		
        LocalDate today = LocalDate.now();
        
        Period age = Period.between(firstDateOfVisit, today);

		return age.getYears();
	}

	public List<PatientDTO> getPatientsByCriteriaWithLimit(PatientDTO patient, String criteria) {
		List<PatientEntity> list=new ArrayList<>();

		switch (criteria) {
			case "name":
				list = patientDAO.getPatientByNameWithLimit(patient.getFname(), patient.getLname(), GlobalValues.getSearchlimit());
				break;
			case "id":
				list.add(patientDAO.getPatientById(patient.getId()));
				break;
			case "mobile":
				list = patientDAO.getPatientByMobileWithLimit(patient.getMobile(), GlobalValues.getSearchlimit());
				break;
		}
		
		return list.stream().map(p -> {
			PatientDTO dto = mapper.map(p, PatientDTO.class);
			dto.setAge(addYearsToAge(p)+p.getAge());
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
			dto.setFirstDateOfVisit( dateFormat.format(p.getFirstDateOfVisit()) );
			return dto;
		}).collect(Collectors.toList());
	}



	
	
}
