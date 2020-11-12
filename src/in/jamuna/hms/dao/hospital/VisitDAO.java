package in.jamuna.hms.dao.hospital;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.VisitTypeEntity;

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
}
