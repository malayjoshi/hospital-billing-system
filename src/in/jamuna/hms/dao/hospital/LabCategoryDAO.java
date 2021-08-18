package in.jamuna.hms.dao.hospital;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.LabCategoryEntity;

@Repository
@Transactional
public class LabCategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public List<LabCategoryEntity> getLabCategories() {
		Query query=sessionFactory.getCurrentSession().createQuery("from LabCategoryEntity",LabCategoryEntity.class);
		return query.getResultList();
	}

	public void saveCategory(String category) {
		LabCategoryEntity cat=new LabCategoryEntity();
		cat.setName(category);
		sessionFactory.getCurrentSession().save(cat);
	}
	
	
	
}
