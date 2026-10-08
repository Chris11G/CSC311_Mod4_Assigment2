package org.example.csc311_mod4_assigment2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Finds an expression that makes 24 from four numbers, if one exists.
 * <p>
 * How it works (brute force with recursion):
 * <ol>
 *   <li>Pick any two numbers from the list.</li>
 *   <li>Combine them with +, -, * or / into one new number.</li>
 *   <li>Recurse on the shorter list (4 numbers -> 3 -> 2 -> 1).</li>
 *   <li>When one number is left, check whether it equals 24.</li>
 * </ol>
 * Trying every pair in both orders covers every possible ordering and
 * grouping (parentheses), so if a solution exists it will be found.
 * Alongside each number we keep the text of the expression that produced it,
 * so the final answer can be shown to the player.
 */
public final class Solver {

    public static final int TARGET = 24;

    /** Tolerance for comparing decimals, since e.g. 8/3 can't be stored exactly. */
    static final double EPSILON = 1e-9;

    private Solver() {
    }

    /**
     * @param values the four card values
     * @return an expression equal to 24, or empty if there is no solution
     */
    public static Optional<String> findSolution(List<Integer> values) {
        List<Double> numbers = new ArrayList<>();
        List<String> expressions = new ArrayList<>();
        for (int v : values) {
            numbers.add((double) v);
            expressions.add(String.valueOf(v));
        }

        String solution = solve(numbers, expressions);
        if (solution == null) {
            return Optional.empty();
        }
        // Every combination step wraps its result in (), so drop the outermost pair
        if (values.size() > 1) {
            solution = solution.substring(1, solution.length() - 1);
        }
        return Optional.of(solution);
    }

    /**
     * Recursive search.
     *
     * @param numbers     current values
     * @param expressions text form of each value (same order as {@code numbers})
     * @return an expression equal to 24, or null if none is possible
     */
    private static String solve(List<Double> numbers, List<String> expressions) {
        if (numbers.size() == 1) {
            return Math.abs(numbers.get(0) - TARGET) < EPSILON ? expressions.get(0) : null;
        }

        for (int i = 0; i < numbers.size(); i++) {
            for (int j = 0; j < numbers.size(); j++) {
                if (i == j) {
                    continue;
                }
                double a = numbers.get(i);
                double b = numbers.get(j);
                String exprA = expressions.get(i);
                String exprB = expressions.get(j);

                // Everything except the two numbers being combined
                List<Double> restNumbers = new ArrayList<>();
                List<String> restExpressions = new ArrayList<>();
                for (int k = 0; k < numbers.size(); k++) {
                    if (k != i && k != j) {
                        restNumbers.add(numbers.get(k));
                        restExpressions.add(expressions.get(k));
                    }
                }

                // Try each operator on the pair (a, b)
                for (char op : new char[]{'+', '-', '*', '/'}) {
                    // + and * give the same result either way, so skip the duplicate order
                    if ((op == '+' || op == '*') && j < i) {
                        continue;
                    }
                    if (op == '/' && Math.abs(b) < EPSILON) {
                        continue; // can't divide by zero
                    }

                    double result = switch (op) {
                        case '+' -> a + b;
                        case '-' -> a - b;
                        case '*' -> a * b;
                        default -> a / b;
                    };

                    restNumbers.add(result);
                    restExpressions.add("(" + exprA + op + exprB + ")");

                    String found = solve(restNumbers, restExpressions);
                    if (found != null) {
                        return found;
                    }

                    // Undo before trying the next operator
                    restNumbers.remove(restNumbers.size() - 1);
                    restExpressions.remove(restExpressions.size() - 1);
                }
            }
        }
        return null;
    }
}
