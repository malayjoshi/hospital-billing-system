package in.jamuna.hms.entities.hospital.stock;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="test_suppliers")
public class TestSupplierEntity {
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

}
