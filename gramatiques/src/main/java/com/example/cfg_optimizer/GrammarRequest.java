package com.example.cfg_optimizer;

import java.util.List;
import java.util.Map;

public class GrammarRequest {

    private List<String> variables;
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
