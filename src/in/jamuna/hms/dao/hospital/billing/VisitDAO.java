package in.jamuna.hms.dao.hospital.billing;

import in.jamuna.hms.entities.hospital.employees.EmployeeEntity;
import in.jamuna.hms.entities.hospital.patient.PatientEntity;
import in.jamuna.hms.entities.hospital.billing.VisitBillEntity;
import in.jamuna.hms.entities.hospital.billing.VisitTypeEntity;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class VisitDAO {
	private final SessionFactory sessionFactory;

	public VisitDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

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
