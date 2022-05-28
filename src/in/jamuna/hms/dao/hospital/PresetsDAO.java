package in.jamuna.hms.dao.hospital;

import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import in.jamuna.hms.entities.hospital.PresetValuesEntity;

@Repository
@Transactional
public class PresetsDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public List<PresetValuesEntity> getPresets() {
		return sessionFactory.getCurrentSession().
				createQuery("from PresetValuesEntity",PresetValuesEntity.class).getResultList();
	}

	public PresetValuesEntity findById(int id) {
		
		return sessionFactory.getCurrentSession().get(PresetValuesEntity.class, id);
	}
	
	
}
