package com.example.transactionstarter.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="transaction_table")
public class Transaction {
    @Id
    @Column(name="transaction_id",nullable = false)
    private String transactionId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(
            name = "customer_id",
            nullable = false,
            referencedColumnName = "customer_id"
    )
    private Customer customer;

    @Column(nullable = false,precision = 19,scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name="transaction_type",nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus transactionStatus;

    public Transaction(){

    }

    public Transaction(String transactionId, Customer customer, BigDecimal amount, String currency, TransactionType transactionType, TransactionStatus transactionStatus) {
        this.transactionId = transactionId;
        this.customer = customer;
        this.amount = amount;
        this.currency = currency;
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
