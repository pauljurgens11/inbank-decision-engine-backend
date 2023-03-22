package ee.taltech.decisionengine.service;

import ee.taltech.decisionengine.exceptions.CantLoanException;
import ee.taltech.decisionengine.exceptions.InvalidDataException;
import ee.taltech.decisionengine.service.utils.DecisionEngineConstants;
import lombok.AllArgsConstructor;

/**
 * Calculate a suitable loan for the client (if possible).
 */
@AllArgsConstructor
public class LoanCalculator {
    private Integer loanAmount;
    private Integer loanPeriod;
    private final Integer creditModifier;

    /**
     * Get the loan amount and period of a loan that can be given to a specific client (if possible).
     * <p>
     * @return array containing a loan amount and period that can be given to the client
     * @throws InvalidDataException gets thrown when one (or more) of the given variables are not valid
     * @throws CantLoanException gets thrown when a loan cannot be given
     */
    public Integer[] getLoanResult() throws CantLoanException, InvalidDataException {
        if (loanAmount == null || loanPeriod == null || creditModifier == null) {
            throw new InvalidDataException(InvalidDataException.Reason.DATA_INVALID);
        }

        final int lowestPeriod = getLowestValidPeriod();
        final int highestLoan = getHighestValidLoan();

        if (getCreditScore() >= 1) {
            return new Integer[] {loanAmount, loanPeriod};

        } else if (highestLoan >= DecisionEngineConstants.minimumLoanAmount
                && highestLoan <= DecisionEngineConstants.maximumLoanAmount) {
            return new Integer[] {getHighestValidLoan(), loanPeriod};

        } else if (lowestPeriod >= DecisionEngineConstants.minimumLoanPeriod
                && lowestPeriod <= DecisionEngineConstants.maximumLoanPeriod) {
            return new Integer[] {2000, getLowestValidPeriod()};

        } else {
            throw new CantLoanException(CantLoanException.Reason.NO_POSSIBLE_LOANS);
        }
    }

    /**
     * Calculate the credit score of a person.
     * <p>
     * The formula for this calculation is as follows: (creditModifier / loanAmount) * loanPeriod
     * @return result of the calculation
     * @throws InvalidDataException gets thrown when one of the given variables is not valid
     */
    private float getCreditScore() throws InvalidDataException {
        if (loanAmount < 2000 || loanAmount > 10000) {
            throw new InvalidDataException(InvalidDataException.Reason.LOAN_AMOUNT_INVALID);
        }
        return (float) ((creditModifier * loanPeriod)) / loanAmount;
    }

    /**
     * Calculate the lowest valid loan period that can be offered for the client.
     * @return result of the calculation
     * @throws CantLoanException thrown when the credit modifier is less than or equal to 0
     */
    private int getLowestValidPeriod() throws CantLoanException {
        if (creditModifier <= 0) {
            throw new CantLoanException(CantLoanException.Reason.CLIENT_IN_DEBT);
        }
        return (int) Math.ceil((float) DecisionEngineConstants.minimumLoanAmount / creditModifier);
    }

    /**
     * Calculate the highest valid loan amount that can be offered for the client.
     * @return result of the calculation
     * @throws InvalidDataException thrown when the loan period is not in the correct range
     */
    private int getHighestValidLoan() throws InvalidDataException {
        if (loanPeriod < 12 || loanPeriod > 60) {
            throw new InvalidDataException(InvalidDataException.Reason.LOAN_PERIOD_INVALID);
        }
        return (int) Math.floor(creditModifier * loanPeriod);
    }
}
