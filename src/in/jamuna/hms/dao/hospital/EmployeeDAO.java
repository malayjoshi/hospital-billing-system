package in.jamuna.hms.dao.hospital;

import java.util.List;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.EmployeeEntity;
import in.jamuna.hms.entities.hospital.RolesEntity;


@Repository
public class EmployeeDAO  {
	@Autowired
	private	SessionFactory sessionFactory;
	
	private static final Logger LOGGER=Logger.getLogger(EmployeeDAO.class.getName());
	
	
	@Transactional
	public List<EmployeeEntity> findByMobileAndRoleAndPasswordOptional(String mobile,RolesEntity role,boolean passwordProvide,String password) {
		
		String hql="from EmployeeEntity where mobile=:mobile and role=:role ";
		
		if(passwordProvide)
			hql+="and password=:password";
		
		Query query=sessionFactory.getCurrentSession()
				.createQuery(hql,EmployeeEntity.class);
		
		query.setParameter("mobile", mobile);
		query.setParameter("role", role);
		if(passwordProvide)
			query.setParameter("password", password);
		
		//LOGGER.info("size:"+query.getResultList().size());
		
		return query.getResultList();
	}
	
	

	@Transactional
	public void addEmployee(EmployeeEntity employee) {
		
		sessionFactory.getCurrentSession().save(employee);
		
	}


	@Transactional
	public List<EmployeeEntity> getAllEmployees() {
		
		return sessionFactory.getCurrentSession().createQuery("from EmployeeEntity",EmployeeEntity.class).getResultList();
	}


	@Transactional
	public List<EmployeeEntity> getEmployeesByPage(Integer pageNum, int perpage) {
		Query query=sessionFactory.getCurrentSession().createQuery("from EmployeeEntity",EmployeeEntity.class);
		query.setFirstResult((pageNum - 1) * perpage);
	    query.setMaxResults(perpage);
	    
		return query.getResultList();
	}


	@Transactional
	public void deleteEmployee(int id) {
		Session session=sessionFactory.getCurrentSession();
		EmployeeEntity employee=session.get(EmployeeEntity.class, id);
		employee.getRole().getEmployees().remove(employee);
		session.delete(employee);
	}


	@Transactional
	public EmployeeEntity findById(int empId) {
		
		return sessionFactory.getCurrentSession().get(EmployeeEntity.class, empId);
	}
	
	
	

}