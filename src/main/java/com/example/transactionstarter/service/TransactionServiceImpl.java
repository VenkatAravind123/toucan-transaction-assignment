package com.example.transactionstarter.service;

import com.example.transactionstarter.model.Customer;
import com.example.transactionstarter.model.Transaction;
import com.example.transactionstarter.model.TransactionStatus;
import com.example.transactionstarter.repository.CustomerRepository;
import com.example.transactionstarter.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Transaction getTransaction(String transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not Found" + transactionId));
    }

    @Override
    public Transaction updateTransactionStatus(String transactionId, TransactionStatus transactionStatus) {
        Transaction transaction = getTransaction(transactionId);
        if(transaction.getTransactionStatus() != TransactionStatus.PENDING){
            throw new RuntimeException("Transaction status cannot be changed from "+transaction.getTransactionStatus());
        }
        transaction.setTransactionStatus(transactionStatus);
        return transactionRepository.save(transaction);
    }
}
