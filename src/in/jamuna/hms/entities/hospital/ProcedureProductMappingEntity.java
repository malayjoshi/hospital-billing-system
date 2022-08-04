package in.jamuna.hms.entities.hospital;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "procedure_product_mapping")
public class ProcedureProductMappingEntity {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Getter
    @Setter
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "product")
    private TestProductEntity product;

    @Getter
    @Setter
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "proc")
    private ProcedureRatesEntity test;

    @Getter
    @Setter
    @Column(name = "ratio") // how many tests can be done with one piece
    private int ratio;


}
