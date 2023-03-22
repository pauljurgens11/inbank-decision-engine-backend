package ee.taltech.decisionengine.service;

import ee.taltech.decisionengine.exceptions.CantLoanException;
import ee.taltech.decisionengine.exceptions.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoanCalculatorTests {
    @Test
    public void testGetLoanResult_NullParams_ShouldThrowException() {
        LoanCalculator lc = new LoanCalculator(null, null, null);

        InvalidDataException e = assertThrows(
                InvalidDataException.class,
                lc::getLoanResult,
                "Expected error to be thrown."
                );
        assertEquals(e.getReason(), InvalidDataException.Reason.DATA_INVALID);

    }

    @Test
    public void testGetLoanResult_InvalidLoanAmount_ShouldThrowException() {
        LoanCalculator lc0 = new LoanCalculator(0, 20, 1000);
        LoanCalculator lc1 = new LoanCalculator(-5000, 20, 1000);
        LoanCalculator lc2 = new LoanCalculator(1999, 20, 1000);
        LoanCalculator lc3 = new LoanCalculator(10001, 20, 1000);

        InvalidDataException e0 = assertThrows(
                InvalidDataException.class,
                lc0::getLoanResult,
                "Expected error to be thrown."
        );
        InvalidDataException e1 = assertThrows(
                InvalidDataException.class,
                lc1::getLoanResult,
                "Expected error to be thrown."
        );
        InvalidDataException e2 = assertThrows(
                InvalidDataException.class,
                lc2::getLoanResult,
                "Expected error to be thrown."
        );
        InvalidDataException e3 = assertThrows(
                InvalidDataException.class,
                lc3::getLoanResult,
                "Expected error to be thrown."
        );

        assertEquals(e0.getReason(), InvalidDataException.Reason.LOAN_AMOUNT_INVALID);
        assertEquals(e1.getReason(), InvalidDataException.Reason.LOAN_AMOUNT_INVALID);
        assertEquals(e2.getReason(), InvalidDataException.Reason.LOAN_AMOUNT_INVALID);
        assertEquals(e3.getReason(), InvalidDataException.Reason.LOAN_AMOUNT_INVALID);
    }
    @Test
    public void testGetLoanResult_InvalidCreditModifier_ShouldThrowException() {
        LoanCalculator lc0 = new LoanCalculator(5000, 20, 0);
        LoanCalculator lc1 = new LoanCalculator(5000, 20, -100);

        CantLoanException e0 = assertThrows(
                CantLoanException.class,
                lc0::getLoanResult,
                "Expected error to be thrown."
        );
        CantLoanException e1 = assertThrows(
                CantLoanException.class,
                lc1::getLoanResult,
                "Expected error to be thrown."
        );

        assertEquals(e0.getReason(), CantLoanException.Reason.CLIENT_IN_DEBT);
        assertEquals(e1.getReason(), CantLoanException.Reason.CLIENT_IN_DEBT);
    }

    @Test
    public void testGetLoanResult_InvalidLoanPeriod_ShouldThrowException() {
        LoanCalculator lc0 = new LoanCalculator(5000, 0, 1000);
        LoanCalculator lc1 = new LoanCalculator(5000, -20, 1000);
        LoanCalculator lc2 = new LoanCalculator(5000, 11, 1000);
        LoanCalculator lc3 = new LoanCalculator(5000, 61, 1000);

        InvalidDataException e0 = assertThrows(
                InvalidDataException.class,
                lc0::getLoanResult,
                "Expected error to be thrown."
        );
        InvalidDataException e1 = assertThrows(
                InvalidDataException.class,
                lc1::getLoanResult,
                "Expected error to be thrown."
        );
        InvalidDataException e2 = assertThrows(
                InvalidDataException.class,
                lc2::getLoanResult,
                "Expected error to be thrown."
        );
        InvalidDataException e3 = assertThrows(
                InvalidDataException.class,
                lc3::getLoanResult,
                "Expected error to be thrown."
        );

        assertEquals(e0.getReason(), InvalidDataException.Reason.LOAN_PERIOD_INVALID);
        assertEquals(e1.getReason(), InvalidDataException.Reason.LOAN_PERIOD_INVALID);
        assertEquals(e2.getReason(), InvalidDataException.Reason.LOAN_PERIOD_INVALID);
        assertEquals(e3.getReason(), InvalidDataException.Reason.LOAN_PERIOD_INVALID);
    }

    @Test
    public void testGetLoanResult_NoPossibleLoans_ShouldThrowException() {
        LoanCalculator lc0 = new LoanCalculator(9999, 59, 5);
        LoanCalculator lc1 = new LoanCalculator(5000, 20, 33);

        CantLoanException e0 = assertThrows(
                CantLoanException.class,
                lc0::getLoanResult,
                "Expected error to be thrown."
        );
        assertEquals(e0.getReason(), CantLoanException.Reason.NO_POSSIBLE_LOANS);

        CantLoanException e1 = assertThrows(
                CantLoanException.class,
                lc1::getLoanResult,
                "Expected error to be thrown."
        );
        assertEquals(e1.getReason(), CantLoanException.Reason.NO_POSSIBLE_LOANS);
    }

    @Test
    public void testGetLoanResult_MaxOrMinHighestLoan_ShouldPass() throws InvalidDataException, CantLoanException {
        LoanCalculator lc0 = new LoanCalculator(2000, 30, 1000);
        LoanCalculator lc1 = new LoanCalculator(10000, 20, 1000);

        Integer[] result0 = lc0.getLoanResult();
        Integer[] result1 = lc1.getLoanResult();

        assertEquals(2000, result0[0]);
        assertEquals(10000, result1[0]);
    }

    @Test
    public void testGetLoanResult_MaxOrMinLowestPeriod_ShouldPass() throws InvalidDataException, CantLoanException {
        LoanCalculator lc0 = new LoanCalculator(3000, 12, 900);
        LoanCalculator lc1 = new LoanCalculator(4000, 60, 1000);

        Integer[] result0 = lc0.getLoanResult();
        Integer[] result1 = lc1.getLoanResult();

        assertEquals(12, result0[1]);
        assertEquals(60, result1[1]);
    }

    @Test
    public void testGetLoanResult_ParamsDontNeedChanging_ShouldPass() throws InvalidDataException, CantLoanException {
        LoanCalculator lc0 = new LoanCalculator(5432, 30, 300);
        LoanCalculator lc1 = new LoanCalculator(9813, 54, 1000);

        Integer[] result0 = lc0.getLoanResult();
        Integer[] result1 = lc1.getLoanResult();

        assertEquals(5432, result0[0]);
        assertEquals(9813, result1[0]);
        assertEquals(30, result0[1]);
        assertEquals(54, result1[1]);
    }

    @Test
    public void testGetLoanResult_LoanAmountNeedsLowering_ShouldPass() throws InvalidDataException, CantLoanException {
        LoanCalculator lc0 = new LoanCalculator(7000, 20, 100);
        LoanCalculator lc1 = new LoanCalculator(10000, 60, 145);

        Integer[] result0 = lc0.getLoanResult();
        Integer[] result1 = lc1.getLoanResult();

        assertEquals(2000, result0[0]);
        assertEquals(8700, result1[0]);
        assertEquals(20, result0[1]);
        assertEquals(60, result1[1]);
    }

    @Test
    public void testGetLoanResult_LoanPeriodNeedsIncreasing_ShouldPass() throws InvalidDataException, CantLoanException {
        LoanCalculator lc0 = new LoanCalculator(5996, 15, 100);
        LoanCalculator lc1 = new LoanCalculator(9623, 13, 123);

        Integer[] result0 = lc0.getLoanResult();
        Integer[] result1 = lc1.getLoanResult();

        assertEquals(2000, result0[0]);
        assertEquals(2000, result1[0]);
        assertEquals(20, result0[1]);
        assertEquals(17, result1[1]);
    }
}
