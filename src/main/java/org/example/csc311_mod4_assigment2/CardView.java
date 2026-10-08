package org.example.csc311_mod4_assigment2;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Draws one playing card on screen using JavaFX controls (no image files needed).
 * <p>
 * The card shows its label and suit in the top-left and bottom-right corners,
 * and a large suit symbol (or the letter, for face cards) in the middle.
 * Its appearance is controlled by the {@code .card} rules in styles.css.
 */
public class CardView extends StackPane {

    public CardView(Card card) {
        getStyleClass().add("card");
        // Red for hearts/diamonds, black for spades/clubs
        getStyleClass().add(card.suit().isRed() ? "card-red" : "card-black");

        String corner = card.label() + "\n" + card.suit().getSymbol();

        Label topLeft = new Label(corner);
        topLeft.getStyleClass().add("card-corner");
        StackPane.setAlignment(topLeft, Pos.TOP_LEFT);

        Label bottomRight = new Label(corner);
        bottomRight.getStyleClass().add("card-corner");
        bottomRight.setRotate(180); // upside down, like a real card
        StackPane.setAlignment(bottomRight, Pos.BOTTOM_RIGHT);

        // Face cards and aces show their letter in the middle; number cards show the suit
        boolean isLetterCard = card.rank() == 1 || card.rank() > 10;
        Label center = new Label(isLetterCard ? card.label() + card.suit().getSymbol() : card.suit().getSymbol());
        center.getStyleClass().add("card-center");

        getChildren().addAll(center, topLeft, bottomRight);
    }
}
