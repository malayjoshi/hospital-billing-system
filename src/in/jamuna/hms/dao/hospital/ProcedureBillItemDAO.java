package in.jamuna.hms.dao.hospital;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.EmployeeEntity;
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

	public List<ProcedureBillItemEntity> getItemsByProcedureAndDoctorAndDate(ProcedureRatesEntity procedureRatesEntity, EmployeeEntity doctor,Date date) {
		Query query= sessionFactory.getCurrentSession().
				createQuery("from ProcedureBillItemEntity where bill.doctor=:doctor and bill.date=:date AND procedure=:procedure",
						ProcedureBillItemEntity.class);
		
		query.setParameter("doctor", doctor);
		query.setParameter("date", date);
		query.setParameter("procedure", procedureRatesEntity);
		
		return query.getResultList();
	}

	
	public List<ProcedureBillItemEntity> getItemsByProcedureAndDate(ProcedureRatesEntity proc, Date date) {
		Query query= sessionFactory.getCurrentSession().
				createQuery("from ProcedureBillItemEntity where bill.date=:date AND procedure=:procedure",
						ProcedureBillItemEntity.class);
		
		query.setParameter("date", date);
		query.setParameter("procedure", proc);
		
		return query.getResultList();
	}

	
	
	
}
