package com.example.transactionstarter;

import com.example.transactionstarter.exception.DuplicateTransactionException;
import com.example.transactionstarter.exception.TransactionNotFoundException;
import com.example.transactionstarter.exception.ValidateException;
import com.example.transactionstarter.model.Customer;
import com.example.transactionstarter.model.Transaction;
import com.example.transactionstarter.model.TransactionStatus;
import com.example.transactionstarter.model.TransactionType;
import com.example.transactionstarter.repository.CustomerRepository;
import com.example.transactionstarter.repository.TransactionRepository;
import com.example.transactionstarter.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionServiceImplTests {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionService transactionService;

    //This method ensures that after each test the database should be wiped off data
    // So that each test runs in the same database environment.
    @BeforeEach
    void setUp(){
        transactionRepository.deleteAll();
        customerRepository.deleteAll();
    }
    //This Test tests whether the transaction is being created or not.
    //A Transaction created successfully.
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

    //This test ensures that a transaction is rejected when invalid data is provided.
    //A transaction is rejected because it fails validation.
    @Test
    void createTransactionWithInvalidAmount(){
        Customer customer = new Customer();
        customer.setCustomerId("C01");
        customer.setName("Aravind");
        customer.setEmail("aravind@gmail.com");
        customerRepository.save(customer);

        Transaction transaction = new Transaction();
        transaction.setTransactionId("TX02");
        transaction.setCustomer(customer);
        transaction.setAmount(BigDecimal.valueOf(0));
        transaction.setCurrency("INR");
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        assertThrows(
                ValidateException.class,
                () -> transactionService.createTransaction(transaction)
        );
    }

    //This test ensures that a Duplicate Transaction cannot be created.
    //A duplicate transaction ID rejected.
    @Test
    void createDuplicateTransaction(){
        Customer c = new Customer();
        c.setCustomerId("C01");
        c.setName("Aravind");
        c.setEmail("aravind@gmail.com");
        customerRepository.save(c);

        Transaction t = new Transaction();
        t.setTransactionId("TX01");
        t.setCustomer(c);
        t.setCurrency("INR");
        t.setAmount(BigDecimal.valueOf(1000));
        t.setTransactionType(TransactionType.PAYMENT);
        t.setTransactionStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(t);


        Transaction t1 = new Transaction();
        t1.setTransactionId("TX01");
        t1.setCustomer(c);
        t1.setCurrency("INR");
        t1.setAmount(BigDecimal.valueOf(1000));
        t1.setTransactionType(TransactionType.PAYMENT);
        t1.setTransactionStatus(TransactionStatus.COMPLETED);
        assertThrows(
                DuplicateTransactionException.class,
                () -> transactionService.createTransaction(t1)
        );

    }

    //This Test ensures that we cannot retrieve a transaction that does not exist.
    //A request for transaction that does not exist.
    @Test
    void getTransactionThatDoesNotExist(){
        assertThrows(
                TransactionNotFoundException.class,
                () -> transactionService.getTransaction("TX01")
        );
    }
}
