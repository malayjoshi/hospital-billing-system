package in.jamuna.hms.dao.hospital;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import java.util.logging.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.dto.login.CredentialsDto;
import in.jamuna.hms.dto.login.SessionDto;
import in.jamuna.hms.entities.hospital.EmployeeEntity;
import in.jamuna.hms.entities.hospital.RolesEntity;



@Repository
public class EmployeeDAO  {
	@Autowired
	private	SessionFactory sessionFactory;
	
	private static final Logger logger=Logger.getLogger(EmployeeDAO.class.getName());
	
	@Transactional
	public List<EmployeeEntity> findByMobileAndPasswordAndRole(String mobile, String password,RolesEntity role) {
		
		Query query=sessionFactory.getCurrentSession()
				.createQuery("from EmployeeEntity as emp where emp.mobile=:mobile and"
						+ " emp.password=:password and emp.enabled=true and emp.role=:role ",EmployeeEntity.class);
		query.setParameter("mobile", mobile);
		query.setParameter("password", password);
		query.setParameter("role", role);
		
		return query.getResultList();
	}

	

}