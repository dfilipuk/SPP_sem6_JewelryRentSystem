package by.bsuir.spp.jewelryrentsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {
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

    @Column(name = "passport_number")
    @Getter
    @Setter
    private String passportNumber;

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
    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private Set<Order> orders;
}
