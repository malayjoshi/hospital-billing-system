package in.jamuna.hms.dao.hospital.billing;

import in.jamuna.hms.entities.hospital.employees.EmployeeEntity;
import in.jamuna.hms.entities.hospital.patient.PatientEntity;
import in.jamuna.hms.entities.hospital.billing.VisitBillEntity;
import in.jamuna.hms.entities.hospital.billing.VisitTypeEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class VisitBillDAO {
	final
	SessionFactory sessionFactory;

	public VisitBillDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Transactional
	public VisitBillEntity getLastVisitBillByDoctorAndVisitAndFeesAndRefund( 
			EmployeeEntity doctor, VisitTypeEntity visit,PatientEntity patient,int fees) {
		
		Query query= sessionFactory.getCurrentSession().createQuery("from VisitBillEntity where doctor=:doctor "
				+ "and visitType=:visit and patient=:patient and fees>=:fees and refundBill is null  order by tid desc", VisitBillEntity.class);
		
		query.setParameter("doctor", doctor);
		query.setParameter("visit", visit);
		query.setParameter("patient", patient);
		query.setParameter("fees", fees);
		
		query.setMaxResults(1);
		List<VisitBillEntity> bills=query.getResultList();
		
		if(bills.size()==0)
			return null;
		else
			return bills.get(0);
		
	}

	public VisitBillEntity findById(int tid) {
		
		return sessionFactory.getCurrentSession().get(VisitBillEntity.class, tid);
	}

	public void refundBill(int tid, int amount) {
		Session session=sessionFactory.getCurrentSession();
		VisitBillEntity refund=new VisitBillEntity();
		VisitBillEntity bill=findById(tid);
		
		refund.setDoctor( bill.getDoctor() );
		refund.setFees( (-1) * amount );
		refund.setPatient( bill.getPatient() );
		refund.setVisitType(bill.getVisitType());
		int refund_tid=(int)session.save(refund);
		bill.setRefundBill( findById(refund_tid) );
		session.save(bill);
		
	}

	public List<VisitBillEntity> getVisitBillsByDoctorAndVisitAndDate(
			EmployeeEntity employeeEntity, VisitTypeEntity visitTypeEntity, Date date) {
		
		Query query= sessionFactory.getCurrentSession().createQuery(
				"from VisitBillEntity where doctor=:doctor and visitType=:visit and billingDate=:date "
				,VisitBillEntity.class);
		query.setParameter("doctor", employeeEntity);
		query.setParameter("visit", visitTypeEntity);
		query.setParameter("date", date);
		
		return query.getResultList();
		
	}

	public List<VisitBillEntity> findByPatient(PatientEntity patient) {
		Query query=sessionFactory.getCurrentSession().
				createQuery("from VisitBillEntity where patient=:patient ",
						VisitBillEntity.class);
		query.setParameter("patient", patient);
		return query.getResultList();
	}

	public List<VisitBillEntity> getVisitBillsByDoctorAndFeesAndDate(EmployeeEntity doctor, int fees, Date date) {
		
		Query query= sessionFactory.getCurrentSession().createQuery(
				"from VisitBillEntity where doctor=:doctor and fees=:fees and billingDate=:date "
				,VisitBillEntity.class);
		query.setParameter("doctor", doctor);
		query.setParameter("fees", fees);
		query.setParameter("date", date);
		
		return query.getResultList();
		
	}

	public List<VisitBillEntity> getVisitBillsByDoctorAndVisitAndMonthAndYear(EmployeeEntity doctor,
			VisitTypeEntity visit, int month, int year) {
		Query query= sessionFactory.getCurrentSession().createQuery(
				"from VisitBillEntity where doctor=:doctor and visitType=:visit and month(billingDate)=:month and year(billingDate)=:year "
				,VisitBillEntity.class);
		query.setParameter("doctor", doctor);
		query.setParameter("visit", visit);
		query.setParameter("month", month);
		query.setParameter("year", year);
		return query.getResultList();
	}

	public List<VisitBillEntity> getVisitBillsByDoctorAndFeesAndMonthAndYear(EmployeeEntity doctor, int fees, int month,
			int year) {
		
		Query query= sessionFactory.getCurrentSession().createQuery(
				"from VisitBillEntity where doctor=:doctor and fees=:fees and month(billingDate)=:month and year(billingDate)=:year "
				,VisitBillEntity.class);
		query.setParameter("doctor", doctor);
		query.setParameter("fees", fees);
		query.setParameter("month", month);
		query.setParameter("year", year);
		return query.getResultList();
	}


    public List<VisitBillEntity> findByPatientAndStartAndEndDate(PatientEntity patientById, Date startDate, Date endDate) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from VisitBillEntity where patient=:patient and billingDate >= :startDate and billingDate <= :endDate", VisitBillEntity.class
		);
		query.setParameter("patient",patientById);
		query.setParameter("startDate",startDate);
		query.setParameter("endDate",endDate);
		return query.getResultList();
	}
}
