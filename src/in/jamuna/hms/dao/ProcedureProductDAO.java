package in.jamuna.hms.dao;

import in.jamuna.hms.dto.cart.CartItemDTO;
import in.jamuna.hms.entities.hospital.ProcedureProductMappingEntity;
import in.jamuna.hms.entities.hospital.ProcedureRatesEntity;
import in.jamuna.hms.entities.hospital.TestProductEntity;
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
}
