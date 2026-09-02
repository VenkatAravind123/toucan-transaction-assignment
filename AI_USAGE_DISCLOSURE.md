
## AI Usage Disclosure
I used ChatGPT as my AI Assistant to finalize my requirements and implementation details.

### How AI was used
- Used ChatGPT to get a bigger picture of the problem.
- Used it to help identify and resolve errors encountered during the development.
- Used it to review logics and code structure.
- Used it to structure a good readme design.

### AI generated suggestions
- At first ChatGPT generated the Entity models with only Transaction only which has the Repository , Service
  and Controller.
- AI also gave a full plan of using DTOs in this project.
- AI also gave a Transaction model with Customer ID but there is no relation between the Customer and Transaction entities.


### Changes and Corrections Made
- I did not blindly use the AI-generated plan because I thought that a transaction should not exist without an
  associated Customer entity and mapped them using a `ManytoOne` relation with a foreign key.
- ChatGPT suggested using DTOs, but I decided to use entity classes directly for this excercise because the application
  is small and I wanted to keep the implementation simple.
- I also maintained a clean seperation between the different layers of the application with Controller,Service and Repository packages.
- I added automated tests beyond the minimum four required tests to verify additional scenarios such as status updates, customer transactions, and
  customer validation.


## Verification 
I verified the final implementation through:

- Manual API testing of the implemented endpoints.
- Automated JUnit and Spring Boot tests.
- Testing validation and exception scenarios.
- Testing duplicate Transaction IDs.
- Testing transaction status updates.
- Testing customer transaction retrieval.
- Running the complete Maven test suite using:





