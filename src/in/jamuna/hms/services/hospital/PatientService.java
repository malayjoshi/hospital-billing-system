package in.jamuna.hms.services.hospital;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dao.hospital.PatientDAO;
import in.jamuna.hms.dto.patient.PatientDTO;
import in.jamuna.hms.entities.hospital.PatientEntity;


@Service
@Transactional
public class PatientService {
	
	@Autowired
	private PatientDAO patientDAO;
	@Autowired
	private ModelMapper mapper;
	


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
        int years = age.getYears();
		
		return years;
	}

	public List<PatientDTO> getPatientsByCriteriaWithLimit(PatientDTO patient, String criteria) {
		List<PatientEntity> list=new ArrayList<>();
		
		if(criteria.equals("name"))
		{
			list=patientDAO.getPatientByNameWithLimit(patient.getFname(), patient.getLname(),GlobalValues.getSearchlimit());
		}	
		else if( criteria.equals("id") )
		{
			list.add(patientDAO.getPatientById(patient.getId()));
		}
		else if( criteria.equals("mobile")) {
			list=patientDAO.getPatientByMobileWithLimit(patient.getMobile(),GlobalValues.getSearchlimit());
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
