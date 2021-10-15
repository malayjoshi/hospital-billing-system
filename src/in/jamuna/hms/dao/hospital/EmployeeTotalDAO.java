package in.jamuna.hms.dao.hospital;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.jamuna.hms.entities.hospital.EmployeesTotalEntity;

@Repository
@Transactional
public class EmployeeTotalDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public List<EmployeesTotalEntity> getEmployees() {
		
		return sessionFactory.getCurrentSession().
				createQuery("from EmployeesTotalEntity",EmployeesTotalEntity.class).getResultList();
	}

	public void addEmployee(String name, String mobile) {
		EmployeesTotalEntity emp=new EmployeesTotalEntity();
		emp.setName(name);
		emp.setMobile(mobile);
		emp.setEnabled(true);
		sessionFactory.getCurrentSession().save(emp);
		
	}

	public EmployeesTotalEntity findById(int id) {
		
		Session session=sessionFactory.getCurrentSession();
		return session.get(EmployeesTotalEntity.class, id);
	}

	public void save(EmployeesTotalEntity emp) {
		sessionFactory.getCurrentSession().save(emp);
	}

	public void setEnabled(boolean b, int id) {
		findById(id).setEnabled(b);
		
	}
	
	
}
