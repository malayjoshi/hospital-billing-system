package in.jamuna.hms.dao.hospital.stock;

import in.jamuna.hms.entities.hospital.stock.TestBatchInvoiceEntity;
import in.jamuna.hms.entities.hospital.stock.TestSupplierEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
@Transactional
public class TestInvoiceDAO {
    @Autowired
    private SessionFactory sessionFactory;


    public TestBatchInvoiceEntity add(TestSupplierEntity supplier, Date invoiceDate, String invoice) {
        TestBatchInvoiceEntity invoiceEntity = new TestBatchInvoiceEntity();
        invoiceEntity.setInvoice(invoice);
        invoiceEntity.setDate(invoiceDate);
        invoiceEntity.setSupplier(supplier);
        sessionFactory.getCurrentSession().save(invoiceEntity);
        return invoiceEntity;
    }
}
