package com.example.transactionstarter.service;

import com.example.transactionstarter.model.Customer;
import com.example.transactionstarter.model.Transaction;
import com.example.transactionstarter.repository.CustomerRepository;
import com.example.transactionstarter.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl  implements TransactionService{
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Transaction> findByCustomerId(String customerId) {
        return transactionRepository.findByCustomerCustomerId(customerId);
    }
}
