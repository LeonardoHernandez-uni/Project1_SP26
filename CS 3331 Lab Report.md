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

What did you do in this program?

What was your approach to solving this problem?

What data structures did you use? Why?

What assumptions, if any, did you make?

## Testing

How did you test the program?

Did you use black-box, white-box testing, or both? Why?

Did you test the solution enough? How can the testing practices be improved?

What are the test cases I used?

Did you break the program and use that to improve it?

## Test results

Describe the results of your tests.

Include any console outputs showing your results.

Include any text document results of your tests.

## Code Review

Explain how you conducted a review of your code. Describe how you checked each part of the code review checklist.



(Note: Turn in all source code, output results (if applicable), reports, and all other required material specified by the assignment). Save this lab report at Team\#\_PA\## – Do not turn in report with notes.)
