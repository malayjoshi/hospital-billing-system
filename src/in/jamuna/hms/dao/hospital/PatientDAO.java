package in.jamuna.hms.dao.hospital;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.PatientEntity;

@Repository
public class PatientDAO {

	@Autowired
	SessionFactory sessionFactory;

	
	@Transactional
	public List<PatientEntity> getPatientByNameWithLimit(String fname, String lname, int limit) {
		Query query=sessionFactory.getCurrentSession().
				createQuery("from PatientEntity where fname like '%"+fname+"%' and lname like '%"+lname+"%' ",PatientEntity.class);
		query.setMaxResults(limit);
		
		return query.getResultList();
	}

	
	@Transactional
	public int savePatient(String fname, String lname, String guardian, String mobile, String address, int age,
			String sex) {
		PatientEntity patient=new PatientEntity();
		patient.setAddress(address);
		patient.setAge(age);
		patient.setFname(fname);
		patient.setGuardian(guardian);
		patient.setLname(lname);
		patient.setMobile(mobile);
		patient.setSex(sex);
		return (int) sessionFactory.getCurrentSession().save(patient);
	}

	
	@Transactional
	public PatientEntity getPatientById(int id) {
		
		return sessionFactory.getCurrentSession().get(PatientEntity.class, id);
	}

	
	@Transactional
	public List<PatientEntity> getPatientByMobileWithLimit(String mobile, int searchlimit) {
		
		Query query=sessionFactory.getCurrentSession().
				createQuery("from PatientEntity where mobile like '"+mobile+"%' ",PatientEntity.class);
		query.setMaxResults(searchlimit);
		
		return query.getResultList();
	}

	
	@Transactional
	public void saveEditedPatient(int id, String fname, String lname, String guardian, String mobile, String address,
			int age, String sex) {
		
		PatientEntity patient= getPatientById(id);
		patient.setAddress(address);
		patient.setAge(age);
		patient.setFirstDateOfVisit( new Date() );
		patient.setFname(fname);
		patient.setGuardian(guardian);
		patient.setLname(lname);
		patient.setMobile(mobile);
		patient.setSex(sex);
		sessionFactory.getCurrentSession().save(patient);
	}
	
	
}
