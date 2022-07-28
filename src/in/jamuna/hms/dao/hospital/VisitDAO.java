package in.jamuna.hms.dao.hospital;

import in.jamuna.hms.entities.hospital.EmployeeEntity;
import in.jamuna.hms.entities.hospital.PatientEntity;
import in.jamuna.hms.entities.hospital.VisitBillEntity;
import in.jamuna.hms.entities.hospital.VisitTypeEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class VisitDAO {
	@Autowired
	private	SessionFactory sessionFactory;
	
	@Transactional
	public  List<VisitTypeEntity> getAllVisitTypes() {
		return sessionFactory.getCurrentSession().
		createQuery("from VisitTypeEntity",VisitTypeEntity.class).getResultList();
	}

	@Transactional
	public VisitTypeEntity findById(int visitId) {
		
		return sessionFactory.getCurrentSession().get(VisitTypeEntity.class, visitId);
	}

	@Transactional
	public int saveVisit(PatientEntity patient, EmployeeEntity doctor, VisitTypeEntity visitType, int rate) {
		VisitBillEntity bill=new VisitBillEntity();
		bill.setDoctor(doctor);
		bill.setFees(rate);
		bill.setPatient(patient);
		bill.setVisitType(visitType);
		return (int)sessionFactory.getCurrentSession().save(bill);
	}
}
