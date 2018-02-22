package by.bsuir.spp.jewelryrentsystem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
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

    @Column(name = "surname")
    @Getter
    @Setter
    private String surname;

    @Column(name = "second_name")
    @Getter
    @Setter
    private String secondName;

    @Column(name = "salary")
    @Getter
    @Setter
    private double salary;

    @Column(name = "position")
    @Getter
    @Setter
    private String position;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;
}
