package in.jamuna.hms.dao.hospital;

import in.jamuna.hms.entities.hospital.BillGroupsEntity;
import in.jamuna.hms.entities.hospital.TestCompanyEntity;
import in.jamuna.hms.entities.hospital.TestSupplierEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Repository
@Transactional
public class TestCompanyDAO {
    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger LOGGER=Logger.getLogger(TestCompanyDAO.class.getName());

    public long getCount() {
        return sessionFactory.getCurrentSession().
                createQuery("from TestCompanyEntity ",TestCompanyEntity.class).getResultList().size();
    }

    public List<TestCompanyEntity> getByPage(Integer no, int perpage) {
        try {
            Query query=sessionFactory.getCurrentSession().createQuery("from TestCompanyEntity", TestCompanyEntity.class);
            query.setFirstResult((no - 1) * perpage);
            query.setMaxResults(perpage);

            return query.getResultList();
        }catch (Exception e){
            LOGGER.info(e.toString());
        }
        return new ArrayList<>();
    }

    public void add(String name) {
        TestCompanyEntity t = new TestCompanyEntity();
        t.setName(name);
        sessionFactory.getCurrentSession().save(t);
    }

    public void toggle(Integer id, boolean b) {
        TestCompanyEntity t = sessionFactory.getCurrentSession().find(TestCompanyEntity.class,id);
        t.setEnabled(b);
        sessionFactory.getCurrentSession().save(t);
    }

    public List<TestCompanyEntity> findByNameAndLimit(String term, int searchlimit) {
        Query query = sessionFactory.getCurrentSession().createQuery("from TestCompanyEntity where name like :term ",TestCompanyEntity.class);
        query.setParameter("term","%"+term+"%");
        query.setMaxResults(searchlimit);
        return query.getResultList();
    }
}
