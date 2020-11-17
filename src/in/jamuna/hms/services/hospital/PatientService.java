package in.jamuna.hms.services.hospital;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dao.hospital.PatientDAO;
import in.jamuna.hms.dto.patient.PatientDTO;
import in.jamuna.hms.entities.hospital.PatientEntity;


@Service
public class PatientService {
	
	@Autowired
	PatientDAO patientDAO;
	

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

	
	
	public PatientEntity updateAge(PatientEntity patientEntity) {
		LocalDate firstDateOfVisit=patientEntity.getFirstDateOfVisit().toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
		
        LocalDate today = LocalDate.now();
        
        Period age = Period.between(firstDateOfVisit, today);
        int years = age.getYears();
		
		patientEntity.setAge( patientEntity.getAge() + years );
		
		return patientEntity;
	}

	public List<PatientEntity> getPatientsByCriteriaWithLimit(PatientDTO patient, String criteria) {
		List<PatientEntity> list=new ArrayList<>();
		
		if(criteria.equals("name"))
		{
			list=patientDAO.getPatientByNameWithLimit(patient.getFname(), patient.getLname(),GlobalValues.getSearchlimit()).
					stream().map( patientEntity -> updateAge(patientEntity) ).collect(Collectors.toList());
		}	
		else if( criteria.equals("id") )
		{
			list.add(updateAge(patientDAO.getPatientById(patient.getId())));
		}
		else if( criteria.equals("mobile")) {
			list=patientDAO.getPatientByMobileWithLimit(patient.getMobile(),GlobalValues.getSearchlimit()).
					stream().map( patientEntity -> updateAge(patientEntity) ).collect(Collectors.toList());
		}
		
		return list;
	}



	
	
}
