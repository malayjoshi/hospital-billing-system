package in.jamuna.hms.entities.hospital;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "allocated_stock")
public class AllocatedStockEntity {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Getter
    @Setter
    @Column(name = "date",nullable = false)
    private Date date;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock",nullable = false)
    private TestStockEntity stock;

    @Getter
    @Setter
    @Column(name = "qty",nullable = false)
    private double qty;

    @Getter
    @Setter
    @Column(name = "qty_left",nullable = false)
    private double qtyLeft;

}
