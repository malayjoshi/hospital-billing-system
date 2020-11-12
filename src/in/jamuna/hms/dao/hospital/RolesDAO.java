package in.jamuna.hms.dao.hospital;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.EmployeeEntity;
import in.jamuna.hms.entities.hospital.RolesEntity;

@Repository
public class RolesDAO {
	@Autowired
	private	SessionFactory sessionFactory;
	
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
