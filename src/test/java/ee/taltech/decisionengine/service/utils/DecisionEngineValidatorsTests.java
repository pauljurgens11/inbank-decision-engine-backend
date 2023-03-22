package ee.taltech.decisionengine.service.utils;

import ee.taltech.decisionengine.exceptions.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DecisionEngineValidatorsTests {
    @Test
    public void testValidateAllParameters_InvalidPersonalCode_ShouldThrowException() {
        DecisionEngineValidators v = new DecisionEngineValidators();
        String code0 = "abcdefghasd";
        String code1 = "4124175903";
        String code2 = "412417590312";
        String code3 = "";
        String code4 = null;

        InvalidDataException e0 = assertThrows(
                InvalidDataException.class,
                () -> v.validateAllParameters(code0, 2000, 20),
                "Expected error to be thrown."
        );
        InvalidDataException e1 = assertThrows(
                InvalidDataException.class,
                () -> v.validateAllParameters(code1, 2000, 20),
                "Expected error to be thrown."
        );
        InvalidDataException e2 = assertThrows(
                InvalidDataException.class,
                () -> v.validateAllParameters(code2, 2000, 20),
                "Expected error to be thrown."
        );
        InvalidDataException e3 = assertThrows(
                InvalidDataException.class,
                () -> v.validateAllParameters(code3, 2000, 20),
                "Expected error to be thrown."
        );
        InvalidDataException e4 = assertThrows(
                InvalidDataException.class,
                () -> v.validateAllParameters(code4, 2000, 20),
                "Expected error to be thrown."
        );

        assertEquals(e0.getReason(), InvalidDataException.Reason.PERSONAL_CODE_INVALID);
        assertEquals(e1.getReason(), InvalidDataException.Reason.PERSONAL_CODE_INVALID);
        assertEquals(e2.getReason(), InvalidDataException.Reason.PERSONAL_CODE_INVALID);
        assertEquals(e3.getReason(), InvalidDataException.Reason.PERSONAL_CODE_INVALID);
        assertEquals(e4.getReason(), InvalidDataException.Reason.PERSONAL_CODE_INVALID);
    }

    @Test
    public void testValidateAllParameters_InvalidLoanAmount_ShouldThrowException() {
        DecisionEngineValidators v = new DecisionEngineValidators();
        Integer loan0 = 10001;
        Integer loan1 = 1999;
        Integer loan2 = -50;
        Integer loan3 = 0;
        Integer loan4 = null;

        InvalidDataException e0 = assertThrows(
                InvalidDataException.class,
                () -> v.validateAllParameters("50000000000", loan0, 20),
                "Expected error to be thrown."
        );
        InvalidDataException e1 = assertThrows(
                InvalidDataException.class,
                () -> v.validateAllParameters("50000000000", loan1, 20),
                "Expected error to be thrown."
        );
        InvalidDataException e2 = assertThrows(
                InvalidDataException.class,
                () -> v.validateAllParameters("50000000000", loan2, 20),
                "Expected error to be thrown."
        );
        InvalidDataException e3 = assertThrows(
                InvalidDataException.class,
                () -> v.validateAllParameters("50000000000", loan3, 20),
                "Expected error to be thrown."
        );
        InvalidDataException e4 = assertThrows(
                InvalidDataException.class,
                () -> v.validateAllParameters("50000000000", loan4, 20),
                "Expected error to be thrown."
        );

        assertEquals(e0.getReason(), InvalidDataException.Reason.LOAN_AMOUNT_INVALID);
        assertEquals(e1.getReason(), InvalidDataException.Reason.LOAN_AMOUNT_INVALID);
        assertEquals(e2.getReason(), InvalidDataException.Reason.LOAN_AMOUNT_INVALID);
        assertEquals(e3.getReason(), InvalidDataException.Reason.LOAN_AMOUNT_INVALID);
        assertEquals(e4.getReason(), InvalidDataException.Reason.LOAN_AMOUNT_INVALID);
    }

    @Test
    public void testValidateAllParameters_InvalidLoanPeriod_ShouldThrowException() {
        DecisionEngineValidators v = new DecisionEngineValidators();
        Integer period0 = 61;
        Integer period1 = 11;
        Integer period2 = -13;
        Integer period3 = 0;
        Integer period4 = null;

        InvalidDataException e0 = assertThrows(
                InvalidDataException.class,
                () -> v.validateAllParameters("50000000000", 5000, period0),
                "Expected error to be thrown."
        );
        InvalidDataException e1 = assertThrows(
                InvalidDataException.class,
                () -> v.validateAllParameters("50000000000", 5000, period1),
                "Expected error to be thrown."
        );
        InvalidDataException e2 = assertThrows(
                InvalidDataException.class,
                () -> v.validateAllParameters("50000000000", 5000, period2),
                "Expected error to be thrown."
        );
        InvalidDataException e3 = assertThrows(
                InvalidDataException.class,
                () -> v.validateAllParameters("50000000000", 5000, period3),
                "Expected error to be thrown."
        );
        InvalidDataException e4 = assertThrows(
                InvalidDataException.class,
                () -> v.validateAllParameters("50000000000", 5000, period4),
                "Expected error to be thrown."
        );

        assertEquals(e0.getReason(), InvalidDataException.Reason.LOAN_PERIOD_INVALID);
        assertEquals(e1.getReason(), InvalidDataException.Reason.LOAN_PERIOD_INVALID);
        assertEquals(e2.getReason(), InvalidDataException.Reason.LOAN_PERIOD_INVALID);
        assertEquals(e3.getReason(), InvalidDataException.Reason.LOAN_PERIOD_INVALID);
        assertEquals(e4.getReason(), InvalidDataException.Reason.LOAN_PERIOD_INVALID);
    }

    @Test
    public void testValidateAllParameters_ValidParams_ShouldPass() throws InvalidDataException {
        DecisionEngineValidators v = new DecisionEngineValidators();

        v.validateAllParameters("12345678901", 2000, 12);
        v.validateAllParameters("00000000000", 10000, 60);
        v.validateAllParameters("90274928759", 6344, 47);
    }
}
