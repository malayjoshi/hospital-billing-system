package in.jamuna.hms.entities.hospital.stock;

import in.jamuna.hms.entities.hospital.billing.ProcedureBillItemEntity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "test_stock_spent")
public class TestStockSpentEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Getter
    @Setter
    @Column(name = "qty",nullable = false)
    private double qty;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock")
    private AllocatedStockEntity allocatedStock;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_item")
    private ProcedureBillItemEntity item;


}
