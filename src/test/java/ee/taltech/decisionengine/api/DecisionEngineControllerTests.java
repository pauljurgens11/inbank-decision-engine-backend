package ee.taltech.decisionengine.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.taltech.decisionengine.exceptions.CantLoanException;
import ee.taltech.decisionengine.exceptions.InvalidDataException;
import ee.taltech.decisionengine.service.DecisionEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class DecisionEngineControllerTests {
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DecisionEngine engine;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetDecision_InvalidPersonalCode_ShouldGiveErrorMessage() throws InvalidDataException, CantLoanException, Exception {
        given(engine.getDecision(anyString(), anyInt(), anyInt()))
                .willThrow(new InvalidDataException(InvalidDataException.Reason.PERSONAL_CODE_INVALID));
        DecisionEngineRequest request = new DecisionEngineRequest("10000000800", 5000, 50);

        mockMvc.perform(post("/api/engine")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(false))
                .andExpect(jsonPath("$.loanAmount").isEmpty())
                .andExpect(jsonPath("$.loanPeriod").isEmpty())
                .andExpect(jsonPath("$.message").value("Invalid personal code. Must consist of 11 digits."))
                .andReturn();
    }

    @Test
    void testGetDecision_InvalidLoanAmount_ShouldGiveErrorMessage() throws InvalidDataException, CantLoanException, Exception {
        given(engine.getDecision(anyString(), anyInt(), anyInt()))
                .willThrow(new InvalidDataException(InvalidDataException.Reason.LOAN_AMOUNT_INVALID));
        DecisionEngineRequest request = new DecisionEngineRequest("10000000800", 5000, 50);

        mockMvc.perform(post("/api/engine")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(false))
                .andExpect(jsonPath("$.loanAmount").isEmpty())
                .andExpect(jsonPath("$.loanPeriod").isEmpty())
                .andExpect(jsonPath("$.message").value("Invalid loan amount. Must be between 2000 and 10000 euros."))
                .andReturn();
    }

    @Test
    void testGetDecision_InvalidLoanPeriod_ShouldGiveErrorMessage() throws InvalidDataException, CantLoanException, Exception {
        given(engine.getDecision(anyString(), anyInt(), anyInt()))
                .willThrow(new InvalidDataException(InvalidDataException.Reason.LOAN_PERIOD_INVALID));
        DecisionEngineRequest request = new DecisionEngineRequest("10000000800", 5000, 50);

        mockMvc.perform(post("/api/engine")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(false))
                .andExpect(jsonPath("$.loanAmount").isEmpty())
                .andExpect(jsonPath("$.loanPeriod").isEmpty())
                .andExpect(jsonPath("$.message").value("Invalid loan period. Must be between 12 and 60 months."))
                .andReturn();
    }

    @Test
    void testGetDecision_ClientInDebt_ShouldGiveErrorMessage() throws InvalidDataException, CantLoanException, Exception {
        given(engine.getDecision(anyString(), anyInt(), anyInt()))
                .willThrow(new CantLoanException(CantLoanException.Reason.CLIENT_IN_DEBT));
        DecisionEngineRequest request = new DecisionEngineRequest("10000000800", 5000, 50);

        mockMvc.perform(post("/api/engine")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(false))
                .andExpect(jsonPath("$.loanAmount").isEmpty())
                .andExpect(jsonPath("$.loanPeriod").isEmpty())
                .andExpect(jsonPath("$.message").value("Client is in debt! Can't loan any money :("))
                .andReturn();
    }

    @Test
    void testGetDecision_UnknownCreditModifier_ShouldGiveErrorMessage() throws InvalidDataException, CantLoanException, Exception {
        given(engine.getDecision(anyString(), anyInt(), anyInt()))
                .willThrow(new CantLoanException(CantLoanException.Reason.UNKNOWN_CREDIT_MODIFIER));
        DecisionEngineRequest request = new DecisionEngineRequest("10000000800", 5000, 50);

        mockMvc.perform(post("/api/engine")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(false))
                .andExpect(jsonPath("$.loanAmount").isEmpty())
                .andExpect(jsonPath("$.loanPeriod").isEmpty())
                .andExpect(jsonPath("$.message").value("Your personal code's credit modifier value is unknown (more info in repository readme)."))
                .andReturn();
    }

    @Test
    void testGetDecision_NoLoansAvailable_ShouldGiveErrorMessage() throws InvalidDataException, CantLoanException, Exception {
        given(engine.getDecision(anyString(), anyInt(), anyInt()))
                .willThrow(new CantLoanException(CantLoanException.Reason.NO_POSSIBLE_LOANS));
        DecisionEngineRequest request = new DecisionEngineRequest("10000000800", 5000, 50);

        mockMvc.perform(post("/api/engine")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(false))
                .andExpect(jsonPath("$.loanAmount").isEmpty())
                .andExpect(jsonPath("$.loanPeriod").isEmpty())
                .andExpect(jsonPath("$.message").value("No loans are currently available for you. Try again with different values."))
                .andReturn();
    }

    @Test
    void testGetDecision_ValidRequest_ShouldPass() throws InvalidDataException, CantLoanException, Exception {
        //given
        given(engine.getDecision(anyString(), anyInt(), anyInt()))
                .willReturn(new Integer[] {5000, 50});
        DecisionEngineRequest request = new DecisionEngineRequest("10000000800", 5000, 50);

        //when
        MvcResult result = mockMvc.perform(post("/api/engine")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(true))
                .andExpect(jsonPath("$.loanAmount").value(5000))
                .andExpect(jsonPath("$.loanPeriod").value(50))
                .andExpect(jsonPath("$.message").value("Success!"))
                .andReturn();

        //then
        DecisionEngineResponse response = objectMapper
                .readValue(result.getResponse().getContentAsString(), DecisionEngineResponse.class);
        assertEquals(5000, response.getLoanAmount());
        assertEquals(50, response.getLoanPeriod());
        assertEquals("Success!", response.getMessage());
    }
}
