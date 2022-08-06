package in.jamuna.hms.dao;

import in.jamuna.hms.entities.hospital.AllocatedStockEntity;
import in.jamuna.hms.entities.hospital.TestStockEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

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
}
