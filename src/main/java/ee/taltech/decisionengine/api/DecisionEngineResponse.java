package ee.taltech.decisionengine.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Store response data before sending it.
 */
@Setter
@Getter
@Component
public class DecisionEngineResponse {
    private boolean response;
    private String loanAmount;
    private String loanPeriod;
    private String message;
}


