package in.jamuna.hms.dao.hospital.billing;

import in.jamuna.hms.entities.hospital.billing.BillGroupsEntity;
import in.jamuna.hms.entities.hospital.billing.ProcedureBillItemEntity;
import in.jamuna.hms.entities.hospital.lab.LabCategoryEntity;
import in.jamuna.hms.entities.hospital.billing.ProcedureBillEntity;
import in.jamuna.hms.entities.hospital.billing.ProcedureRatesEntity;
import in.jamuna.hms.entities.hospital.stock.ProcedureProductMappingEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Repository
@Transactional
public class ProceduresDAO {
	final
	SessionFactory sessionFactory;
	
	private static final Logger LOGGER=Logger.getLogger(ProceduresDAO.class.getName());

	public ProceduresDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addProcedure(BillGroupsEntity group, String procedure, int rate) {
		
		try {
			
			Session session=sessionFactory.getCurrentSession();
			ProcedureRatesEntity procedureEntity=new ProcedureRatesEntity();
			procedureEntity.setProcedure(procedure);
			procedureEntity.setRate(rate);
			procedureEntity.setBillGroup(group);
			
			session.save(procedureEntity);
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
		
	}


	public List<ProcedureRatesEntity> getAllProcedures() {
		
		return sessionFactory.getCurrentSession().
		createQuery("from ProcedureRatesEntity ",ProcedureRatesEntity.class).getResultList();
	}


	public void saveProcedureRate(int id, int rate) {
		Session session = sessionFactory.getCurrentSession();
		ProcedureRatesEntity procedure = session.get(ProcedureRatesEntity.class, id);
		procedure.setRate(rate);
		session.save(procedure);
		
	}


	public void enableDisableProcedure(int id, boolean b) {
		Session session = sessionFactory.getCurrentSession();
		ProcedureRatesEntity procedure = session.get(ProcedureRatesEntity.class, id);
		procedure.setEnabled(b);
		session.save(procedure);
		
	}


	public List<ProcedureRatesEntity> findByNameAndEnabledWithLimit(String term, int searchlimit) {
		Query<ProcedureRatesEntity> query=sessionFactory.getCurrentSession()
				.createQuery("from ProcedureRatesEntity where procedure like '%"+term+"%' and enabled=true ",ProcedureRatesEntity.class);
		
		query.setMaxResults(searchlimit);
		
		return query.getResultList();
	}


	public ProcedureRatesEntity findById(int id) {
		return sessionFactory.getCurrentSession().get(ProcedureRatesEntity.class, id);
	}


	public List<ProcedureRatesEntity> findByBillGroupId(int labGroupId) {
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery(
				"from ProcedureRatesEntity where billGroup.id=:id");
		query.setParameter("id", labGroupId);
		
		return query.getResultList();
	}


	public void setCategory(LabCategoryEntity cat, int testId) {
		ProcedureRatesEntity proc=findById(testId);
		proc.setCategory(cat);
		sessionFactory.getCurrentSession().save(proc);
	}

	public List<ProcedureRatesEntity> findByTidAndBillGroup(int tid, int groupId) {
		try {

			Query query= sessionFactory.getCurrentSession().
					createQuery("select item.procedure from ProcedureBillItemEntity item where "
							+ "item.bill.tid=:tid and item.procedure.billGroup.id=:groupId",
							ProcedureRatesEntity.class);
			query.setParameter("tid", tid);
			query.setParameter("groupId", groupId);
			LOGGER.info(query.getResultList().size()+",113");
			return query.getResultList();
		
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}	
		
		return new ArrayList<ProcedureRatesEntity>();
	}


	public List<ProcedureRatesEntity> findProceduresFromTestValuesByBill(ProcedureBillEntity bill) {
		try {

			Query query= sessionFactory.getCurrentSession().
					createQuery("select distinct(val.parameter.test) from TestsEntity val where "
							+ "val.bill=:bill",
							ProcedureRatesEntity.class);
			query.setParameter("bill", bill);
			return query.getResultList();
		
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}	
		
		return null;
	}


    public void enableDisableStockTrack(int id, boolean equals) {
		Session session = sessionFactory.getCurrentSession();
		ProcedureRatesEntity procedure = session.get(ProcedureRatesEntity.class, id);
		procedure.setStockEnabled(equals);
		session.save(procedure);
    }


    public ProcedureRatesEntity getByBillItem(ProcedureBillItemEntity item) {
		javax.persistence.Query query = sessionFactory.getCurrentSession().createQuery(
				"select item.procedure from ProcedureBillItemEntity item where item.id=:id", ProcedureRatesEntity.class
		);
		query.setParameter("id",item.getId());
		return (ProcedureRatesEntity) query.getSingleResult();
    }
}
