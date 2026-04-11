package com.example.cfg_optimizer;

import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class GrammarOptimizerService {


    public GrammarResponse optimizeGrammar(GrammarRequest request) {

        LinkedList<Variable> linkedVariables = parseVariables(request.getVariables());

        for(Variable variable : linkedVariables) {
            if(!request.getProductions().containsKey(variable.getName())) throw new VariableWithNoProductionsException("All variables must have at least one production");
            String productions = request.getProductions().get(variable.getName());
            addAllProductions(productions, variable, linkedVariables);
        }

        Grammar grammar = new Grammar(linkedVariables);
        grammar.eliminateNonFecunds();
        grammar.eliminateNonAccessibles();

        List<String> variablesResponse = new LinkedList<>();
        Map<String,String> productionsResponse = new HashMap<>();
        for(Variable variable : grammar.getVariables()) {
            variablesResponse.add(variable.getName());
            productionsResponse.put(variable.getName(), variable.getStringProductions());
        }

        GrammarResponse optimizedGrammar = new GrammarResponse(variablesResponse, productionsResponse);
        return optimizedGrammar;
    }

    private static LinkedList<Variable> parseVariables(List<String> variables) {
        var linkedVariables = new LinkedList<Variable>();
        for(String variable : variables) {
            Variable newVar = new Variable(variable.trim());
            linkedVariables.add(newVar);
        }

        return linkedVariables;
    }



    private static void addAllProductions(String line, Variable variable, LinkedList<Variable> variables) {
        String[] productions = line.split("\\|");
        for(String prod : productions) {
            variable.addProduction(variables, prod);
        }

    }
}
