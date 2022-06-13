package in.jamuna.hms.dao.hospital;

import java.util.Collection;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.dto.common.CommonIdAndNameDto;
import in.jamuna.hms.entities.hospital.ProcedureBillEntity;
import in.jamuna.hms.entities.hospital.ProcedureRatesEntity;
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
