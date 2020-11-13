package in.jamuna.hms.services.hospital;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.jamuna.hms.dao.hospital.PatientDAO;
import in.jamuna.hms.dto.patient.PatientDTO;
import in.jamuna.hms.entities.hospital.PatientEntity;

@Service
public class PatientService {

	@Autowired
	PatientDAO patientDAO;
	
	public List<PatientEntity> getPatientByNameOrGuardianOrMobile(PatientDTO patient) {
		
		return patientDAO.getPatientByNameOrGuardianOrMobile(
				patient.getFname(),patient.getLname(),patient.getGuardian(),patient.getMobile());
	}

	public int savePatient(PatientDTO patient) {
		return patientDAO.savePatient(
				patient.getFname(),patient.getLname(),patient.getGuardian(),patient.getMobile(),
				patient.getAddress(),patient.getAge(),patient.getSex()
				);
		
	}
	
	
	
}
