package in.jamuna.hms.dao.hospital;

import in.jamuna.hms.entities.hospital.AllocatedStockEntity;
import in.jamuna.hms.entities.hospital.ProcedureBillItemEntity;
import in.jamuna.hms.entities.hospital.TestStockSpentEntity;
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
