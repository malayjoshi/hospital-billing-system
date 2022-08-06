package in.jamuna.hms.dao;

import in.jamuna.hms.entities.hospital.TestBatchInvoiceEntity;
import in.jamuna.hms.entities.hospital.TestProductEntity;
import in.jamuna.hms.entities.hospital.TestStockEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class TestStockDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void add(TestBatchInvoiceEntity invoiceEntity, TestProductEntity byId, double amount, String batch, double discount, Date expiry, int free, int quantity, double tax, double rate, double mrp) {
        TestStockEntity stock =  new TestStockEntity();
        stock.setAmount(amount);
        stock.setBatch(batch);
        stock.setRate(rate);
        stock.setDiscount(discount);
        stock.setExpiry(expiry);
        stock.setFree(free);
        stock.setProduct(byId);
        stock.setInvoice(invoiceEntity);
        stock.setQty(quantity);
        stock.setTax(tax);
        stock.setQtyLeft(quantity+free);
        stock.setMrp(mrp);
        sessionFactory.getCurrentSession().save(stock);
    }

    public List<TestStockEntity> getBatchesForAllocateAndExpirySortAndExpiryGap(TestProductEntity product, double qty,String sorting,int gap) {
        // where left > 0 & ex[iry nearest
        List<TestStockEntity> list = new ArrayList<>();
        List<TestStockEntity> result  ;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);


        String queryStr = "from TestStockEntity where product=:product and qtyLeft>0 and expiry > :expiry order by expiry ";
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
}
