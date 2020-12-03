package in.jamuna.hms.dao.hospital;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.BillGroupsEntity;

@Repository
public class BillGroupsDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public List<BillGroupsEntity> findAll(){
		return sessionFactory.getCurrentSession().
				createQuery("from BillGroupsEntity",BillGroupsEntity.class).getResultList();
	}

	@Transactional
	public void addBillGroup(String name) {
		BillGroupsEntity group=new BillGroupsEntity();
		group.setName(name);
		sessionFactory.getCurrentSession().save(group);
	}

	@Transactional
	public void setEnabledById(int id, boolean b) {
		Session session=sessionFactory.getCurrentSession();
		BillGroupsEntity group=session.get(BillGroupsEntity.class,id);
		group.setEnabled(b);
	}
}
