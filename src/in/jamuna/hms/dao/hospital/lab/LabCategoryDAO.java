package in.jamuna.hms.dao.hospital.lab;

import in.jamuna.hms.entities.hospital.lab.LabCategoryEntity;
import in.jamuna.hms.entities.hospital.billing.ProcedureBillEntity;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
@Transactional
public class LabCategoryDAO {

	private final SessionFactory sessionFactory;

	private static final Logger LOGGER=Logger.getLogger(LabCategoryDAO.class.getName());

	public LabCategoryDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	public List<LabCategoryEntity> getLabCategories() {
		Query query=sessionFactory.getCurrentSession().createQuery("from LabCategoryEntity",LabCategoryEntity.class);
		return query.getResultList();
	}

	public void saveCategory(String category) {
		LabCategoryEntity cat=new LabCategoryEntity();
		cat.setName(category);
		sessionFactory.getCurrentSession().save(cat);
	}

	public LabCategoryEntity findById(int categoryId) {

		return sessionFactory.getCurrentSession().get(LabCategoryEntity.class, categoryId);
	}

	public List<LabCategoryEntity> findCategoriesOfCompletedTestsByBill(ProcedureBillEntity bill) {
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(
					"select distinct(val.parameter.test.category) from TestsEntity val where val.bill=:bill and val.value !='' ");
			query.setParameter("bill", bill);
			return query.getResultList();
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		return new ArrayList<>();
	}
	
	
	
}
