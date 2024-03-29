package in.jamuna.hms.dao.hospital.lab;

import in.jamuna.hms.entities.hospital.billing.ProcedureBillEntity;
import in.jamuna.hms.entities.hospital.billing.ProcedureRatesEntity;
import in.jamuna.hms.entities.hospital.lab.TestParametersEntity;
import in.jamuna.hms.entities.hospital.lab.TestsEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TestsDAO {
	
	private final SessionFactory sessionFactory;

	public TestsDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void saveValue(ProcedureBillEntity bill, TestParametersEntity para, String value) {
		TestsEntity test=new TestsEntity();
		test.setBill(bill);
		test.setParameter(para);
		test.setValue(value);
		sessionFactory.getCurrentSession().save(test);
	}

	public List<TestsEntity> findByBill(ProcedureBillEntity bill) {
		Query query=sessionFactory.getCurrentSession().createQuery(
				"from TestsEntity as test where test.bill=:bill");
		query.setParameter("bill", bill);
		
		return query.getResultList();
	}

	public void changeValueById(int id, String value) {
		Session session=sessionFactory.getCurrentSession();
		TestsEntity test=session.get(TestsEntity.class, id);
		test.setValue(value);
		session.save(test);
	}

	public List<TestsEntity> findByTestAndTid(ProcedureRatesEntity test,int tid) {
		
		Query query=sessionFactory.getCurrentSession().createQuery(
				"from TestsEntity as test where test.parameter.test=:test and test.bill.tid=:tid");
		query.setParameter("test", test);
		query.setParameter("tid", tid);
		return query.getResultList();
		
	}
	
	
	
}
