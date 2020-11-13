package in.jamuna.hms.dao.hospital;

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
	public List<PatientEntity> getPatientByNameOrGuardianOrMobile(String fname, String lname, String guardian,
			String mobile) {
		Query query=sessionFactory.getCurrentSession().createQuery("from PatientEntity where (fname like '%:fname%' and lname like '%:lname%') "
				+ "or guardian like '%:guardian%' or ':mobile%' ",PatientEntity.class);
		
		query.setParameter("fname", fname);
		query.setParameter("lname", lname);
		query.setParameter("guardian", guardian);
		query.setParameter("mobile",mobile);
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
	
	
}
