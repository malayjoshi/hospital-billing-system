package in.jamuna.hms.dao.hospital;

import in.jamuna.hms.dto.common.CommonIdAndNameDto;
import in.jamuna.hms.entities.hospital.EmployeeEntity;
import in.jamuna.hms.entities.hospital.ProcedureRatesEntity;
import in.jamuna.hms.entities.hospital.TestCompanyEntity;
import in.jamuna.hms.entities.hospital.TestSupplierEntity;
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
public class TestSupplierDAO {
    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger LOGGER=Logger.getLogger(TestSupplierDAO.class.getName());


    public long getCount() {

        return sessionFactory.getCurrentSession().
                createQuery(" from TestSupplierEntity ",TestSupplierEntity.class).getResultList().size();
    }

    public List<TestSupplierEntity> getByPage(Integer no, int perpage) {
        try {
            Query query=sessionFactory.getCurrentSession().createQuery("from TestSupplierEntity", TestSupplierEntity.class);
            query.setFirstResult((no - 1) * perpage);
            query.setMaxResults(perpage);
            return query.getResultList();
        }catch (Exception e){
            LOGGER.info(e.toString());
        }
        return new ArrayList<>();

    }

    public void add(String name) {
            TestSupplierEntity t = new TestSupplierEntity();
            t.setName(name);
            sessionFactory.getCurrentSession().save(t);

    }

    public void toggle(Integer id, boolean b) {
        TestSupplierEntity t = sessionFactory.getCurrentSession().find(TestSupplierEntity.class,id);
        t.setEnabled(b);
        sessionFactory.getCurrentSession().save(t);
    }
}
