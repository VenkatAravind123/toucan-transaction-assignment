package com.example.transactionstarter;

import com.example.transactionstarter.model.Customer;
import com.example.transactionstarter.model.Transaction;
import com.example.transactionstarter.model.TransactionStatus;
import com.example.transactionstarter.model.TransactionType;
import com.example.transactionstarter.repository.CustomerRepository;
import com.example.transactionstarter.repository.TransactionRepository;
import com.example.transactionstarter.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TransactionServiceImplTests {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionService transactionService;

    @Test
    void createTransactionSuccessfully(){

        Customer customer = new Customer();
        customer.setCustomerId("C01");
        customer.setName("Aravind");
        customer.setEmail("aravind@gmail.com");
        customerRepository.save(customer);

        Transaction transaction = new Transaction();
        transaction.setTransactionId("TX01");
        transaction.setCustomer(customer);
        transaction.setAmount(BigDecimal.valueOf(100.0));
        transaction.setCurrency("INR");
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        Transaction result = transactionService.createTransaction(transaction);

        assertNotNull(result);
        assertEquals("TX01",result.getTransactionId());
    }

    @Test
    void createTransactionWithInvalidAmount(){
        Customer customer = new Customer();
        customer.setCustomerId("C01");
        customer.setName("Aravind");
        customer.setEmail("aravind@gmail.com");
        customerRepository.save(customer);

        Transaction transaction = new Transaction();
        transaction.setTransactionId("TX01");
        transaction.setCustomer(customer);
        transaction.setAmount(BigDecimal.valueOf(100.0));
        transaction.setCurrency("");
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        Transaction result = transactionService.createTransaction(transaction);

    }
}
