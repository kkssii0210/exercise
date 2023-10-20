package com.example.exercise.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Customers {
    @Id
    @Column(name = "CUSTOMERID")
    private Long customerId;

    @Column(name = "CUSTOMERNAME")
    private String customerName;

    @Column(name = "CONTACTNAME")
    private String contactName;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "CITY")
    private String city;

    @Column(name = "POSTALCODE")
    private String postalCode;

    @Column(name = "COUNTRY")
    private String country;

    @OneToMany(mappedBy="customers")
    private List<Orders> orders;
}
