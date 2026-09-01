package com.example.transactionstarter.service;

import com.example.transactionstarter.exception.DuplicateTransactionException;
import com.example.transactionstarter.exception.TransactionNotFoundException;
import com.example.transactionstarter.exception.ValidateException;
import com.example.transactionstarter.model.Customer;
import com.example.transactionstarter.model.Transaction;
import com.example.transactionstarter.model.TransactionStatus;
import com.example.transactionstarter.repository.CustomerRepository;
import com.example.transactionstarter.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

        if(transaction.getTransactionId() == null || transaction.getTransactionId().isBlank()){
            throw new ValidateException("Transaction ID is required");
        }
        if(transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0)
        {
            throw new ValidateException("Amount must be greater than 0");
        }
        if(transaction.getCurrency() == null || transaction.getCurrency().isBlank()){
            throw new ValidateException("Currency is required");
        }
        if(transaction.getTransactionType() == null){
            throw new ValidateException("Transaction Type is not required");
        }
        if(transaction.getCustomer() == null ||
                transaction.getCustomer().getCustomerId() == null ||
                transaction.getCustomer().getCustomerId().isBlank()){
            throw new ValidateException("Customer ID is required");
        }
        String customerId = transaction.getCustomer().getCustomerId();
        Customer customer =  customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ValidateException("Customer not found " + customerId));
        transaction.setCustomer(customer);

        Optional<Transaction> t = transactionRepository.findById(transaction.getTransactionId());
        if(t.isPresent()){
            throw new DuplicateTransactionException("Transaction Rejected. Reason : Transaction already exists with ID:" + transaction.getTransactionId());
        }
        else{
            return transactionRepository.save(transaction);
        }
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
                .orElseThrow(() -> new TransactionNotFoundException("Transaction Not Found" + transactionId));
    }

    @Override
    public Transaction updateTransactionStatus(String transactionId, TransactionStatus transactionStatus) {
        Transaction transaction = getTransaction(transactionId);
        if(transaction.getTransactionStatus() != TransactionStatus.PENDING){
            throw new ValidateException("Transaction status cannot be changed from "+transaction.getTransactionStatus());
        }
        transaction.setTransactionStatus(transactionStatus);
        return transactionRepository.save(transaction);
    }
}
