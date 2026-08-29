package com.example.transactionstarter.service;

import com.example.transactionstarter.model.Customer;
import com.example.transactionstarter.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);
    Customer createCustomer(Customer customer);
    List<Transaction> findByCustomerId(String customerId);
    Transaction getTransaction(String transactiondId);
}
