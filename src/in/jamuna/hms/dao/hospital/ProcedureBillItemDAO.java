package in.jamuna.hms.dao.hospital;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.BillGroupsEntity;
import in.jamuna.hms.entities.hospital.EmployeeEntity;
import in.jamuna.hms.entities.hospital.ProcedureBillEntity;
import in.jamuna.hms.entities.hospital.ProcedureBillItemEntity;
import in.jamuna.hms.entities.hospital.ProcedureRatesEntity;

@Repository
@Transactional
public class ProcedureBillItemDAO {
	@Autowired
	SessionFactory sessionFactory;

	private static final Logger LOGGER=Logger.getLogger(ProcedureBillItemDAO.class.getName());

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

	
	

	public List<ProcedureBillItemEntity> getItemsByProcedureAndDateAndTypeDoctor(ProcedureRatesEntity proc, Date date,
			String type, EmployeeEntity doctor) {
		
		Query query = null;
		
		if(type.equals("Daily")) {
			query= sessionFactory.getCurrentSession().
					createQuery("from ProcedureBillItemEntity where bill.date=:date AND procedure=:procedure and bill.doctor=:doctor",
							ProcedureBillItemEntity.class);	
			query.setParameter("date", date);
		}
		else if(type.equals("Monthly")) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int month=calendar.get(Calendar.MONTH)+1;
			int year=calendar.get(Calendar.YEAR);
			query= sessionFactory.getCurrentSession().
					createQuery("from ProcedureBillItemEntity where year(bill.date)=:year and month(bill.date)=:month AND procedure=:procedure and bill.doctor=:doctor",
							ProcedureBillItemEntity.class);
			query.setParameter("year", year);
			query.setParameter("month", month);
			
		}
		
		
		query.setParameter("procedure", proc);
		query.setParameter("doctor", doctor);
		return query.getResultList();
	}

	public List<ProcedureBillItemEntity> findItemsByGroupAndDoctorAndDate(BillGroupsEntity group, EmployeeEntity doc, Date date) {
		Query query= sessionFactory.getCurrentSession().
				createQuery("from ProcedureBillItemEntity where bill.date=:date and procedure.billGroup=:group and bill.doctor=:doctor order by bill.tid",
						ProcedureBillItemEntity.class);
		query.setParameter("date", date);
		query.setParameter("group", group);
		query.setParameter("doctor", doc);
		
		return query.getResultList();
	}


	
	
	
}
