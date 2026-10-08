package org.example.csc311_mod4_assigment2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for card-game.fxml. Handles the three buttons:
 * <ul>
 *   <li><b>Refresh</b> - deals four new random cards.</li>
 *   <li><b>Find a Solution</b> - shows an expression that makes 24 (or says none exists).</li>
 *   <li><b>Verify</b> - checks the player's expression and shows the result in a dialog.</li>
 * </ul>
 */
public class CardGameController {

    /** Holds the four CardView nodes. */
    @FXML
    private HBox cardBox;

    /** Where the player types their expression. */
    @FXML
    private TextField expressionField;

    /** Read-only field that displays the computer's solution. */
    @FXML
    private TextField solutionField;

    private final Deck deck = new Deck();
    private List<Card> currentCards = new ArrayList<>();

    /** Called automatically by JavaFX after the FXML is loaded. */
    @FXML
    public void initialize() {
        dealNewCards();
    }

    @FXML
    protected void onRefreshClick() {
        dealNewCards();
    }

    @FXML
    protected void onFindSolutionClick() {
        solutionField.setText(Solver.findSolution(getCardValues()).orElse("No solution"));
    }

    /**
     * Validates the player's expression:
     * 1) it must be a valid expression,
     * 2) it must use exactly the four card values, each once,
     * 3) it must evaluate to 24.
     */
    @FXML
    protected void onVerifyClick() {
        String expression = expressionField.getText();

        ExpressionEvaluator.Result result;
        try {
            result = ExpressionEvaluator.evaluate(expression);
        } catch (IllegalArgumentException | ArithmeticException e) {
            showDialog(Alert.AlertType.ERROR, "Invalid Expression", e.getMessage());
            return;
        }

        // Compare sorted copies so the order the numbers were typed in doesn't matter
        List<Integer> used = new ArrayList<>(result.numbers());
        List<Integer> expected = getCardValues();
        used.sort(null);
        expected.sort(null);
        if (!used.equals(expected)) {
            showDialog(Alert.AlertType.ERROR, "Wrong Numbers",
                    "The numbers in your expression don't match the cards.\n\n"
                            + "Cards: " + expected + "\nYou used: " + used + "\n\n"
                            + "Use each card value exactly once (A=1, J=11, Q=12, K=13).");
            return;
        }

        if (Math.abs(result.value() - Solver.TARGET) < Solver.EPSILON) {
            showDialog(Alert.AlertType.INFORMATION, "Correct!",
                    "Correct! " + expression.trim() + " = 24");
        } else {
            showDialog(Alert.AlertType.ERROR, "Incorrect",
                    "Incorrect. " + expression.trim() + " = " + formatNumber(result.value()) + ", not 24.");
        }
    }

    /** Deals four cards, displays them, and clears both text fields. */
    private void dealNewCards() {
        currentCards = deck.deal(4);

        cardBox.getChildren().clear();
        for (Card card : currentCards) {
            cardBox.getChildren().add(new CardView(card));
        }

        expressionField.clear();
        solutionField.clear();
    }

    /** @return the game values (1-13) of the cards on the table */
    private List<Integer> getCardValues() {
        List<Integer> values = new ArrayList<>();
        for (Card card : currentCards) {
            values.add(card.value());
        }
        return values;
    }

    private void showDialog(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /** Shows whole numbers without ".0" and fractions with up to 4 decimals. */
    private static String formatNumber(double value) {
        if (value == Math.rint(value)) {
            return String.valueOf((long) value);
        }
        return String.format("%.4f", value).replaceAll("0+$", "");
    }
}
