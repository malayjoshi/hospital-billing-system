package in.jamuna.hms.entities.hospital;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="company")
    private TestCompanyEntity company;

}
