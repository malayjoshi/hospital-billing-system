package in.jamuna.hms.dao.hospital;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.ProcedureBillEntity;
import in.jamuna.hms.entities.hospital.TestParametersEntity;
import in.jamuna.hms.entities.hospital.TestsEntity;

@Repository
@Transactional
public class TestsDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

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
	
	
	
}
