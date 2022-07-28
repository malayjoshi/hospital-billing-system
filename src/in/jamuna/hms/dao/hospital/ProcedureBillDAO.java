package in.jamuna.hms.dao.hospital;

import in.jamuna.hms.entities.hospital.EmployeeEntity;
import in.jamuna.hms.entities.hospital.PatientEntity;
import in.jamuna.hms.entities.hospital.ProcedureBillEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class ProcedureBillDAO {
	final
	SessionFactory sessionFactory;

	public ProcedureBillDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public ProcedureBillEntity saveBill(PatientEntity patient, EmployeeEntity doctor, int total) {
		ProcedureBillEntity bill=new ProcedureBillEntity();
		bill.setDoctor(doctor);
		bill.setPatient(patient);
		bill.setTotal(total);
		sessionFactory.getCurrentSession().save(bill);
		return  bill;
	}

	public ProcedureBillEntity findByTid(int tid) {
		return sessionFactory.getCurrentSession().get(ProcedureBillEntity.class, tid);
	}

	public void refundBill(int tid, int amount) {
		Session session=sessionFactory.getCurrentSession();
		ProcedureBillEntity refund=new ProcedureBillEntity();
		ProcedureBillEntity bill=findByTid(tid);
		
		refund.setDoctor( bill.getDoctor() );
		refund.setTotal( (-1) * amount );
		refund.setPatient( bill.getPatient() );
		
		int refund_tid=(int)session.save(refund);
		bill.setRefundBill( findByTid(refund_tid) );
		session.save(bill);
	}

	public List<ProcedureBillEntity> findByDoctorAndDate(EmployeeEntity doctor, Date date) {
		Query query= sessionFactory.getCurrentSession().
				createQuery("from ProcedureBillEntity where doctor=:doctor and date=:date",
						ProcedureBillEntity.class);
		query.setParameter("doctor", doctor);
		query.setParameter("date", date);
		
		return query.getResultList();
	}

	
	public List<ProcedureBillEntity> findByFromDate(Date date) {
		Query query=sessionFactory.getCurrentSession().
				createQuery("from ProcedureBillEntity where date > :date ",
						ProcedureBillEntity.class);
		query.setParameter("date", date);
		return query.getResultList();
	}

	public List<ProcedureBillEntity> findByPatient(PatientEntity patient) {
		Query query=sessionFactory.getCurrentSession().
				createQuery("from ProcedureBillEntity where patient=:patient ",
						ProcedureBillEntity.class);
		query.setParameter("patient", patient);
		return query.getResultList();
		
	}


    public List<ProcedureBillEntity> findByPatientAndStartAndEndDate(PatientEntity patientById, Date startDate, Date endDate) {
    	Query query = sessionFactory.getCurrentSession().createQuery(
				"from ProcedureBillEntity where patient=:patient and date >= :startDate and date <= :endDate",ProcedureBillEntity.class
		);
		query.setParameter("patient",patientById);
		query.setParameter("startDate",startDate);
		query.setParameter("endDate",endDate);
		return query.getResultList();
	}
}
