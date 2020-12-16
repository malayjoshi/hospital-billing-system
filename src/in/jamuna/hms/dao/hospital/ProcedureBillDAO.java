package in.jamuna.hms.dao.hospital;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.EmployeeEntity;
import in.jamuna.hms.entities.hospital.PatientEntity;
import in.jamuna.hms.entities.hospital.ProcedureBillEntity;

@Repository
@Transactional
public class ProcedureBillDAO {
	@Autowired
	SessionFactory sessionFactory;

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
	
	
}
