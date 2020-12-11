package in.jamuna.hms.dao.hospital;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.PatientEntity;
import in.jamuna.hms.entities.hospital.ProcedureRatesEntity;
import in.jamuna.hms.entities.hospital.ProceduresCartEntity;

@Repository
@Transactional
public class ProceduresCartDAO {

	@Autowired
	SessionFactory sessionFactory;

	public void saveItem(PatientEntity patientById, ProcedureRatesEntity findById) {
		ProceduresCartEntity item=new ProceduresCartEntity();
		item.setPatient(patientById);
		item.setProcedure(findById);
		sessionFactory.getCurrentSession().save(item);
	}

	public List<ProceduresCartEntity> findByPatient(PatientEntity patient) {
		
		Query query= sessionFactory.getCurrentSession().
				createQuery("from ProceduresCartEntity where patient=:patient",ProceduresCartEntity.class);
				query.setParameter("patient", patient);
		return query.getResultList();
	}

	public void deleteItem(int id) {
		Session session=sessionFactory.getCurrentSession();
		ProceduresCartEntity item=session.get(ProceduresCartEntity.class, id);
		session.delete(item);
		
	}
	
	
	
}
