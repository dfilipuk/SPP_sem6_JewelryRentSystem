package by.bsuir.spp.jewelryrentsystem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "branches")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "address")
    @Getter
    @Setter
    private String address;

    @Column(name = "telephone")
    @Getter
    @Setter
    private String telephone;
}
