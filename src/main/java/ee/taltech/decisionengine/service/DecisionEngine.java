package ee.taltech.decisionengine.service;

import ee.taltech.decisionengine.exceptions.CantLoanException;
import ee.taltech.decisionengine.exceptions.InvalidDataException;
import ee.taltech.decisionengine.service.utils.DecisionEngineValidators;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Main class for the service side of the decision engine.
 */
@Service
@Getter
public class DecisionEngine {

    /**
     * Get decision whether the client can loan money or not.
     * <p>
     * Throws an exception if loan can not be requested, otherwise returns the appropriate loan amount and period.
     * @param personalCode client's personal code
     * @param loanAmount loan amount in euros
     * @param loanPeriod loan period in months
     * @return calculated loan amount and period in an array
     * @throws InvalidDataException gets thrown when one (or more) of the given variables are not valid
     * @throws CantLoanException gets thrown when a loan cannot be given
     */
    public Integer[] getDecision(String personalCode, Integer loanAmount, Integer loanPeriod)
            throws CantLoanException, InvalidDataException {
        
        DecisionEngineValidators validators = new DecisionEngineValidators();
        validators.validateAllParameters(personalCode, loanAmount, loanPeriod);

        Optional<Integer> creditModifier = getCreditModifier(personalCode);
        if (creditModifier.isEmpty()) {
            throw new CantLoanException(CantLoanException.Reason.UNKNOWN_CREDIT_MODIFIER);
        } else if (creditModifier.get() == 0) {
            throw new CantLoanException(CantLoanException.Reason.CLIENT_IN_DEBT);
        }

        LoanCalculator loanCalculator = new LoanCalculator(loanAmount, loanPeriod, creditModifier.get());
        return loanCalculator.getLoanResult();
    }

    /**
     * Get the client's credit modifier.
     * <p>
     * Currently, the method for determining credit modifiers is as follows:
     * We take the last 3 digits of a client's personal code.
     * A value between and including 000 and 199 is considered an unknown credit modifier.
     * A value between and including 200 and 399 is a credit modifier of 0 (client in debt).
     * A value between and including 400 and 599 is a credit modifier of 100.
     * A value between and including 600 and 799 is a credit modifier of 300.
     * A value between and including 800 and 999 is a credit modifier of 1000.
     * @param personalCode client's personal code
     * @return an optional potentially containing the client's credit modifier
     */
    private Optional<Integer> getCreditModifier(String personalCode) {
        final String lastThreeDigitsString = personalCode.substring(personalCode.length() - 3);
        final int lastThreeDigits = Integer.parseInt(lastThreeDigitsString);

        if (200 <= lastThreeDigits && lastThreeDigits < 400) {
            return Optional.of(0);
        } else if (400 <= lastThreeDigits && lastThreeDigits < 600) {
            return Optional.of(100);
        } else if (600 <= lastThreeDigits && lastThreeDigits < 800) {
            return Optional.of(300);
        } else if (800 <= lastThreeDigits) {
            return Optional.of(1000);
        }

        return Optional.empty();
    }
}
