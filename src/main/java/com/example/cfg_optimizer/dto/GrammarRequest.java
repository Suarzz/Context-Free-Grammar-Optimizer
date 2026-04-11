package com.example.cfg_optimizer.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Map;

public class GrammarRequest {

    @NotEmpty
    private List<String> variables;

    @NotEmpty
    private Map<String, String> productions;

    public List<String> getVariables() {
        return variables;
    }
    public void setVariables(List<String> variables) {
        this.variables = variables;
    }

    public Map<String, String> getProductions() {
        return productions;
    }
    public void setProductions(Map<String, String> productions) {
        this.productions = productions;
    }
}
