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
import in.jamuna.hms.entities.hospital.VisitBillEntity;
import in.jamuna.hms.entities.hospital.VisitTypeEntity;

@Repository
@Transactional
public class VisitBillDAO {
	@Autowired
	SessionFactory sessionFactory;

	
	
	@Transactional
	public VisitBillEntity getLastVisitBillByDoctorAndVisitAndFeesAndRefund( 
			EmployeeEntity doctor, VisitTypeEntity visit,PatientEntity patient,int fees, VisitBillEntity refund) {
		
		Query query= sessionFactory.getCurrentSession().createQuery("from VisitBillEntity where doctor=:doctor "
				+ "and visitType=:visit and patient=:patient and fees>=:fees and refundBill=:refund  order by tid desc", VisitBillEntity.class);
		
		query.setParameter("doctor", doctor);
		query.setParameter("visit", visit);
		query.setParameter("patient", patient);
		query.setParameter("fees", fees);
		query.setParameter("refund", refund);
		
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
	
	
	
}
