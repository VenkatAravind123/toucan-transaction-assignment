
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
- I did not blindly use the AI-generated plan because I thought that a transaction cannot exist without a Customer
So I added a Customer entity and mapped them using foreign key (ManytoOne relation).
- ChatGPT gave the implementation with DTOs but I made proper preperations to implement without DTOs as it is complex.
- I thought that DTOs are used in a REST API context to decouple the internal representation from the client-facing API so 
I refrained from using that.
- I also maintained a clean codebase with Controller,Service and Repository packages.



