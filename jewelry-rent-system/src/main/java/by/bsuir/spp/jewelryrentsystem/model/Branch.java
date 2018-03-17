package by.bsuir.spp.jewelryrentsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

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

    @Getter
    @Setter
    @OneToMany(mappedBy = "branch")
    @JsonIgnore
    private Set<Employee> employees;

    @Getter
    @Setter
    @OneToMany(mappedBy = "branch")
    @JsonIgnore
    private Set<Jewelry> jewelries;
}
