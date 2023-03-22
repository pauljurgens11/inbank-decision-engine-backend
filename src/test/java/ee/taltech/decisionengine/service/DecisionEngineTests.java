package ee.taltech.decisionengine.service;

import ee.taltech.decisionengine.exceptions.CantLoanException;
import ee.taltech.decisionengine.exceptions.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DecisionEngineTests {
    @Test
    public void testGetDecision_InvalidPersonalCode_ReturnEmptyOptional() {
        DecisionEngine de = new DecisionEngine();
        String code0 = null;
        String code1 = "";
        String code2 = "abcdefasdas";
        String code3 = "6432784623";
        String code4 = "635632465745";

        InvalidDataException e0 = assertThrows(
                InvalidDataException.class,
                () -> de.getDecision(code0, 2000, 20),
                "Expected error to be thrown."
        );
        InvalidDataException e1 = assertThrows(
                InvalidDataException.class,
                () -> de.getDecision(code1, 2000, 20),
                "Expected error to be thrown."
        );
        InvalidDataException e2 = assertThrows(
                InvalidDataException.class,
                () -> de.getDecision(code2, 2000, 20),
                "Expected error to be thrown."
        );
        InvalidDataException e3 = assertThrows(
                InvalidDataException.class,
                () -> de.getDecision(code3, 2000, 20),
                "Expected error to be thrown."
        );
        InvalidDataException e4 = assertThrows(
                InvalidDataException.class,
                () -> de.getDecision(code4, 2000, 20),
                "Expected error to be thrown."
        );

        assertEquals(e0.getReason(), InvalidDataException.Reason.PERSONAL_CODE_INVALID);
        assertEquals(e1.getReason(), InvalidDataException.Reason.PERSONAL_CODE_INVALID);
        assertEquals(e2.getReason(), InvalidDataException.Reason.PERSONAL_CODE_INVALID);
        assertEquals(e3.getReason(), InvalidDataException.Reason.PERSONAL_CODE_INVALID);
        assertEquals(e4.getReason(), InvalidDataException.Reason.PERSONAL_CODE_INVALID);
    }

    @Test
    public void testGetDecision_InvalidLoanAmount_ReturnEmptyOptional() {
        DecisionEngine de = new DecisionEngine();
        Integer loan0 = null;
        Integer loan1 = 0;
        Integer loan2 = 1999;
        Integer loan3 = 10001;
        Integer loan4 = -2000;

        InvalidDataException e0 = assertThrows(
                InvalidDataException.class,
                () -> de.getDecision("50000000000", loan0, 20),
                "Expected error to be thrown."
        );
        InvalidDataException e1 = assertThrows(
                InvalidDataException.class,
                () -> de.getDecision("50000000000", loan1, 20),
                "Expected error to be thrown."
        );
        InvalidDataException e2 = assertThrows(
                InvalidDataException.class,
                () -> de.getDecision("50000000000", loan2, 20),
                "Expected error to be thrown."
        );
        InvalidDataException e3 = assertThrows(
                InvalidDataException.class,
                () -> de.getDecision("50000000000", loan3, 20),
                "Expected error to be thrown."
        );
        InvalidDataException e4 = assertThrows(
                InvalidDataException.class,
                () -> de.getDecision("50000000000", loan4, 20),
                "Expected error to be thrown."
        );

        assertEquals(e0.getReason(), InvalidDataException.Reason.LOAN_AMOUNT_INVALID);
        assertEquals(e1.getReason(), InvalidDataException.Reason.LOAN_AMOUNT_INVALID);
        assertEquals(e2.getReason(), InvalidDataException.Reason.LOAN_AMOUNT_INVALID);
        assertEquals(e3.getReason(), InvalidDataException.Reason.LOAN_AMOUNT_INVALID);
        assertEquals(e4.getReason(), InvalidDataException.Reason.LOAN_AMOUNT_INVALID);
    }

    @Test
    public void testGetDecision_InvalidLoanPeriod_ShouldThrowException() {
        DecisionEngine de = new DecisionEngine();
        Integer period0 = null;
        Integer period1 = 0;
        Integer period2 = 11;
        Integer period3 = 61;
        Integer period4 = -12;

        InvalidDataException e0 = assertThrows(
                InvalidDataException.class,
                () -> de.getDecision("50000000000", 5000, period0),
                "Expected error to be thrown."
        );
        InvalidDataException e1 = assertThrows(
                InvalidDataException.class,
                () -> de.getDecision("50000000000", 5000, period1),
                "Expected error to be thrown."
        );
        InvalidDataException e2 = assertThrows(
                InvalidDataException.class,
                () -> de.getDecision("50000000000", 5000, period2),
                "Expected error to be thrown."
        );
        InvalidDataException e3 = assertThrows(
                InvalidDataException.class,
                () -> de.getDecision("50000000000", 5000, period3),
                "Expected error to be thrown."
        );
        InvalidDataException e4 = assertThrows(
                InvalidDataException.class,
                () -> de.getDecision("50000000000", 5000, period4),
                "Expected error to be thrown."
        );

        assertEquals(e0.getReason(), InvalidDataException.Reason.LOAN_PERIOD_INVALID);
        assertEquals(e1.getReason(), InvalidDataException.Reason.LOAN_PERIOD_INVALID);
        assertEquals(e2.getReason(), InvalidDataException.Reason.LOAN_PERIOD_INVALID);
        assertEquals(e3.getReason(), InvalidDataException.Reason.LOAN_PERIOD_INVALID);
        assertEquals(e4.getReason(), InvalidDataException.Reason.LOAN_PERIOD_INVALID);
    }

    @Test
    public void testGetDecision_UnknownCreditModifier_ShouldThrowException() {
        DecisionEngine de = new DecisionEngine();

        CantLoanException e0 = assertThrows(
                CantLoanException.class,
                () -> de.getDecision("12312312000", 2000, 20),
                "Expected error to be thrown."
        );
        CantLoanException e1 = assertThrows(
                CantLoanException.class,
                () -> de.getDecision("12312312199", 2000, 20),
                "Expected error to be thrown."
        );

        assertEquals(e0.getReason(), CantLoanException.Reason.UNKNOWN_CREDIT_MODIFIER);
        assertEquals(e1.getReason(), CantLoanException.Reason.UNKNOWN_CREDIT_MODIFIER);
    }

    @Test
    public void testGetDecision_ClientInDebt_ShouldThrowException() {
        DecisionEngine de = new DecisionEngine();

        CantLoanException e0 = assertThrows(
                CantLoanException.class,
                () -> de.getDecision("12312312200", 2000, 20),
                "Expected error to be thrown."
        );
        CantLoanException e1 = assertThrows(
                CantLoanException.class,
                () -> de.getDecision("12312312399", 2000, 20),
                "Expected error to be thrown."
        );

        assertEquals(e0.getReason(), CantLoanException.Reason.CLIENT_IN_DEBT);
        assertEquals(e1.getReason(), CantLoanException.Reason.CLIENT_IN_DEBT);
    }

    @Test
    public void testGetDecision_WorkingParameters_ShouldPass() throws InvalidDataException, CantLoanException {
        DecisionEngine de = new DecisionEngine();

        Integer[] result0 = de.getDecision("00000000400", 2000, 20);
        Integer[] result1 = de.getDecision("00000000599", 2000, 12);
        Integer[] result2 = de.getDecision("00000000600", 10000, 15);
        Integer[] result3 = de.getDecision("00000000485", 9577, 58);
        Integer[] result4 = de.getDecision("00000000800", 9577, 58);

        assertEquals(2000, result0[0]);
        assertEquals(20, result0[1]);
        assertEquals(2000, result1[0]);
        assertEquals(20, result1[1]);
        assertEquals(4500, result2[0]);
        assertEquals(15, result2[1]);
        assertEquals(5800, result3[0]);
        assertEquals(58, result3[1]);
        assertEquals(9577, result4[0]);
        assertEquals(58, result4[1]);
    }
}
