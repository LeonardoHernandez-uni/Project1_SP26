# Scenario 1: Purchase Tickets

## Actor:
Customer or TicketMiner Member

## Preconditions:
The user is logged in as a Customer and events are loaded into the system.

## Main Flow:
1. The Customer selects "Purchase Tickets" from the main menu.
2. The System displays all available events.
3. The customer searches for and then selects the event.
4. The Customer enters the quantity and type of tickets (e.g., VIP, Gold).
5. The System verifies ticket availability and existing customer funds.
6. Steps 3–5 are repeated for additional events.
7. The Customer confirms they are ready to checkout.

## Postconditions:
Customer balance is updated, event ticket capacity is reduced, and a confirmation number is generated.

## Alternative Flow fro TicketMiner Members:
If the actor is a "TicketMiner member" the System applies a 10% discount to the subtotal before adding the 8.25% sales tax.

## Exception Flow
If the Customer has insufficient funds or tickets are sold out, the System displays an error message and returns to the event selection menu.

-----------------------------------------------------------------------------------------------------------------------------------------------

# Scenario 2: Generate Event Report

## Actor:
Organizer

## Preconditions:
The user is logged in as and Organizer and events exist in the system.

## Main Flow:
1. The Organizer selects "Generate Event Report" from the menu.
2. The System prompts the Organizer to enter the Event ID, Name, or Date.
3. The Organizer enters the search criteria (e.g., Event ID "101").
4. The System searches the event list for a match.
5. The System calculates total seats sold per tier and corresponding revenue.
6. The System calculates the "Expected profit" and "Actual profit".
7. The System displays the full report on the console.

## Postconditions:
The system displays the report and returns to the Organizer menu.

## Exception Flow:
if the event is not found, the System displays an informative message stating the event does not exist and returns to the menu.
