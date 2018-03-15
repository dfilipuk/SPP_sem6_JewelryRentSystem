package by.bsuir.spp.jewelryrentsystem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "jewelries")
public class Jewelry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @Column(name = "producer")
    @Getter
    @Setter
    private String producer;

    @Column(name = "description")
    @Getter
    @Setter
    private String description;

    @Column(name = "pictureUrl")
    @Getter
    @Setter
    private String pictureUrl;

    @Column(name = "type")
    @Getter
    @Setter
    private String type;

    @Column(name = "weight")
    @Getter
    @Setter
    private double weight;

    @Column(name = "status")
    @Getter
    @Setter
    private String status;

    @Column(name = "cost_per_day")
    @Getter
    @Setter
    private double costPerDay;

    @Column(name = "days_rental")
    @Getter
    @Setter
    private int daysRental;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Getter
    @Setter
    @OneToMany(mappedBy = "jewelry")
    private Set<Order> orders;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "jewelry_material",
            joinColumns = @JoinColumn(name = "jewelry_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "material_id", referencedColumnName = "id")
    )
    private Set<Material> materials;
}
