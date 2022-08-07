package in.jamuna.hms.entities.hospital.stock;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "test_product_invoice")
public class TestBatchInvoiceEntity {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Getter
    @Setter
    @Column(name = "invoice",nullable = false)
    private String invoice;

    @Getter
    @Setter
    @Column(name = "date",nullable = false)
    private Date date;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier",nullable = false)
    private TestSupplierEntity supplier;
}
