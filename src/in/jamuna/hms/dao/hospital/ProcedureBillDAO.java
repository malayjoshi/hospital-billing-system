package in.jamuna.hms.dao.hospital;

import javax.transaction.Transactional;

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

	public int saveBill(PatientEntity patient, EmployeeEntity doctor, int total) {
		ProcedureBillEntity bill=new ProcedureBillEntity();
		bill.setDoctor(doctor);
		bill.setPatient(patient);
		bill.setTotal(total);
		return (int) sessionFactory.getCurrentSession().save(bill);
	}

	public ProcedureBillEntity findByTid(int tid) {
		return sessionFactory.getCurrentSession().get(ProcedureBillEntity.class, tid);
	}
	
	
}
