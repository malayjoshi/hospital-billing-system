package in.jamuna.hms.entities.hospital;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="test_companies")
public class TestCompanyEntity {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Getter
    @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter
    @Setter
    @Column(name = "enabled", nullable = false)
    private boolean enabled=true;

    @Getter
    @Setter
    @OneToMany(mappedBy = "company")
    private List<TestProductEntity> products;

}
