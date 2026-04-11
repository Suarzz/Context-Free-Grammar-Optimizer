package com.example.cfg_optimizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class GrammarTests {

    private Variable s; // Starting symbol
    private Variable a;
    private Variable b;
    private Variable c;

    @BeforeEach
    void setUp() {
        // Assuming a basic constructor for Variable (e.g., passing a name).
        // Adjust these instantiations based on your actual Variable class.
        s = new Variable("S");
        a = new Variable("A");
        b = new Variable("B");
        c = new Variable("C");
    }

    // --- Constructor & Getter Tests ---

    @Test
    void testConstructorAndGetters() {
        LinkedList<Variable> variables = new LinkedList<>(Arrays.asList(s, a, b));
        Grammar grammar = new Grammar(variables);

        assertEquals(s, grammar.getStartingSymbol(), "The first variable in the list should be set as the starting symbol.");
        assertEquals(variables, grammar.getVariables(), "The getVariables method should return the initialized list.");
    }

    @Test
    void testConstructor_emptyListThrowsException() {
        LinkedList<Variable> emptyList = new LinkedList<>();

        // variables.getFirst() will throw a NoSuchElementException if the list is empty
        assertThrows(NoSuchElementException.class, () -> {
            new Grammar(emptyList);
        }, "Initializing Grammar with an empty list should throw an exception.");
    }

    // --- eliminateNonAccessibles Tests ---

    @Test
    void testEliminateNonAccessibles_removesUnreachableVariables() {
        LinkedList<Variable> variables = new LinkedList<>(Arrays.asList(s, a, b));
        Grammar grammar = new Grammar(variables);
        s.addProduction(variables, "A");
        a.addProduction(variables, "a");
        b.addProduction(variables, "b");

        // S -> A  (S can reach A)
        // A -> a  (A reaches terminal 'a')
        // B -> b  (B reaches terminal 'b', but B is completely disconnected from S)

        grammar.eliminateNonAccessibles();

        LinkedList<Variable> updatedVars = grammar.getVariables();
        assertTrue(updatedVars.contains(s), "Starting symbol S must always remain accessible.");
        assertTrue(updatedVars.contains(a), "Variable A is reachable from S and should remain.");
        assertFalse(updatedVars.contains(b), "Variable B is completely unreachable from S and should be eliminated.");
    }

    @Test
    void testEliminateNonAccessibles_keepsAllIfReachable() {
        LinkedList<Variable> variables = new LinkedList<>(Arrays.asList(s, a, b));
        Grammar grammar = new Grammar(variables);

        s.addProduction(variables, "A");
        a.addProduction(variables, "B");
        b.addProduction(variables, "b");

        // S -> A
        // A -> B
        // B -> b
        // All variables form a reachable chain from S.

        grammar.eliminateNonAccessibles();

        assertEquals(3, grammar.getVariables().size(), "No variables should be removed if all are accessible from S.");
    }

    // --- eliminateNonFecunds Tests ---

    @Test
    void testEliminateNonFecunds_removesNonGeneratingVariables() {
        LinkedList<Variable> variables = new LinkedList<>(Arrays.asList(s, a, b, c));
        Grammar grammar = new Grammar(variables);

        s.addProduction(variables, "A");
        s.addProduction(variables, "B");
        a.addProduction(variables, "a");
        b.addProduction(variables, "C");
        c.addProduction(variables, "C");

        // S -> A | B
        // A -> a       (A is immediately fecund)
        // B -> C       (B's fecundity relies on C)
        // C -> C       (C is an infinite loop / never reaches a terminal -> non-fecund)

        // Expected outcome:
        // A is fecund (immediate).
        // C is not fecund (non-immediate failure).
        // Because C is not fecund, B also fails to be fecund.
        // S is fecund because it has at least one valid path (S -> A -> a).

        grammar.eliminateNonFecunds();

        LinkedList<Variable> updatedVars = grammar.getVariables();
        assertTrue(updatedVars.contains(s), "S generates a terminal string via A, so it should remain.");
        assertTrue(updatedVars.contains(a), "A directly generates a terminal, so it should remain.");
        assertFalse(updatedVars.contains(c), "C never yields a terminal string, so it should be eliminated.");
        assertFalse(updatedVars.contains(b), "B only leads to C (which is non-fecund), so B should be eliminated.");
    }

    @Test
    void testEliminateNonFecunds_keepsAllIfFecund() {
        LinkedList<Variable> variables = new LinkedList<>(Arrays.asList(s, a, b));
        Grammar grammar = new Grammar(variables);

        s.addProduction(variables, "A");
        a.addProduction(variables, "B");
        b.addProduction(variables, "b");

        // S -> A
        // A -> B
        // B -> b
        // S, A, and B all eventually resolve to the terminal 'b'.

        grammar.eliminateNonFecunds();

        assertEquals(3, grammar.getVariables().size(), "No variables should be removed if all eventually yield a terminal.");
    }

    @Test
    void testVariableWithNoProductions() {
        LinkedList<Variable> variables = new LinkedList<>(Arrays.asList(s, a, b, c));
        Grammar grammar = new Grammar(variables);

        s.addProduction(variables, "A");
        s.addProduction(variables, "B");
        a.addProduction(variables, "a");
        b.addProduction(variables, "b");

        grammar.eliminateNonFecunds();
        grammar.eliminateNonAccessibles();

        assertEquals(3, grammar.getVariables().size(), "Error");
    }
}