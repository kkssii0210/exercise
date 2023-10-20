package com.example.exercise.repository;

import com.example.exercise.domain.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, Integer>, QuerydslPredicateExecutor<Customers> {
    @Query("select c.customerName from Customers c")
    List<String> findAllByCustomerName();

    long countByCountry(String country);

    List<Customers> findByCountryIn(List<String> countries);

    List<Customers> findByCustomerNameContaining(String keyword);
}
