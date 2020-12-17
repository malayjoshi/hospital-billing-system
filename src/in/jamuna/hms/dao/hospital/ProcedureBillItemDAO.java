package in.jamuna.hms.dao.hospital;

import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import in.jamuna.hms.entities.hospital.ProcedureBillEntity;
import in.jamuna.hms.entities.hospital.ProcedureBillItemEntity;
import in.jamuna.hms.entities.hospital.ProcedureRatesEntity;

@Repository
@Transactional
public class ProcedureBillItemDAO {
	@Autowired
	SessionFactory sessionFactory;

	public void saveItem(ProcedureBillEntity bill, 
			ProcedureRatesEntity procedure, Integer rate) {
		ProcedureBillItemEntity item=new ProcedureBillItemEntity();
		item.setBill(bill);
		item.setProcedure(procedure);
		item.setRate(rate);
		sessionFactory.getCurrentSession().save(item);
		
	}

	
	
	
}
