package org.example.csc311_mod4_assigment2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A standard 52-card deck used to deal random hands.
 */
public class Deck {

    private final List<Card> cards = new ArrayList<>();

    /** Builds a full deck: every rank (1-13) in every suit. */
    public Deck() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (int rank = 1; rank <= 13; rank++) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    /**
     * Shuffles the deck and returns the top {@code count} cards.
     * The cards are all different because they come from one shuffled deck.
     *
     * @param count how many cards to deal (the game uses 4)
     * @return a new list containing the dealt cards
     */
    public List<Card> deal(int count) {
        Collections.shuffle(cards);
        return new ArrayList<>(cards.subList(0, count));
    }
}
