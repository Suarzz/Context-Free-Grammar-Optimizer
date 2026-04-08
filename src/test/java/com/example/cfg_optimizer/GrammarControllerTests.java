package com.example.cfg_optimizer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GrammarController.class) //Only loads GrammarController class and its security (valid)
public class GrammarControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GrammarOptimizerService optimizerService;

    @Test
    public void whenMissingProductions_thenReturns400() throws Exception {

        String badJson = "{ \"variables\": [\"S\"] }"; // Missing productions!
        mockMvc.perform(post("/optimize")
                .contentType("application/json")
                .content(badJson)).
                andExpect(status().is(400));
    }

    @Test
    public void whenValidInput_thenReturns200() throws Exception {
        String validJson = """
                {
                	"variables": ["S", "A", "B", "C", "E", "F"],
                	"productions": {
                		"S": "A|B|E",
                		"A": "a",
                		"B": "b",
                        "C": "B",
                        "E": "Fa",
                        "F": "bE"
                	}
                }""";

        mockMvc.perform(post("/optimize")
                .contentType("application/json")
                .content(validJson)).
                andExpect(status().is(200));

    }
}