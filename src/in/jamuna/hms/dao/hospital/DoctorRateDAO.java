package in.jamuna.hms.dao.hospital;

import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.DoctorRateEntity;
import in.jamuna.hms.entities.hospital.EmployeeEntity;
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
	
	
}
