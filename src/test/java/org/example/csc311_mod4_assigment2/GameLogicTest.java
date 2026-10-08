package org.example.csc311_mod4_assigment2;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for the expression evaluator and the solver (no GUI needed). */
class GameLogicTest {

    @Test
    void evaluatesExampleFromAssignment() {
        ExpressionEvaluator.Result result = ExpressionEvaluator.evaluate("(6+6)*12/6");
        assertEquals(24, result.value(), 1e-9);
        assertEquals(List.of(6, 6, 12, 6), result.numbers());
    }

    @Test
    void respectsOrderOfOperations() {
        assertEquals(14, ExpressionEvaluator.evaluate("2+3*4").value(), 1e-9);
        assertEquals(20, ExpressionEvaluator.evaluate("(2+3)*4").value(), 1e-9);
        assertEquals(1, ExpressionEvaluator.evaluate("8-4-3").value(), 1e-9);
    }

    @Test
    void handlesFractionsInTheMiddle() {
        // 8 / (3 - 8/3) = 24, which needs a non-whole intermediate value
        assertEquals(24, ExpressionEvaluator.evaluate("8/(3-8/3)").value(), 1e-9);
    }

    @Test
    void rejectsInvalidExpressions() {
        assertThrows(IllegalArgumentException.class, () -> ExpressionEvaluator.evaluate(""));
        assertThrows(IllegalArgumentException.class, () -> ExpressionEvaluator.evaluate("(1+2"));
        assertThrows(IllegalArgumentException.class, () -> ExpressionEvaluator.evaluate("1+2)"));
        assertThrows(IllegalArgumentException.class, () -> ExpressionEvaluator.evaluate("1++2"));
        assertThrows(IllegalArgumentException.class, () -> ExpressionEvaluator.evaluate("abc"));
        assertThrows(ArithmeticException.class, () -> ExpressionEvaluator.evaluate("4/(2-2)"));
    }

    @Test
    void solverFindsValidSolutions() {
        List<List<Integer>> hands = List.of(
                List.of(6, 6, 12, 6), List.of(3, 3, 8, 8), List.of(1, 5, 5, 5), List.of(13, 12, 11, 10));
        for (List<Integer> hand : hands) {
            Optional<String> solution = Solver.findSolution(hand);
            assertTrue(solution.isPresent(), "Expected a solution for " + hand);

            // The solver's answer must itself pass the evaluator and use the same numbers
            ExpressionEvaluator.Result result = ExpressionEvaluator.evaluate(solution.get());
            assertEquals(24, result.value(), 1e-9);
            assertEquals(hand.stream().sorted().toList(), result.numbers().stream().sorted().toList());
        }
    }

    @Test
    void solverReportsNoSolution() {
        assertTrue(Solver.findSolution(List.of(1, 1, 1, 1)).isEmpty());
        assertTrue(Solver.findSolution(List.of(13, 13, 13, 13)).isEmpty());
    }
}
