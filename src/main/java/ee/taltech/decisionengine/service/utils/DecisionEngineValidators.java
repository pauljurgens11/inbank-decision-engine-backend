package ee.taltech.decisionengine.service.utils;

import ee.taltech.decisionengine.exceptions.InvalidDataException;

/**
 * Validate the variables that are received via a POST request.
 */
public class DecisionEngineValidators {

    /**
     * Public method to validate all parameters received from a POST request.
     * <p>
     * @param personalCode a string representing a client's personal code
     * @param loanAmount amount that a client wants to loan (in euros)
     * @param loanPeriod period for the loan (in months)
     * @throws InvalidDataException throws exception when a variable is not valid according to our rules
     */
    public void validateAllParameters(String personalCode, Integer loanAmount, Integer loanPeriod) throws InvalidDataException {
        personalCodeValidator(personalCode);
        loanAmountValidator(loanAmount);
        loanPeriodValidator(loanPeriod);
    }

    /**
     * Validate a client's personal code.
     * <p>
     * The code must be 11 digits long and numeric.
     * @param code personal code
     * @throws InvalidDataException throws exception when personal code is not valid
     */
    private void personalCodeValidator(String code) throws InvalidDataException {
        final int personalCodeLength = 11;

        if (code == null) {
            throw new InvalidDataException(InvalidDataException.Reason.PERSONAL_CODE_INVALID);
        } else if (code.length() != personalCodeLength) {
            throw new InvalidDataException(InvalidDataException.Reason.PERSONAL_CODE_INVALID);
        }

        try {
            Double.parseDouble(code);
        } catch(NumberFormatException e){
            throw new InvalidDataException(InvalidDataException.Reason.PERSONAL_CODE_INVALID);
        }
    }

    /**
     * Validate loan amount.
     * <p>
     * Must be numeric and between 2000 and 10000 euros.
     * @param amount loan amount
     * @throws InvalidDataException throws exception when loan amount is not valid
     */
    private void loanAmountValidator(Integer amount) throws InvalidDataException {
        final int minimumLoanAmount = 2000;
        final int maximumLoanAmount = 10000;

        if (amount == null) {
            throw new InvalidDataException(InvalidDataException.Reason.LOAN_AMOUNT_INVALID);
        } else if (minimumLoanAmount > amount || amount > maximumLoanAmount) {
            throw new InvalidDataException(InvalidDataException.Reason.LOAN_AMOUNT_INVALID);
        }
    }

    /**
     * Validate loan period.
     * <p>
     * Must be numeric and between 12 and 60 months.
     * @param period loan period
     * @throws InvalidDataException throws exception when loan period is not valid
     */
    private void loanPeriodValidator(Integer period) throws InvalidDataException {
        final int minimumLoanPeriod = 12;
        final int maximumLoanPeriod = 60;

        if (period == null) {
            throw new InvalidDataException(InvalidDataException.Reason.LOAN_PERIOD_INVALID);
        } else if (minimumLoanPeriod > period || period > maximumLoanPeriod) {
            throw new InvalidDataException(InvalidDataException.Reason.LOAN_PERIOD_INVALID);
        }
    }
}
