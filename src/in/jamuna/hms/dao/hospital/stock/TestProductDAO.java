package in.jamuna.hms.dao.hospital.stock;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dto.common.ProductForInvoice;
import in.jamuna.hms.entities.hospital.stock.TestCompanyEntity;
import in.jamuna.hms.entities.hospital.stock.TestProductEntity;
import in.jamuna.hms.entities.hospital.stock.TestStockSpentEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Repository
@Transactional
public class TestProductDAO {
    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger LOGGER = Logger.getLogger(TestProductDAO.class.getName());
    public List<TestProductEntity> getByPage(Integer no, int perPage) {
        try {
            Query query=sessionFactory.getCurrentSession().createQuery(
                    "from TestProductEntity", TestProductEntity.class);
            query.setFirstResult((no - 1) * perPage);
            query.setMaxResults(perPage);

            return query.getResultList();
        }catch (Exception e){
            LOGGER.info(e.toString());
        }
        return new ArrayList<>();
    }

    public long getCount() {
       return sessionFactory.getCurrentSession().createQuery("from TestProductEntity ",TestProductEntity.class).getResultList().size();
    }

    public void add(String name, TestCompanyEntity company) {
        TestProductEntity t = new TestProductEntity();
        t.setName(name);
        t.setEnabled(true);
        t.setCompany(company);

            sessionFactory.getCurrentSession().save(t);

    }

    public void toggle(Integer id, boolean b) {
        TestProductEntity t = sessionFactory.getCurrentSession().find(TestProductEntity.class,id);
        t.setEnabled(b);
        sessionFactory.getCurrentSession().save(t);
    }

    public List<TestProductEntity> findByNameAndLimit(String term, int searchlimit) {
        Query query = sessionFactory.getCurrentSession().createQuery("from TestProductEntity  where name like :term ",TestProductEntity.class);
        query.setParameter("term","%"+term+"%");
        query.setMaxResults(searchlimit);
        return query.getResultList();
    }

    public TestProductEntity findById(Integer productId) {
        return sessionFactory.getCurrentSession().find(TestProductEntity.class,productId);
    }

    public List<TestProductEntity> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from TestProductEntity ",TestProductEntity.class).getResultList();
    }

    public int findBySpentStock(TestStockSpentEntity s) {
        return sessionFactory.getCurrentSession().
                find(TestStockSpentEntity.class,s.getId())
                .getAllocatedStock().getStock().getProduct().getId();
    }



    public List<ProductForInvoice> getProductForInvoiceByTerm(String term) {
        return findByNameAndLimit(term, GlobalValues.getSearchlimit()).stream().map(s->{
                    ProductForInvoice dto = new ProductForInvoice();
                    dto.setEnabled(s.isEnabled());
                    dto.setName(s.getName());
                    dto.setId(s.getId());
                    dto.setCompany(s.getCompany().getName());

                    return dto;
                }

        ).collect(Collectors.toList());
    }

    public TestCompanyEntity getCompanyByProdId(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("select prod.company from TestProductEntity prod where prod.id=:id",TestCompanyEntity.class);
        query.setParameter("id",id);
        return (TestCompanyEntity) query.getSingleResult();
    }
}
