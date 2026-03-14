Leonardo Hernandez, Kevin Pinon 
March 14, 2026
CS 3331 – Advanced Object-Oriented Programming – Term (Spring) 2026
Professor Bhanukiran Gurijala
Project Part 1


This work was done as a team and completely on our own. We did not share, reproduce, or alter any part of this assignment for any purpose. We did not share code, upload this assignment online in any form, or view/receive/modify code written by anyone else. All deliverables were produced entirely on our own. This assignment is part of an academic course at The University of Texas at El Paso, and a grade will be assigned for the work I/we produced.


### Program Explanation

In Project Part 1, we were given a technical document describing the specifications and requirements of a ticket system called "TicketMiner". Our assignment was to translate this technical document into a class diagram and use case diagram which we could then use to create our own program in Java. In order to tackle the assignment, one teammate did the class diagram based off the specifications and the attributes given by the example csv files provided, and the other created use case diagrams based off their idea of how the system might be used. Along with the use case diagrams, said teammate also worked on a few scenarios to describe a hypothetical step-by-step on performing the actions described in the use case diagram.

After this, we began implementing abstract classes User, Event, and Venue, then began constructing subclasses that construct themselves by instantiating these abstract classes with super(). To store all of these classes, we created global ArrayLists and as we went along, we developed methods for manipulating the entries in these ArrayLists.  At the same time, we worked on the our original TUI (terminal user interface) and developed the options as described by the technical document.

At some point early on, to make compiling easier, the program was separated into packages.
- logic: Holds manager classes that contain methods for manipulating the ArrayLists containing User, Venue, and Event objects and their subclasses.
- models: Holds all User, Venue, and Event objects and their subclasses.
- ui: Holds our main and menu handler classes.

The ui package is notable for managing program startup methods, our TUI, and our shutdown methods. While we initially developed our TUI to run in RunTicketMiner, it was decided it'd be best to move it into its own class called menuHandler to contain our numerous menu options. RunTicketMiner, in turn, then became the method we used to call our mainMenu() method along with our loadData and saveData methods. From our mainMenu() method, every other TUI method is accessible depending on what you log in as (for example, logging in as an admin will direct you to an admin menu with options for adding, searching for, updating, and deleting users, events, or venues.

Speaking of our loadData and saveData methods, our loadData methods take a csv file and parse their information into our array lists. After our main menu is exited out of via our EXIT command, our saveData methods are called to create new csv files labeled with the current date. 

As part of our specifications, RunTicketMiner also has a ArrayList in charge of documenting every action the user performs. From logging into to creating a new account, this ArrayList documents it all then creates txt file on exit.

In general, we did quite a lot, but to solve all our problems, we broke developement down into steps, created reuseable elements where we could (for example, we copy and pasted our first implementation of loadData from User to Event and Venue managers and edited them to work there), and grouped a lot of our code together to keep track of it all while adhering to the technical document for our design and implementation.

## What did I learn?

In general, I (Leonardo), feel we learned a lot about early planning in general.

In its current state, our class diagram is almost unrecognizeable in terms of comparing it to our code. Many changes and additions had to be done to our code in order to accomodate our goals, and with those changes, we strayed further and further from our original design. I believe at a point, the class diagram could have been updated to better reflect the code implemented from it, but because we didn't act on it earlier, it became more of a burden to update the farther we got into our code. I also believe not having everything we needed to implement on it also led to some backtracking as we realized some classes were missing attributes and, in some cases, methods necessary to keeping with specifications.

It took us two weeks to finish this project, and we still didn't make it to due date, but I believe with a stronger idea of what we were trying to accomplish, we absolutely could have.

## Solution Design

1. What did you do in this program?
Our program implements a ticket management system with three primary entity types: Users (Customers, Organizers, and Admins), Events (Concerts, Sports, and Special events), and Venues (Arenas, Stadiums, Auditoriums, and OpenAir venues). The system allows users to login, organizers to manage events, and administrators to oversee the entire system at this current moment.
  
2. What was your approach to solving this problem?
Our approach to solving this problem was to use object-oriented design principles, particularly inheritance and abstraction. We created abstract base classes (User, Event, Venue) that define common attributes and behaviors, then extended them with specific subclasses that implement unique features. For instance, the Customer class extends User and adds fields like moneyAvailable and hasMembership, while the Admin class extends User and includes methods for managing all system entities.

3. What data structures did you use? Why?
The primary data structure we used is ArrayList, which stores collections of Users, Events, and Venues. We chose ArrayLists because they provide dynamic sizing and easy iteration. Each entity type has its own ArrayList managed by dedicated manager classes (UserManager, EventManager, VenueManager). These manager classes were used to manipulate the data and provide methods like generateID(), searchMember(), and loadData().

4. What assumptions, if any, did you make?
We assumed that venue capacity percentages (VIP, Gold, Silver, Bronze, General Admission) should total 100%, though we did not enforce this. Second, we assumed that CSV files would follow a consistent format with specific column orders. Finally, we assumed that the system would run on a single machine without concurrent access, so we did not implement safety or database transactions.

