package in.jamuna.hms.dao;

import in.jamuna.hms.entities.hospital.TestBatchInvoiceEntity;
import in.jamuna.hms.entities.hospital.TestProductEntity;
import in.jamuna.hms.entities.hospital.TestStockEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
@Transactional
public class TestStockDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void add(TestBatchInvoiceEntity invoiceEntity, TestProductEntity byId, double amount, String batch, double discount, Date expiry, int free, int quantity, double tax, double rate) {
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
        stock.setLeft(quantity);
        sessionFactory.getCurrentSession().save(stock);
    }
}
