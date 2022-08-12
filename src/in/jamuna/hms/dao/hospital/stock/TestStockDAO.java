package in.jamuna.hms.dao.hospital.stock;

import in.jamuna.hms.entities.hospital.stock.TestBatchInvoiceEntity;
import in.jamuna.hms.entities.hospital.stock.TestProductEntity;
import in.jamuna.hms.entities.hospital.stock.TestStockEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Repository
@Transactional
public class TestStockDAO {
    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger LOGGER = Logger.getLogger(TestStockDAO.class.getName());

    public void add(TestBatchInvoiceEntity invoiceEntity, TestProductEntity byId, String batch, Date expiry, int free, int quantity) {
        TestStockEntity stock =  new TestStockEntity();

        stock.setBatch(batch);
        stock.setExpiry(expiry);
        stock.setFree(free);
        stock.setProduct(byId);
        stock.setInvoice(invoiceEntity);
        stock.setQty(quantity);
        stock.setQtyLeft(quantity+free);

        sessionFactory.getCurrentSession().save(stock);
    }

    public List<TestStockEntity> getBatchesForAllocateAndExpirySortAndExpiryGap(TestProductEntity product, double qty,String sorting,int gap) {
        // where left > 0 & ex[iry nearest
        List<TestStockEntity> list = new ArrayList<>();
        List<TestStockEntity> result  ;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, gap);
        cal.set(Calendar.DAY_OF_MONTH, 1);


        String queryStr = "from TestStockEntity where product=:product and qtyLeft>0 and expiry >= :expiry order by expiry ";
        if(sorting.equals("desc")){
            queryStr+="desc";
        }

        Query query = sessionFactory.getCurrentSession()
                .createQuery(queryStr, TestStockEntity.class);
        query.setParameter("product",product);
        query.setParameter("expiry", cal.getTime());

        result = query.getResultList();
        double sum = 0;

        for(TestStockEntity s:result){

            sum+=s.getQtyLeft();
            list.add(s);
            if(sum>=qty)
                break;


        }

        return list;
    }

    public List<TestStockEntity> findByProduct(TestProductEntity product) {
        Query query = sessionFactory.getCurrentSession().createQuery("from TestStockEntity where qtyLeft > 0 and product=:product",TestStockEntity.class);
        query.setParameter("product",product);
        return query.getResultList();
    }

    public List<TestStockEntity> findTotalQtyLeftByProductAndExcludingExpired(TestProductEntity product, Date date) {
        try {
            Query query = sessionFactory.getCurrentSession()
                    .createQuery(
                            "from TestStockEntity where expiry>= :date and qtyLeft > 0 and product=:product",TestStockEntity.class);
            query.setParameter("product",product);
            query.setParameter("date",date);

            return query.getResultList();
        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }
        return new ArrayList<>();
    }


    public List<TestStockEntity> findByProductAndLessThanExpiry(TestProductEntity product, Date firstDayThisMonth) {
        try {
            Query query = sessionFactory.getCurrentSession()
                    .createQuery(
                            "from TestStockEntity where expiry < :date and qtyLeft > 0 and product=:product",TestStockEntity.class);
            query.setParameter("product",product);
            query.setParameter("date",firstDayThisMonth);

            return query.getResultList();
        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<TestStockEntity> findByProductOnly(TestProductEntity product) {
        try {
            Query query = sessionFactory.getCurrentSession()
                    .createQuery(
                            "from TestStockEntity where  product=:product",TestStockEntity.class);
            query.setParameter("product",product);

            return query.getResultList();
        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }
        return new ArrayList<>();
    }
}
