## Part II: Architectural Design & Implementation

### 1. The Singleton Design Pattern
A major refactoring effort in this phase was transitioning our core management classes (`UserManager`, `EventManager`, and `VenueManager`) to utilize the Singleton design pattern. 

* **The Problem:** In previous iterations, manager objects could be instantiated multiple times across different menus, risking desynchronization of the data state and creating duplicate file-saving errors.
* **The Solution:** We locked down the architecture by making the constructors `private` and exposing a single, static `getInstance()` method. This guarantees that the entire application operates on a single, globally accessible instance of each manager. Whether the user is in the `MenuHandler` or a background processing task, they are modifying the exact same `ArrayList` of data.

### 2. Interface-Driven Data Exportation
To maintain organizational clarity and adhere to SOLID principles, we implemented a dedicated `Interface` package to handle data persistence.

* **The `Exportable` Interface:** We created an `Exportable` interface that dictates a single contract: any class implementing it must define a `toCSVString()` method. 
* **Implementation:** Our core abstract models (`User`, `Event`, and `Venue`) all implement this interface. This offloads the responsibility of string formatting from the Manager classes to the models themselves. By utilizing a dedicated interface package, we decoupled the saving logic from the business logic. If a new model is introduced in the future, it simply implements `Exportable` to instantly support file saving.

### 3. Exception Handling & Separation of Concerns
To handle transactional errors gracefully, we developed a custom `InsufficientFundsException` that extends Java's base `Exception` class.

* **Implementation Details:** The exception is thrown exclusively by the `buyTicket()` method within the `Customer` model when a ticket's price exceeds the customer's `moneyAvailable`. 
* **Explenation:** This approach strictly enforces the separation of concerns. The model is solely responsible for enforcing business rules (protecting the wallet balance), while the UI layer (`MenuHandler`) is responsible for catching the exception and displaying a user-friendly error message. This prevents the program from crashing while avoiding the anti-pattern of placing `System.out.println` statements directly inside core data models.

***

## Part III: Testing & Assumptions

### 1. Assumptions Made
* **Assumption 1:** It is assumed that user inputs for numerical values (like money or ticket prices) will be parsed correctly from the scanner, though the custom exception handles the specific business logic of insufficient funds.
* **Assumption 2:** We assumed the "Electronic Order Summary" text file should be generated locally in the project root directory rather than asking the user to manually input a specific file path. 

### 2. JUnit Testing
Per the project requirements, a JUnit test suite was developed to test the functionality and robustness of the `Customer` class's `buyTicket()` method.

* **Approach: White-Box Testing**
We came up with the JUnit test cases using a **White-Box** approach. Because we actively wrote and reviewed the internal logic of the `buyTicket()` method—specifically the `if`-statements checking for ticket availability and sufficient funds—we designed our tests to explicitly hit every single internal branch and exception trigger within that method.

* **Test Cases**
To test the correctness of the chosen functions, we considered and implemented three distinct test cases:
    * **`testBuyTicketSuccess` (The Happy Path):** Tests a standard transaction where the user has sufficient funds and the ticket is available. We verify that the exact ticket price is deducted from the wallet and the ticket is flagged as sold.
    * **`testBuyTicketInsufficientFundsException` (The Exception Path):** Tests the system's reaction when a customer attempts to buy a ticket that costs more than their available balance. We verify that our custom user-defined exception is thrown and that no money is incorrectly deducted.
    * **`testBuySoldOutTicket` (The Edge Case):** Tests the system's guardrails by attempting to purchase a ticket that is already flagged as "sold," verifying that the transaction is rejected safely.

* **Justification of Sufficiency**
We believe these test cases are enough to test the correctness of the function because they achieve 100% branch coverage for the `buyTicket()` method. By testing the successful execution, the anticipated failure state (insufficient funds), and an edge-case failure state (sold-out ticket), we have mathematically proven that the model protects the user's data integrity under all possible transaction circumstances.


### Code Review

**Explain how you conducted a review of your code. Describe how you checked each part of the code review checklist.**
We conducted a systematic code review using the provided rubric checklist, evaluating the project across Implementation, Logic, Readability/Style, and Performance. We verified that our inheritance structures, data structures, and algorithms met the project specifications.

**Implementation:** The code successfully implements the core TicketMiner requirements, utilizing a clear Object-Oriented design with Abstract classes (`Venue`, `User`, `Event`) and specific concrete classes (`Stadium`, `Concert`, `Customer`). We utilized the Singleton design pattern for our manager classes (`EventManager`, `VenueManager`, `UserManager`) to centralize data access. One area that required dynamic implementation was linking Events to Venues; since the event CSV lacked explicit venue IDs, we implemented a keyword-matching algorithm to dynamically assign locations (e.g., matching "football" to the Sun Bowl). 

**Logic:** During the review, we identified and resolved several critical logic errors regarding capacity and ticket pools. Initially, events were generating hardcoded ticket amounts and stacking them improperly. We refactored the `Event` class logic to dynamically clear and rebuild ticket pools based strictly on the percentage breakdowns (VIP, Gold, Silver, Bronze, GA) dictated by the assigned `Venue`. We also integrated a custom `InsufficientFundsException` to properly handle and reject customer transactions when they cannot afford the ticket, calculating the final price using the required 8.25% tax and 10% member discounts.

**Readability/Style:** The code follows standard industry Java naming conventions (camelCase for variables, PascalCase for classes). We implemented comprehensive Javadoc comments across all methods to ensure maintainability. The package structure effectively separates concerns into `models`, `logic`, `ui`, and `exceptions`. However, similar to previous iterations, the `MenuHandler` remains a heavy, monolithic class that handles the UI for Customers, Organizers, and Admins. If there were to be future iteration, this should be decoupled into specific UI handler classes for each user type. 

**Performance:** Most operations currently run at O(n) time complexity. Our search functions within the Manager classes utilize linear searches through `ArrayList` data structures to find specific IDs or names. Similarly, reserving a ticket requires an O(n) traversal of the event's ticket pool to find an unsold seat matching the requested price tier. While sufficient for the current scale, migrating the primary data structures from `ArrayList` to `HashMap` (using IDs as keys) would optimize search and retrieval operations to O(1) complexity as the dataset grows.

**Rewrites to Make Code Acceptable:** We refactored as much as possible before the due date and with busy schedules.
