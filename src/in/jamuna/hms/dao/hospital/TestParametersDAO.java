package in.jamuna.hms.dao.hospital;

import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.ProcedureRatesEntity;
import in.jamuna.hms.entities.hospital.TestParametersEntity;

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

	
	
}
