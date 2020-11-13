package in.jamuna.hms.dao.hospital;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.DoctorRateEntity;
import in.jamuna.hms.entities.hospital.EmployeeEntity;
import in.jamuna.hms.entities.hospital.RolesEntity;
import in.jamuna.hms.entities.hospital.VisitTypeEntity;

@Repository
public class DoctorRateDAO {
	
	@Autowired
	private	SessionFactory sessionFactory;

	@Transactional
	public void saveDoctorRate(VisitTypeEntity visit, EmployeeEntity doctor, Date startTime,
			Date endTime, int rateDoctor) {
		
		DoctorRateEntity rate=new DoctorRateEntity();
		rate.setDoctor(doctor);
		rate.setVisitType(visit);
		rate.setEndTime(endTime);
		rate.setStartTime(startTime);
		rate.setRate(rateDoctor);
		sessionFactory.getCurrentSession().save(rate);
		
	}
	
	@Transactional
	public List<DoctorRateEntity> getAllDoctorRatesGroupByDoctorAndVisitAndTime() {
		
		return sessionFactory.getCurrentSession().createQuery("from DoctorRateEntity group by doctor,visitType,startTime")
				.getResultList();
	}
	
	@Transactional
	public void deleteDoctorRate(int rateId) {
		Session session=sessionFactory.getCurrentSession();
		session.delete(session.get(DoctorRateEntity.class,rateId));
	}
	
	
}
