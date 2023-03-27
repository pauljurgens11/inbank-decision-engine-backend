package ee.taltech.decisionengine.api;

import ee.taltech.decisionengine.service.DecisionEngine;
import ee.taltech.decisionengine.exceptions.CantLoanException;
import ee.taltech.decisionengine.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API endpoint for post requests. Receiving requests and sending responses happens within this branch.
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/api/engine")
public class DecisionEngineController {
    private final DecisionEngineResponse response;
    private final DecisionEngine engine;

    @Autowired
    public DecisionEngineController(DecisionEngineResponse response, DecisionEngine engine) {
        this.response = response;
        this.engine = engine;
    }

    /**
     * Receive data from a POST request and send back a fitting response.
     * <p>
     * @param request request object that contains request parameters
     * @return response with parameters
     */
    @PostMapping
    public ResponseEntity<DecisionEngineResponse> receiveRequestAndRespond(@RequestBody DecisionEngineRequest request) {
        try {
            Integer[] loanInfo = engine.getDecision(request.getPersonalCode(), request.getLoanAmount(), request.getLoanPeriod());

            // Can offer a loan if no exception occurs.
            response.setResponse(true);
            response.setLoanAmount(loanInfo[0].toString());
            response.setLoanPeriod(loanInfo[1].toString());
            response.setMessage(
                    request.getLoanAmount().equals(loanInfo[0]) && request.getLoanPeriod().equals(loanInfo[1])
                            ? "Success! We can offer you this loan:"
                            : "We can offer you this loan instead:" // Triggers when loan amount or period gets changed
            );
            System.out.println(response);
            return ResponseEntity.ok().body(response);

        } catch (CantLoanException | InvalidDataException e ) {
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setMessage("An unknown error occurred.");

        }
        response.setResponse(false);
        response.setLoanAmount(null);
        response.setLoanPeriod(null);
        System.out.println(response);
        return ResponseEntity.ok().body(response);
    }
}
