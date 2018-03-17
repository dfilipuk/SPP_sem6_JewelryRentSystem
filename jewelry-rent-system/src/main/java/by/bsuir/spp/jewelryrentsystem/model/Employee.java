package by.bsuir.spp.jewelryrentsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "login")
    @Getter
    @Setter
    private String login;

    @Column(name = "password", length = 512)
    @Getter
    @Setter
    private String password;

    @Column(name = "role")
    @Getter
    @Setter
    private String role;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonIgnore
    private Branch branch;
}
