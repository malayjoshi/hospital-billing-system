package in.jamuna.hms.dao.hospital.billing;

import in.jamuna.hms.entities.hospital.billing.BillGroupsEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class BillGroupsDAO {
	private final SessionFactory sessionFactory;

	public BillGroupsDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<BillGroupsEntity> findAll(){
		return sessionFactory.getCurrentSession().
				createQuery("from BillGroupsEntity",BillGroupsEntity.class).getResultList();
	}

	
	public void addBillGroup(String name) {
		BillGroupsEntity group=new BillGroupsEntity();
		group.setName(name);
		sessionFactory.getCurrentSession().save(group);
	}

	
	public void setEnabledById(int id, boolean b) {
		Session session=sessionFactory.getCurrentSession();
		BillGroupsEntity group=session.get(BillGroupsEntity.class,id);
		group.setEnabled(b);
	}

	public BillGroupsEntity findById(int groupId) {
		
		return sessionFactory.getCurrentSession().get(BillGroupsEntity.class,groupId);
	}
}
