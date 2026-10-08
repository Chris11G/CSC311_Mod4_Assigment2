package org.example.csc311_mod4_assigment2;

/**
 * A single playing card.
 * <p>
 * The rank is stored as its game value: Ace = 1, 2-10 = face value,
 * Jack = 11, Queen = 12, King = 13.
 *
 * @param rank the card's value, from 1 (Ace) to 13 (King)
 * @param suit the card's suit
 */
public record Card(int rank, Suit suit) {

    /** The four suits, each with its symbol and whether it is printed in red. */
    public enum Suit {
        SPADES("♠", false),
        HEARTS("♥", true),
        DIAMONDS("♦", true),
        CLUBS("♣", false);

        private final String symbol;
        private final boolean red;

        Suit(String symbol, boolean red) {
            this.symbol = symbol;
            this.red = red;
        }

        public String getSymbol() {
            return symbol;
        }

        public boolean isRed() {
            return red;
        }
    }

    public Card {
        if (rank < 1 || rank > 13) {
            throw new IllegalArgumentException("Rank must be between 1 and 13: " + rank);
        }
    }

    /** @return the number this card is worth in the game (same as its rank) */
    public int value() {
        return rank;
    }

    /** @return the label printed on the card, e.g. "A", "7", "Q" */
    public String label() {
        return switch (rank) {
            case 1 -> "A";
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            default -> String.valueOf(rank);
        };
    }

    @Override
    public String toString() {
        return label() + suit.getSymbol();
    }
}
