package ee.taltech.decisionengine.exceptions;

/**
 * Exception that occurs when a received variable is in an incorrect format or not in the correct range.
 */
public class InvalidDataException extends Throwable {
    private final Reason reason;
    public enum Reason {
        PERSONAL_CODE_INVALID,
        LOAN_AMOUNT_INVALID,
        LOAN_PERIOD_INVALID,
        DATA_INVALID
    }

    public InvalidDataException(Reason reason) {
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
        if (reason.equals(Reason.PERSONAL_CODE_INVALID)) {
            return "Invalid personal code. Must consist of 11 digits.";
        } else if (reason.equals(Reason.LOAN_AMOUNT_INVALID)) {
            return "Invalid loan amount. Must be between 2000 and 10000 euros.";
        } else if (reason.equals(Reason.LOAN_PERIOD_INVALID)) {
            return "Invalid loan period. Must be between 12 and 60 months.";
        } else {
            return "Some of the data was invalid.";
        }
    }

    public Reason getReason() {
        return reason;
    }
}
