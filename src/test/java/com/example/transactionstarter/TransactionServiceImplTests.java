package com.example.transactionstarter;
import static org.junit.jupiter.api.Assertions.*;
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
import java.util.List;

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
    //Test 1
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
    //Test 2
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
    //Test 3
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
    //Test 4
    @Test
    void getTransactionThatDoesNotExist(){
        assertThrows(
                TransactionNotFoundException.class,
                () -> transactionService.getTransaction("TX01")
        );
    }

    //THis test ensures that the status of the transaction is updating successfully.
    //UPDATE Transaction status from PENDING to COMPLETED.
    //Test 5
    @Test
    void updateTransactionStatusSuccessfully(){
        Customer c = new Customer();
        c.setEmail("aravind@gmail.com");
        c.setName("Aravind");
        c.setCustomerId("C01");
        customerRepository.save(c);

        Transaction transaction = new Transaction();
        transaction.setTransactionId("TX04");
        transaction.setCustomer(c);
        transaction.setAmount(BigDecimal.valueOf(1000));
        transaction.setCurrency("INR");
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transactionRepository.save(transaction);

        Transaction result = transactionService.updateTransactionStatus("TX04",TransactionStatus.COMPLETED);

        assertNotNull(result);
        assertEquals(TransactionStatus.COMPLETED,result.getTransactionStatus());
    }
    //This test ensures that transactions of a customer are retrieved successfully by customer ID.
    //Test 6
    @Test
    void getCustomerTransactions(){
        Customer c = new Customer();
        c.setEmail("aravind@gmail.com");
        c.setName("Aravind");
        c.setCustomerId("C01");
        customerRepository.save(c);

        Transaction transaction = new Transaction();
        transaction.setTransactionId("TX01");
        transaction.setCustomer(c);
        transaction.setAmount(BigDecimal.valueOf(1000));
        transaction.setCurrency("INR");
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transactionRepository.save(transaction);

        Transaction transaction1 = new Transaction();
        transaction1.setTransactionId("TX02");
        transaction1.setCustomer(c);
        transaction1.setAmount(BigDecimal.valueOf(1000));
        transaction1.setCurrency("INR");
        transaction1.setTransactionType(TransactionType.PAYMENT);
        transaction1.setTransactionStatus(TransactionStatus.PENDING);
        transactionRepository.save(transaction1);

        List<Transaction> customerTransactions = transactionService.findByCustomerId(c.getCustomerId());
        assertEquals(2,customerTransactions.size());

    }
    //This test ensures that the customer is created successfully.
    //Test 7
    @Test
    void createCustomer(){
        Customer c = new Customer();
        c.setEmail("aravind@gmail.com");
        c.setName("Aravind");
        c.setCustomerId("C01");


        Customer customer = transactionService.createCustomer(c);
        assertNotNull(customer);
        assertEquals("C01",customer.getCustomerId());
    }




    //This test ensures that a Transaction status cannot be changed once it is already COMPLETED.
    //Test 8
    @Test
    void cannotUpdateCompletedTransaction(){
        Customer c = new Customer();
        c.setEmail("aravind@gmail.com");
        c.setName("Aravind");
        c.setCustomerId("C01");
        customerRepository.save(c);

        Transaction transaction = new Transaction();
        transaction.setTransactionId("TX04");
        transaction.setCustomer(c);
        transaction.setAmount(BigDecimal.valueOf(1000));
        transaction.setCurrency("INR");
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);


        assertThrows(
                ValidateException.class,
                () -> transactionService.updateTransactionStatus("TX04",TransactionStatus.FAILED)
        );
    }



    //This test ensures that a transaction cannot be created without a customer.
    //Test 9
    @Test
    void createTransactionWithoutCustomer(){
        Transaction transaction = new Transaction();
        transaction.setTransactionId("TX03");
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setCurrency("INR");
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        assertThrows(
                ValidateException.class,
                () -> transactionService.createTransaction(transaction)
        );

    }


    //This test ensures that an existing transaction is retrieved successfully.
    //Test 10
    @Test
    void getExistingTransactionSuccessfully(){
        Customer c = new Customer();
        c.setEmail("aravind@gmail.com");
        c.setName("Aravind");
        c.setCustomerId("C01");
        customerRepository.save(c);

        Transaction transaction = new Transaction();
        transaction.setTransactionId("TX01");
        transaction.setCustomer(c);
        transaction.setAmount(BigDecimal.valueOf(1000));
        transaction.setCurrency("INR");
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transactionRepository.save(transaction);

        Transaction t = transactionService.getTransaction(transaction.getTransactionId());
        assertEquals(transaction.getTransactionId(),t.getTransactionId());
    }
}
