package in.jamuna.hms.dao.hospital.stock;

import in.jamuna.hms.entities.hospital.stock.AllocatedStockEntity;
import in.jamuna.hms.entities.hospital.billing.ProcedureBillItemEntity;
import in.jamuna.hms.entities.hospital.stock.TestStockSpentEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Repository
@Transactional
public class TestStockSpentDAO {
    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger LOGGER = Logger.getLogger(TestStockSpentDAO.class.getName());

    public void add(ProcedureBillItemEntity billItem, AllocatedStockEntity l, double qtyLeft) {
        TestStockSpentEntity spent = new TestStockSpentEntity();
        spent.setAllocatedStock(l);
        spent.setItem(billItem);
        spent.setQty(qtyLeft);
        sessionFactory.getCurrentSession().save(spent);
    }

    public List<TestStockSpentEntity> findByStartAndEndDateAndGroupByProducts(Date date1, Date date2) {
        try {

            Query query = sessionFactory.getCurrentSession().
                    createQuery(
                            "from TestStockSpentEntity where item.bill.date>=:date1 and item.bill.date<=:date2 "
                            ,TestStockSpentEntity.class);
            query.setParameter("date1",date1);
            query.setParameter("date2",date2);
            return query.getResultList();
        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }
        return new ArrayList<>();
    }
}
