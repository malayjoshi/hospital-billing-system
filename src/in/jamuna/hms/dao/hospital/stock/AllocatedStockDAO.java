package in.jamuna.hms.dao.hospital.stock;

import in.jamuna.hms.entities.hospital.stock.AllocatedStockEntity;
import in.jamuna.hms.entities.hospital.stock.TestProductEntity;
import in.jamuna.hms.entities.hospital.stock.TestStockEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class AllocatedStockDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void add(Date date, TestStockEntity testStockEntity, double qtyLeft) {
        AllocatedStockEntity allocated = new AllocatedStockEntity();
        allocated.setStock(testStockEntity);
        allocated.setDate(date);
        allocated.setQty(qtyLeft);
        allocated.setQtyLeft(qtyLeft);
        sessionFactory.getCurrentSession().save(allocated);
    }

    public Double getSumOfStockByProd(TestProductEntity prod) {
        Query query=sessionFactory.getCurrentSession().
                createQuery("select sum(stock.qtyLeft) from AllocatedStockEntity stock where stock.qtyLeft > 0 and stock.stock.product=:prod  " );
        query.setParameter("prod",prod);
        return (Double) query.getSingleResult();
    }

    public List<AllocatedStockEntity> findByQtyLeftAndStockProd(double level, TestProductEntity product) {
        Query query = sessionFactory.getCurrentSession().
                createQuery("from AllocatedStockEntity where qtyLeft >:level and stock.product=:product order by date",AllocatedStockEntity.class);
        query.setParameter("level",level);
        query.setParameter("product",product);
        return query.getResultList();
    }
}
