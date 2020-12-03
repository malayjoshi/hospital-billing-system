package in.jamuna.hms.dao.hospital;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.EmployeeEntity;
import in.jamuna.hms.entities.hospital.PatientEntity;
import in.jamuna.hms.entities.hospital.VisitBillEntity;
import in.jamuna.hms.entities.hospital.VisitTypeEntity;

@Repository
public class VisitBillDAO {
	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public List<VisitBillEntity> getVisitBillsByDoctorAndVisit(EmployeeEntity doctor, VisitTypeEntity visit) {
		
		Query query= sessionFactory.getCurrentSession().createQuery("from VisitBillEntity where doctor=:doctor and visit=:visit "
				,VisitBillEntity.class);
		query.setParameter("doctor", doctor);
		query.setParameter("visit", visit);
		return query.getResultList();
	}
	
	@Transactional
	public VisitBillEntity getLastVisitBillByDoctorAndVisit( EmployeeEntity doctor, VisitTypeEntity visit,PatientEntity patient) {
		Query query= sessionFactory.getCurrentSession().createQuery("from VisitBillEntity where doctor=:doctor "
				+ "and visitType=:visit and patient=:patient order by tid desc", VisitBillEntity.class);
		query.setParameter("doctor", doctor);
		query.setParameter("visit", visit);
		query.setParameter("patient", patient);
		query.setMaxResults(1);
		List<VisitBillEntity> bills=query.getResultList();
		
		if(bills.size()==0)
			return null;
		else
			return bills.get(0);
		
	}
	
	
}
