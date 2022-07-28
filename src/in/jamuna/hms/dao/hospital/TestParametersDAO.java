package in.jamuna.hms.dao.hospital;

import in.jamuna.hms.entities.hospital.ProcedureRatesEntity;
import in.jamuna.hms.entities.hospital.TestParametersEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.logging.Logger;

@Repository
@Transactional
public class TestParametersDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	private static final Logger LOGGER=Logger.getLogger(TestParametersDAO.class.getName());

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
		TestParametersEntity para=sessionFactory.getCurrentSession().get(TestParametersEntity.class, paraId);
		return para;
	}

	public void saveParameter(TestParametersEntity para) {
		sessionFactory.getCurrentSession().save(para);
		
	}

	
	
}
