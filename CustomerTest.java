import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void setUp() {
        // Initialize a customer with exactly $100.00 for testing
        testCustomer = new Customer("John", "Doe", "jdoe123", "password", 1234, 100.00, true, new ArrayList<>());
        
        affordableTicket = new Ticket(1, 999, 50.00, 1, false);
        expensiveTicket = new Ticket(2, 999, 150.00, 2, false);
        soldTicket = new Ticket(3, 999, 20.00, 3, true); 
    }

    // ==========================================
    // REAL TESTS (These should PASS)
    // ==========================================

    @Test
    public void testBuyTicketSuccess() {
        try {
            testCustomer.buyTicket(affordableTicket);
            assertEquals(50.00, testCustomer.getMoneyAvailable(), 0.001);
            assertTrue(affordableTicket.checkIfSold());
        } catch (InsufficientFundsException e) {
            fail("Exception should not have been thrown for an affordable ticket.");
        }
    }

    @Test
    public void testBuyTicketInsufficientFundsException() {
        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            testCustomer.buyTicket(expensiveTicket);
        });

        assertEquals("Transaction failed: jdoe123 does not have enough funds for this 150.0 ticket.", exception.getMessage());
        assertEquals(100.00, testCustomer.getMoneyAvailable(), 0.001);
        assertFalse(expensiveTicket.checkIfSold());
    }

    @Test
    public void testBuySoldOutTicket() {
        try {
            testCustomer.buyTicket(soldTicket);
            assertEquals(100.00, testCustomer.getMoneyAvailable(), 0.001);
        } catch (InsufficientFundsException e) {
            fail("InsufficientFundsException should not trigger for a sold-out ticket.");
        }
    }

    // ==========================================
    // INTENTIONAL FAILURES (These should FAIL)
    // ==========================================

    @Test
    public void testIntentionalMathFailure() {
        try {
            testCustomer.buyTicket(affordableTicket);
            
            // INTENTIONAL ERROR: The math is $100 - $50 = $50. 
            // We are telling JUnit to expect $999 instead.
            assertEquals(999.00, testCustomer.getMoneyAvailable(), 0.001, "INTENTIONAL FAILURE: Math expectation is wrong.");
            
        } catch (InsufficientFundsException e) {
            fail("Exception should not have been thrown.");
        }
    }

    @Test
    public void testIntentionalBooleanFailure() {
        try {
            testCustomer.buyTicket(affordableTicket);
            
            // INTENTIONAL ERROR: The ticket was just bought, so checkIfSold() is TRUE.
            // We are asserting that it is FALSE.
            assertFalse(affordableTicket.checkIfSold(), "INTENTIONAL FAILURE: Expected ticket to be unsold, but it was sold.");
            
        } catch (InsufficientFundsException e) {
            fail("Exception should not have been thrown.");
        }
    }
}