package org.example.csc311_mod4_assigment2;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses and evaluates an arithmetic expression such as {@code (6+6)*12/6}.
 * <p>
 * Supported: whole numbers, {@code + - * /}, and parentheses. Normal order of
 * operations applies (* and / before + and -, left to right).
 * <p>
 * This is a "recursive descent" parser. Each grammar rule below becomes one method:
 * <pre>
 *   expression := term   (('+' | '-') term)*
 *   term       := factor (('*' | '/') factor)*
 *   factor     := number | '(' expression ')'
 * </pre>
 * Because {@code expression} calls {@code term}, and {@code term} calls
 * {@code factor}, multiplication and division are grouped tighter than
 * addition and subtraction automatically.
 * <p>
 * While parsing, every number found is recorded so the game can check that
 * the player used exactly the four card values.
 */
public class ExpressionEvaluator {

    /**
     * The outcome of evaluating an expression.
     *
     * @param value   the numeric result
     * @param numbers every number that appeared in the expression, in order
     */
    public record Result(double value, List<Integer> numbers) {
    }

    private final String input;
    private int pos = 0;                                  // index of the next character to read
    private final List<Integer> numbers = new ArrayList<>();

    private ExpressionEvaluator(String input) {
        this.input = input;
    }

    /**
     * Evaluates the given expression.
     *
     * @param expression text typed by the player
     * @return the value and the numbers used
     * @throws IllegalArgumentException if the expression is not valid
     * @throws ArithmeticException      if it divides by zero
     */
    public static Result evaluate(String expression) {
        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException("Please enter an expression.");
        }
        ExpressionEvaluator parser = new ExpressionEvaluator(expression);
        double value = parser.parseExpression();

        // Anything left over (e.g. an extra ")") means the expression is malformed
        parser.skipSpaces();
        if (parser.pos < parser.input.length()) {
            throw new IllegalArgumentException(
                    "Unexpected character '" + parser.input.charAt(parser.pos) + "' at position " + (parser.pos + 1) + ".");
        }
        return new Result(value, List.copyOf(parser.numbers));
    }

    /** expression := term (('+' | '-') term)* */
    private double parseExpression() {
        double value = parseTerm();
        while (true) {
            if (consume('+')) {
                value += parseTerm();
            } else if (consume('-')) {
                value -= parseTerm();
            } else {
                return value;
            }
        }
    }

    /** term := factor (('*' | '/') factor)* */
    private double parseTerm() {
        double value = parseFactor();
        while (true) {
            if (consume('*')) {
                value *= parseFactor();
            } else if (consume('/')) {
                double divisor = parseFactor();
                if (divisor == 0) {
                    throw new ArithmeticException("Division by zero is not allowed.");
                }
                value /= divisor;
            } else {
                return value;
            }
        }
    }

    /** factor := number | '(' expression ')' */
    private double parseFactor() {
        if (consume('(')) {
            double value = parseExpression();
            if (!consume(')')) {
                throw new IllegalArgumentException("Missing closing parenthesis ')'.");
            }
            return value;
        }

        skipSpaces();
        int start = pos;
        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            pos++;
        }
        if (start == pos) {
            if (pos >= input.length()) {
                throw new IllegalArgumentException("The expression ends too early - a number is missing.");
            }
            throw new IllegalArgumentException(
                    "Expected a number at position " + (pos + 1) + " but found '" + input.charAt(pos) + "'.");
        }
        int number = Integer.parseInt(input.substring(start, pos));
        numbers.add(number);
        return number;
    }

    /**
     * If the next non-space character is {@code expected}, moves past it and returns true.
     * Otherwise leaves the position unchanged and returns false.
     */
    private boolean consume(char expected) {
        skipSpaces();
        if (pos < input.length() && input.charAt(pos) == expected) {
            pos++;
            return true;
        }
        return false;
    }

    private void skipSpaces() {
        while (pos < input.length() && Character.isWhitespace(input.charAt(pos))) {
            pos++;
        }
    }
}
