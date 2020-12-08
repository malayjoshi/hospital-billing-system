package in.jamuna.hms.dao.hospital;

import java.util.List;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.BillGroupsEntity;
import in.jamuna.hms.entities.hospital.ProcedureRatesEntity;

@Repository
@Transactional
public class ProceduresDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	private static final Logger LOGGER=Logger.getLogger(ProceduresDAO.class.getName());
	
	public void addProcedure(BillGroupsEntity group, String procedure, int rate) {
		
		try {
			
			Session session=sessionFactory.getCurrentSession();
			ProcedureRatesEntity procedureEntity=new ProcedureRatesEntity();
			procedureEntity.setProcedure(procedure);
			procedureEntity.setRate(rate);
			procedureEntity.setBillGroup(group);
			
			session.save(procedureEntity);
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
	}


	public List<ProcedureRatesEntity> getAllProcedures() {
		
		return sessionFactory.getCurrentSession().
		createQuery("from ProcedureRatesEntity ",ProcedureRatesEntity.class).getResultList();
	}


	public void saveProcedureRate(int id, int rate) {
		Session session = sessionFactory.getCurrentSession();
		ProcedureRatesEntity procedure = session.get(ProcedureRatesEntity.class, id);
		procedure.setRate(rate);
		session.save(procedure);
		
	}


	public void enableDisableProcedure(int id, boolean b) {
		Session session = sessionFactory.getCurrentSession();
		ProcedureRatesEntity procedure = session.get(ProcedureRatesEntity.class, id);
		procedure.setEnabled(b);
		session.save(procedure);
		
	}
	
	
}
