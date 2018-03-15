package by.bsuir.spp.jewelryrentsystem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "materials")
public class Material {
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

    @Column(name = "description")
    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private Material parentMaterial;

    @Getter
    @Setter
    @OneToMany(mappedBy = "parentMaterial", fetch = FetchType.LAZY)
    private Set<Material> childMaterials;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "materials", fetch = FetchType.LAZY)
    private Set<Jewelry> jewelries;
}
