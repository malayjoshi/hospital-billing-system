package in.jamuna.hms.dao.hospital;

import in.jamuna.hms.entities.hospital.ProcedureRatesEntity;
import in.jamuna.hms.entities.hospital.TestParametersEntity;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class TestParametersDAO {

	final
	SessionFactory sessionFactory;

	public TestParametersDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void saveTest(ProcedureRatesEntity test, String parameter, String unit, Float lower, Float high,char refNeeded) {
		TestParametersEntity para=new TestParametersEntity();
		para.setTest(test);
		para.setRefNeeded(refNeeded);
		para.setName(parameter);
		
		if(!unit.equals(""))
		{
			para.setUnit(unit);
			para.setLowerRange(lower);
			para.setUpperRange(high);
			
			
		}
		sessionFactory.getCurrentSession().save(para);
		
	}

	public TestParametersEntity findById(int paraId) {
		return sessionFactory.getCurrentSession().get(TestParametersEntity.class, paraId);
	}

	public void saveParameter(TestParametersEntity para) {
		sessionFactory.getCurrentSession().save(para);
		
	}

	
	
}
