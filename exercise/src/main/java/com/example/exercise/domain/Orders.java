package com.example.exercise.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class Orders {
    @Id
    @Column(name = "ORDERID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CUSTOMERID")
    private Customers customers;
    @Column(name = "EMPLOYEEID")
    private Long eid;
    @Column(name = "ORDERDATE")
    private LocalDate orderDate;
    @Column(name = "SHIPPERID")
    private Long sid;
}
