package in.jamuna.hms.entities.hospital.stock;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="test_products")
public class TestProductEntity {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Getter
    @Setter
    @Column(name="name")
    private String name;

    @Getter
    @Setter
    @Column(name="enabled")
    private boolean enabled;


    @Getter
    @Setter
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="company")
    private TestCompanyEntity company;

    @Setter
    @Getter
    @OneToMany(mappedBy = "product",orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<ProcedureProductMappingEntity> mappings;

    @Setter
    @Getter
    @OneToMany(mappedBy = "product",orphanRemoval = true,
            cascade = CascadeType.ALL)
    private  List<TestStockEntity> stock;

}
