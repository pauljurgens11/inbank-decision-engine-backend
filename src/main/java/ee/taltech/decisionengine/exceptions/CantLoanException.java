package ee.taltech.decisionengine.exceptions;

/**
 * Exception that occurs when a loan can not be given.
 */
public class CantLoanException extends Throwable {
    private final Reason reason;
    public enum Reason {
        CLIENT_IN_DEBT,
        NO_POSSIBLE_LOANS,
        UNKNOWN_CREDIT_MODIFIER
    }
    public CantLoanException(Reason reason) {
        this.reason = reason;
    }

    /**
     * Get a message describing what happened to the client.
     * <p>
     * The message is decided by the reason why the exception was thrown.
     * @return a string that describes what went wrong
     */
    @Override
    public String getMessage() {
        if (reason.equals(Reason.CLIENT_IN_DEBT)) {
            return "Client is in debt! Can't loan any money :(";
        } else if (reason.equals(Reason.NO_POSSIBLE_LOANS)) {
            return "No loans are currently available for you. Try again with different values.";
        } else if (reason.equals(Reason.UNKNOWN_CREDIT_MODIFIER)) {
            return "Your personal code's credit modifier value is unknown (more info in repository readme).";
        } else {
            return "Loan could not be given.";
        }
    }

    public Reason getReason() {
        return reason;
    }
}
