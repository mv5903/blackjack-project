package blackjack;

public class Card {
	private int value;
	private int suit; // where 0, 1, 2, 3, is spades diamonds clubs hearts in that order

	public Card(int suit, int value) {
		this.suit = suit;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public int getSuit() {
		return suit;
	}

	/**
	 * Displays a card according to its suit and value. Used to assist
	 * {@link #toString()}.
	 * 
	 * @param c The card to display.
	 * @return The card value and suit where 1, 11, 12, 13, are adjusted for A, J,
	 *         Q, and K accordingly.
	 */
	private String displayCard(Card c) {
		String temp = "";
		if (c.getValue() == 1) {
			temp += "A";
		} else if (c.getValue() == 11) {
			temp += "J";
		} else if (c.getValue() == 12) {
			temp += "Q";
		} else if (c.getValue() == 13) {
			temp += "K";
		} else {
			temp += Integer.toString(c.getValue());
		}
		return temp;
	}

	public String toString() {
		String temp = displayCard(this) + " of ";
		switch (suit) {
		case 0:
			temp += "spades";
			break;
		case 1:
			temp += "diamonds";
			break;
		case 2:
			temp += "clubs";
			break;
		case 3:
			temp += "hearts";
			break;
		}
		return temp;
	}
}
