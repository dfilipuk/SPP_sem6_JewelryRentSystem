package by.bsuir.spp.jewelryrentsystem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "status")
    @Getter
    @Setter
    private String status;

    @Column(name = "rent_date")
    @Getter
    @Setter
    private Date rentDate;

    @Column(name = "days_rent")
    @Getter
    @Setter
    private int daysRent;

    @Column(name = "cost")
    @Getter
    @Setter
    private double cost;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "jewelry_id", nullable = false)
    private Jewelry jewelry;
}