## Testing

1. How did you test the program?
We tested the program using a combination of black-box and white-box testing. Black-box testing was used to verify that user-facing features worked as expected from an end-user perspectiv. White-box testing was employed to examine internal logic, such as verifying that ID generation produced unique values.

2. Did you use black-box, white-box testing, or both? Why?
We primarily used manual testing through the terminal user interface, entering various inputs to observe system behavior. For example, we tested input validation by entering non-numeric values where numbers were expected (which gave the NumberFormatException bug in the manageEvents method).

3. Did you test the solution enough? How can the testing practices be improved?
We did not test the solution enough. Our testing was primarily manual, conducted through the terminal. We discovered critical bugs like the NumberFormatException. Testing practices could be improved by implementing automated unit tests using JUnit to test individual methods, creating a comprehensive test with documented test cases covering both valid and invalid inputs, performing edge testing systematically, and establishing regression testing to ensure bug fixes don't break existing functionality. Additionally, we should test edge cases more thoroughly, such as empty CSV files, and special characters in input fields.

5. What are the test cases I used?
1. User Registration: Creating customer and organizer accounts with various input combinations, including edge cases like empty passwords and special characters in usernames.
2. Login Validation: Testing correct credentials, incorrect passwords, non-existent usernames, and case sensitivity.
3. Admin Operations: Adding, searching, updating, and deleting users, venues, and events through the admin menu. Testing that deletion properly removed entries and that updates persisted correctly.
4. Input Validation: Entering invalid data types, empty inputs, and boundary values.
5. Time Parsing: Testing the event time input with various formats like "10:30 PM", "7:00 AM", and invalid formats.
6. CSV Persistence: Verifying that data saved to CSV files on exit and loaded correctly on restart, maintaining data across sessions.

5. Did you break the program and use that to improve it?
Yes, we did break the program during testing, and this proved valuable for improvement. The most significant bug we discovered was the NumberFormatException in the manageEvents() method at line 466. When prompted for time input, if a user entered non-numeric text (like "asdfasdf"), the program crashed because the next line attempted to parse VIP price without first prompting the user. This revealed that we were missing a System.out.print("VIP Price: ") statement before the parseDouble() call. We fixed this by adding the missing prompt and implemented try-catch blocks around numeric parsing to provide better error messages and recovery.

## Test results

1. Describe the results of your tests.
Our testing revealed both successful functionality and areas requiring improvement. The majority of core features worked as intended, including user registration, login authentication, admin CRUD operations, and CSV data persistence.
Include any console outputs showing your results.

2. Include any text document results of your tests.
Failed Test Result - NumberFormatException:
When testing event creation in the admin menu, we encountered the following error:
Time (e.g. 7:30 PM): 10:30 PM asdfasdf Exception in thread "main" java.lang.NumberFormatException: For input string: "asdfasdf"         at java.base/jdk.internal.math.FloatingDecimal.readJavaFormatString(FloatingDecimal.java:2054)         at java.base/jdk.internal.math.FloatingDecimal.parseDouble(FloatingDecimal.java:110)         at java.base/java.lang.Double.parseDouble(Double.java:792)         at ui.MenuHandler.manageEvents(MenuHandler.java:466)

This error occurred because the code at line 466 attempted to parse the VIP price without first displaying a prompt to the user. The user, not knowing what input was expected, entered "asdfasdf", which could not be parsed as a double. The fix was to add System.out.print("VIP Price: "); before the Double.parseDouble() call.

## Code Review

Explain how you conducted a review of your code. Describe how you checked each part of the code review checklist.

We did a systematic code review using the provided checklist, examining Implementation, Logic, Readability/Style, and Performance.

Implementation:
The code successfully implements core TicketMiner requirements. Several areas could be simplified, particularly the repetitive CSV parsing logic across manager classes and deeply nested switch statements in MenuHandler. While mostly dynamic, venue and event types are hardcoded strings.

Logic:
We identified several logic issues: the manageEvents() method crashes with NumberFormatException due to missing input prompts and invalid menu inputs don't re-display options. The code fails when users enter non-numeric input without try-catch protection, invalid time formats, negative values, capacity percentages not summing to 100%, or when CSV files are missing.

Readability/Style:
Code readability is moderate with descriptive variable names but suffers from deeply nested logic and long methods. The package structure has good separation, but MenuHandler is a everything class that should be split into AdminMenu, CustomerMenu, and OrganizerMenu classes. The code follows Java naming conventions. Javadoc was implemented to increase maintainability and readability

Performance:
Most operations run at O(n) complexity: login and search use linear searches through ArrayLists, add operations are O(1), delete is O(n), and ID generation is O(n). As data grows, performance degrades linearly.

Rewrites to Make Code Acceptable:
We rewrote and improved the code as much as possible before the due date.



(Note: Turn in all source code, output results (if applicable), reports, and all other required material specified by the assignment). Save this lab report at Team\#\_PA\## – Do not turn in report with notes.)
