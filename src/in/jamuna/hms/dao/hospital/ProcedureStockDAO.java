package in.jamuna.hms.dao.hospital;

import java.util.logging.Logger;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.ProcedureRatesEntity;
import in.jamuna.hms.entities.hospital.ProcedureStockEntity;

@Repository
@Transactional
public class ProcedureStockDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	private static final Logger LOGGER=Logger.getLogger(ProcedureStockDAO.class.getName());

	public long addStock(ProcedureRatesEntity proc, int qty) {
		ProcedureStockEntity stock=new ProcedureStockEntity();
		stock.setProcedure(proc);
		stock.setQty(qty);
		sessionFactory.getCurrentSession().save(stock);
		return stock.getId();
	}

	public long countOfStockByProcedureAndQty(ProcedureRatesEntity procedure, int qty) {
		long count=0;
		try {
			Query query=sessionFactory.getCurrentSession().createQuery("from ProcedureStockEntity stock where procedure=:procedure and qty>=:qty",
					ProcedureStockEntity.class);
			query.setParameter("procedure", procedure);
			query.setParameter("qty", qty);
			count= query.getResultList().size();
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		return count;
	}

	
	public void getSingleAndDeductByQtyByProcedureAndQty(ProcedureRatesEntity procedure,
			int qty) {
		//get top row
		Query query=sessionFactory.getCurrentSession().createQuery("from ProcedureStockEntity where procedure=:procedure and qty>=:qty",
				ProcedureStockEntity.class);
		query.setParameter("procedure", procedure);
		query.setParameter("qty", qty);
		query.setMaxResults(1);
		ProcedureStockEntity stock=(ProcedureStockEntity) query.getResultList().get(0);
		stock.setQty( stock.getQty()-1 );
	}
	
	
	
}
