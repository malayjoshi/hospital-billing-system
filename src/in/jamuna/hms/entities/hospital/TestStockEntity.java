package in.jamuna.hms.entities.hospital;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "test_stock")
public class TestStockEntity {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice",nullable = false)
    private TestBatchInvoiceEntity invoice;

    @Getter
    @Setter
    @Column(name = "qty",nullable = false)
    private double qty;


    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product",nullable = false)
    private TestProductEntity product;

    @Getter
    @Setter
    @Column(name = "batch",nullable = false)
    private String batch;

    @Getter
    @Setter
    @Column(name = "expiry",nullable = false)
    private Date expiry;

    @Getter
    @Setter
    @Column(name = "hsn")
    private String hsn;

    @Getter
    @Setter
    @Column(name = "mrp",nullable = false)
    private double mrp;

    @Getter
    @Setter
    @Column(name = "rate", nullable = false)
    private double rate;

    @Getter
    @Setter
    @Column(name = "discount",nullable = false)
    private double discount = 0;

    @Getter
    @Setter
    @Column(name = "free")
    private double free;

    @Getter
    @Setter
    @Column(name = "tax", nullable = false)
    private double tax;

    @Getter
    @Setter
    @Column(name = "amount", nullable = false)
    private double amount;

}
