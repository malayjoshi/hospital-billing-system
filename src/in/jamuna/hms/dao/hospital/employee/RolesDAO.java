package in.jamuna.hms.dao.hospital.employee;

import in.jamuna.hms.entities.hospital.employees.EmployeeEntity;
import in.jamuna.hms.entities.hospital.employees.RolesEntity;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
public class RolesDAO {
	private final SessionFactory sessionFactory;

	public RolesDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public List<RolesEntity> getAllRoles() {
		
		return sessionFactory.getCurrentSession().
				createQuery("from RolesEntity",RolesEntity.class).getResultList();
	}
	
	@Transactional
	public RolesEntity findByRoleId(int roleId) {
		return sessionFactory.getCurrentSession().
				createQuery("from RolesEntity where roleId="+roleId,RolesEntity.class).getSingleResult();
	
	}
	
	@Transactional
	public  Set<EmployeeEntity> findByRole(String role) {
		return sessionFactory.getCurrentSession().
				createQuery("from RolesEntity where role='"+role+"'",RolesEntity.class).
				getSingleResult().getEmployees();
	}
	
	
	
	
}
