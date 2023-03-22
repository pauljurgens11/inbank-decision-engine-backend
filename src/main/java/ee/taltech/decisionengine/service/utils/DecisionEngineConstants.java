package ee.taltech.decisionengine.service.utils;

import lombok.Getter;

/**
 * Constants used in the service side of the decision engine.
 */
@Getter
public class DecisionEngineConstants {
    public static final int minimumLoanAmount = 2000;
    public static final int maximumLoanAmount = 10000;
    public static final int minimumLoanPeriod = 12;
    public static final int maximumLoanPeriod = 60;
}
