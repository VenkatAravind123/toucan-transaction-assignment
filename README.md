# Transaction Starter Project

This is the starter project for the Customer Transactions exercise.

## Before you start

The first thing you should do after cloning the repository is:

### Linux / macOS

```bash
./mvnw clean test
```

### Windows

```bat
mvnw.cmd clean test
```

The sample test should pass before you begin implementing the exercise.

## What is already provided

- Java 17
- Spring Boot
- Maven wrapper
- Spring Web
- Spring Data JPA
- H2 embedded database
- JUnit / Spring Boot Test
- A sample REST endpoint: `GET /api/sample`
- A sample test that loads the Spring context

## Exercise

Implement these four operations:

1. Create transaction
2. Get transaction
3. Update transaction status
4. Get all transactions for a customer

You may change the surrounding design if you believe your solution is better.

## What I Implemented in this project

## Customer Fields

Every Customer contains:

- Customer ID => Primary Key (String)
- Name (String)
- Email (String)

## Transaction fields

Every transaction contains:

- Transaction ID => Primary Key (String)
- Customer ID => Customer ID is Foreign key (String)
- Amount (BigDecimal)
- Currency (String)
- Transaction Type (Enum) => "PAYMENT", "REFUND"
- Transaction Status (Enum) => "PENDING", "COMPLETED", "FAILED"

### My Validation rules

- Transaction ID must not be null or blank.
- Customer ID must not be null or blank.
- Customer ID must refer to an existing customer.
- Amount must be greater than 0.
- Currency must not be null or blank.
- Transaction type is required.
- Transaction Status is required when creating a transaction.
- A transaction status can only be updated when the current status is "PENDING".

## API skeleton

### Create

`POST /createtransaction`

Creates a new transaction after validating the input and checking that the customer exists and transaction ID is unique.

Request:

```json
{
  "transactionId": "TX01",
  "customer": {
    "customerId": "C01"
  },
  "amount": 1,
  "currency": "INR",
  "transactionType": "PAYMENT",
  "transactionStatus": "PENDING"
}
```

Response:

```json
{
  "transactionId": "TX01",
  "customer": {
    "customerId": "C01",
    "name": "Aravind",
    "email": "aravind@gmail.com"
  },
  "amount": 1.00,
  "currency": "INR",
  "transactionType": "PAYMENT",
  "transactionStatus": "PENDING"
}
```

### Get

`GET /gettransactionbytid/TX01`

Used Path Variable for input.

Returns the transaction with ID `TX01`.

Response:

```json
{
  "transactionId": "TX01",
  "customer": {
    "customerId": "C01",
    "name": "Venkat",
    "email": "aravind@gmail.com"
  },
  "amount": 1000.00,
  "currency": "INR",
  "transactionType": "PAYMENT",
  "transactionStatus": "PENDING"
}
```

### Update status

`PUT /updatetransactionstatus?transactionId=TX01&transactionStatus=FAILED`

Used Request Param for inputs.

Response:

```json
{
  "transactionId": "TX01",
  "customer": {
    "customerId": "C01",
    "name": "Venkat",
    "email": "aravind@gmail.com"
  },
  "amount": 1000.00,
  "currency": "INR",
  "transactionType": "PAYMENT",
  "transactionStatus": "FAILED"
}
```

### Get customer transactions

`GET /gettransactionbycid?customerId=C01`

Response:

```json
[
  {
    "transactionId": "TX01",
    "customer": {
      "customerId": "C01",
      "name": "Venkat",
      "email": "aravind@gmail.com"
    },
    "amount": 1000.00,
    "currency": "INR",
    "transactionType": "PAYMENT",
    "transactionStatus": "FAILED"
  },
  {
    "transactionId": "TX02",
    "customer": {
      "customerId": "C01",
      "name": "Venkat",
      "email": "aravind@gmail.com"
    },
    "amount": 1200.00,
    "currency": "INR",
    "transactionType": "PAYMENT",
    "transactionStatus": "COMPLETED"
  }
]
```

### Create Customer

`POST /createcustomer`

Creates a new customer.

Request:

```json
{
  "customerId": "C01",
  "name": "Venkat",
  "email": "aravind@gmail.com"
}
```

Response:

```json
{
  "customerId": "C01",
  "name": "Venkat",
  "email": "aravind@gmail.com"
}
```

## Testing

Automated Tests are implemented using JUnit and Spring Boot Test.

The following scenarios are covered:

1. Create a Transaction Successfully.
2. Transaction Rejected when Invalid amount is given.
3. Duplicate Transaction ID Rejected.
4. Request for a Transaction that Does not Exist.
5. Successful Transaction status update.
6. Retrieval of all transactions for a customer.
7. Creation of a New Customer.
8. Cannot Update a Completed Transaction.
9. Transaction Rejected when Customer is Not Found.
10. Retrieve Existing Transaction Successfully.

The tests use the H2 in-memory database and clear the transaction and customer data before each test to keep each test independent.

## Documentation

### Understanding of the Problem

This Application is a Transaction Processing Service that manages customers to process transactions.

The Application implements main four operations: Create a Transaction, Update Transaction Status,
Get Transaction by ID, and Get Transactions by Customer ID.

A Transaction is an entity that can be created by an existing customer. I implemented the relation between
the Transaction and Customer using a `@ManyToOne` Mapping, where one customer can have many transactions.

### Assumptions I made

- Customer ID and Transaction ID are primary keys and are unique.
- A transaction can only be created for an existing customer.
- Transaction ID must be unique.
- Amount must be greater than zero.
- Transaction Types are PAYMENT and REFUND.
- Transaction Status are PENDING, COMPLETED, FAILED.
- I allow status updates only when the current status is PENDING because COMPLETED and FAILED 
transactions represent final states and should not be changed after processing.

### Testing Approach

As this application uses in-memory database I added a setup to wipe out the database before each test.

### Known Limitations

- The application has entities which are used directly as request and response models;
  There are no DTOs introduced.
- Exception handling could be improved by introducing a another minute exceptions.
- Validation is implemented in the service layer but we can implement Bean Validations.

### Improvements With More Time

With additional time

- I would introduce DTOs for better seperation between the API and database.
- I would implement exceptions with detailed HTTP Status codes and Error messages.
- I would implement more detailed Bean Validations for the Entity models.


### Result

```bash
./mvnw clean test
```

```text
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.027 s -- in com.example.transactionstarter.TransactionStarterApplicationTests
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 11, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  36.098 s
[INFO] Finished at: 2026-09-02T10:48:59+05:30
[INFO] ------------------------------------------------------------------------
```