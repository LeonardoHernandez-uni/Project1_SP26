package exceptions;

/**
 * Custom exception thrown when a customer attempts to purchase a ticket
 * without enough available funds in their account.
 */
public class InsufficientFundsException extends Exception {
    
    public InsufficientFundsException(String message) {
        super(message);
    }
}
