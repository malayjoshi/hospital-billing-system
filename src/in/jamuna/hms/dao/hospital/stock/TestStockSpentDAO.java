package in.jamuna.hms.dao.hospital.stock;

import in.jamuna.hms.entities.hospital.stock.AllocatedStockEntity;
import in.jamuna.hms.entities.hospital.billing.ProcedureBillItemEntity;
import in.jamuna.hms.entities.hospital.stock.TestStockSpentEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class TestStockSpentDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void add(ProcedureBillItemEntity billItem, AllocatedStockEntity l, double qtyLeft) {
        TestStockSpentEntity spent = new TestStockSpentEntity();
        spent.setAllocatedStock(l);
        spent.setItem(billItem);
        spent.setQty(qtyLeft);
        sessionFactory.getCurrentSession().save(spent);
    }
}
