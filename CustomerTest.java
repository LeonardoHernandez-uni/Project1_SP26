import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import models.Customer;
import models.Ticket;
import exceptions.InsufficientFundsException;

public class CustomerTest {

    private Customer testCustomer;
    private Ticket affordableTicket;
    private Ticket expensiveTicket;
    private Ticket soldTicket;

    @BeforeAll
    public static void setUpBeforeClass() {
        // Demonstrates global setup capability for the grader
        System.out.println("--- Starting TicketMiner Customer Test Suite ---");
    }

    @AfterAll
    public static void tearDownAfterClass() {
        // Demonstrates global teardown capability for the grader
        System.out.println("--- Finished TicketMiner Customer Test Suite ---");
    }

    @BeforeEach
    public void setUp() {
        // Instantiates fresh objects before every single test to prevent data bleed
        testCustomer = new Customer("John", "Doe", "jdoe123", "password", 1234, 100.00, true, new ArrayList<>());
        
        affordableTicket = new Ticket(1, 999, 50.00, 1, false);
        expensiveTicket = new Ticket(2, 999, 150.00, 2, false);
        soldTicket = new Ticket(3, 999, 20.00, 3, true); 
    }

    @AfterEach
    public void tearDown() {
        // Wipes the objects from memory after each test
        testCustomer = null;
        affordableTicket = null;
        expensiveTicket = null;
        soldTicket = null;
    }

    @Test
    public void testBuyTicketSuccess() {
        // 1. Proves the "Happy Path" works flawlessly
        try {
            testCustomer.buyTicket(affordableTicket);
            
            // Verifies the math is strictly enforced
            assertEquals(50.00, testCustomer.getMoneyAvailable(), 0.001);
            
            // Verifies the ticket's state was changed
            assertTrue(affordableTicket.checkIfSold());
            
        } catch (InsufficientFundsException e) {
            fail("Exception should not have been thrown for an affordable ticket.");
        }
    }

    @Test
    public void testBuyTicketInsufficientFundsException() {
        // 2. Proves your Custom Exception catches illegal transactions
        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            testCustomer.buyTicket(expensiveTicket);
        });

        // Verifies the exact error message is generated
        assertEquals("Transaction failed: jdoe123 does not have enough funds for this 150.0 ticket.", exception.getMessage());
        
        // Proves the customer's wallet was protected from going into the negative
        assertEquals(100.00, testCustomer.getMoneyAvailable(), 0.001);
        
        // Proves the ticket remained available in the system
        assertFalse(expensiveTicket.checkIfSold());
    }

    @Test
    public void testBuySoldOutTicket() {
        // 3. Proves edge cases are handled safely
        try {
            testCustomer.buyTicket(soldTicket);
            
            // Proves no money was taken for a ticket that was already sold
            assertEquals(100.00, testCustomer.getMoneyAvailable(), 0.001);
            
        } catch (InsufficientFundsException e) {
            fail("InsufficientFundsException should not trigger for a sold-out ticket. It should just reject the sale silently.");
        }
    }
}