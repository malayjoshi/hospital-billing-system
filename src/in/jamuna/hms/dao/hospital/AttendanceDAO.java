package in.jamuna.hms.dao.hospital;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import in.jamuna.hms.entities.hospital.AttendanceEntity;
import in.jamuna.hms.entities.hospital.EmployeesTotalEntity;
import in.jamuna.hms.entities.hospital.PresetValuesEntity;


@Repository
@Transactional
public class AttendanceDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public List<AttendanceEntity> getAttendanceByDate(Date date) {
		Query query= sessionFactory.getCurrentSession().
				createQuery("from AttendanceEntity where date=:date",
						AttendanceEntity.class);
		query.setParameter("date", date);
		
		return query.getResultList();
		
		
	}

	public void saveAttendance(EmployeesTotalEntity emp, PresetValuesEntity day, PresetValuesEntity night, Date date) {
		AttendanceEntity att=new AttendanceEntity();
		att.setDate(date);
		att.setDay(day);
		att.setNight(night);
		att.setEmployee(emp);
		sessionFactory.getCurrentSession().save(att);
		
	}


	public void updateAttendanceById(int id, PresetValuesEntity day, PresetValuesEntity night) {
		AttendanceEntity att = sessionFactory.getCurrentSession().get(AttendanceEntity.class, id);
		att.setDay(day);
		att.setNight(night);
	}

	public long getCountOfDayAttendanceByPresetAndMonthYearAndEmployee(PresetValuesEntity preset, int month, int year,EmployeesTotalEntity emp) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(att.id) from AttendanceEntity as att where year(att.date)=:year and month(att.date)=:month and att.day=:preset and att.employee=:emp",
				Long.class
				);
		query.setParameter("year", year);
		query.setParameter("month", month);
		query.setParameter("preset", preset);
		query.setParameter("emp", emp);
		return (Long)query.getSingleResult();
	}

	public long getCountOfNightAttendanceByPresetAndMonthYearAndEmployee(PresetValuesEntity preset, int month, int year,EmployeesTotalEntity emp) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(att.id) from AttendanceEntity as att where year(att.date)=:year and month(att.date)=:month and att.night=:preset and att.employee=:emp",
				Long.class
				);
		query.setParameter("year", year);
		query.setParameter("month", month);
		query.setParameter("preset", preset);
		query.setParameter("emp", emp);
		return (Long)query.getSingleResult();
	}
	
	
}
