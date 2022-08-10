package in.jamuna.hms.dao.hospital.stock;

import in.jamuna.hms.entities.hospital.stock.ProcedureProductMappingEntity;
import in.jamuna.hms.entities.hospital.billing.ProcedureRatesEntity;
import in.jamuna.hms.entities.hospital.stock.TestProductEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ProcedureProductDAO {
    @Autowired
    private SessionFactory sessionFactory;


    public List<ProcedureProductMappingEntity> findByProcedure(ProcedureRatesEntity byId) {
       Query query = sessionFactory.getCurrentSession().createQuery("from ProcedureProductMappingEntity where test=:test",ProcedureProductMappingEntity.class);
        query.setParameter("test",byId);
        return query.getResultList();
    }

    public void add(TestProductEntity product, ProcedureRatesEntity procedure, int ratio) {

        ProcedureProductMappingEntity p =new ProcedureProductMappingEntity();
        p.setProduct(product);
        p.setTest(procedure);
        p.setRatio(ratio);
        sessionFactory.getCurrentSession().save(p);
    }

    public void changeRatioById(int id, int ratio) {
        ProcedureProductMappingEntity m = sessionFactory.getCurrentSession().find(ProcedureProductMappingEntity.class,id);
        m.setRatio(ratio);
        sessionFactory.getCurrentSession().save(m);
    }

    public void delete(int id) {
        sessionFactory.getCurrentSession().delete( sessionFactory.getCurrentSession().find(ProcedureProductMappingEntity.class,id) );
    }

    public ProcedureRatesEntity findTestById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "select mapping.test from ProcedureProductMappingEntity mapping where mapping.id=:id",ProcedureRatesEntity.class);
        query.setParameter("id",id);
        return (ProcedureRatesEntity) query.getSingleResult();
    }
}
