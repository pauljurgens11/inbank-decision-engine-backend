package ee.taltech.decisionengine.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Store information received from an API request.
 */
@Getter
@AllArgsConstructor
public class DecisionEngineRequest {
    private final String personalCode;
    private final Integer loanAmount;
    private final Integer loanPeriod;
}
