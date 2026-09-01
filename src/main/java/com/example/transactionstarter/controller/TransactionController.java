package com.example.transactionstarter.controller;

import com.example.transactionstarter.model.Customer;
import com.example.transactionstarter.model.Transaction;
import com.example.transactionstarter.model.TransactionStatus;
import com.example.transactionstarter.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/api/sample")
    public Map<String, String> sample() {
        return Map.of("message", "Starter project is running");
    }

    //Create a Transaction
    @PostMapping("/createtransaction")
    public Transaction createTransaction(@RequestBody Transaction transaction){
        return transactionService.createTransaction(transaction);
    }

    //Create a Customer added method
    @PostMapping("/createcustomer")
    public Customer createCustomer(@RequestBody Customer customer){
        return transactionService.createCustomer(customer);
    }
    //GET transactions List by Customer ID
    @GetMapping("/gettransactionbycid")
    public List<Transaction> getTransactionsByCustomerId(@RequestParam String customerId){
        return transactionService.findByCustomerId(customerId);
    }

    //GET transaction by Transaction ID
    @GetMapping("/gettransactionbytid/{transactionId}")
    public Transaction getTransactionByTransactionId(@PathVariable String transactionId){
        return transactionService.getTransaction(transactionId);
    }

    //UPDATE transaction status
    @PutMapping("/updatetransactionstatus")
    public Transaction updateTransactionStatus(@RequestParam String transactionId,@RequestParam TransactionStatus transactionStatus){
        return transactionService.updateTransactionStatus(transactionId,transactionStatus);
    }
}
