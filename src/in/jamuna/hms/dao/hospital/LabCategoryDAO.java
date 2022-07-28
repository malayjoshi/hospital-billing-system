package in.jamuna.hms.dao.hospital;

import in.jamuna.hms.entities.hospital.LabCategoryEntity;
import in.jamuna.hms.entities.hospital.ProcedureBillEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
@Transactional
public class LabCategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger LOGGER=Logger.getLogger(LabCategoryDAO.class.getName());

	
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
		LabCategoryEntity cat=sessionFactory.getCurrentSession().get(LabCategoryEntity.class, categoryId);
		
		return cat;
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
		return new ArrayList<LabCategoryEntity>();
	}
	
	
	
}
