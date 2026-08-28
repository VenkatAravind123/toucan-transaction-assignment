package com.example.transactionstarter.repository;

import com.example.transactionstarter.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {
}
