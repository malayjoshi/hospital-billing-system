package in.jamuna.hms.dao.hospital;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
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
	
	@Transactional
	public List<DoctorRateEntity> getAllDoctorRates() {
		
		return sessionFactory.getCurrentSession().createQuery("from DoctorRateEntity")
				.getResultList();
	}
	
	@Transactional
	public void deleteDoctorRate(int rateId) {
		Session session=sessionFactory.getCurrentSession();
		session.delete(session.get(DoctorRateEntity.class,rateId));
	}

	@Transactional
	public DoctorRateEntity getRateByDoctorAndVisitAndTime(EmployeeEntity doctor, VisitTypeEntity visit, long time) {
		
		Query query= sessionFactory.getCurrentSession().createQuery("from DoctorRateEntity where doctor=:doctor "
				+ "and visitType=:visit and startTime<=:time and endTime>=:time ",DoctorRateEntity.class);
		query.setParameter("time", new Date(time));
		query.setParameter("doctor", doctor);
		query.setParameter("visit", visit);
		
		List<DoctorRateEntity> rates=query.getResultList();
		if(rates.isEmpty())
			return null;
		else
			return rates.get(0);
		
	}
	
	
}
