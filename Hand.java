package blackjack;

import java.util.ArrayList;

public class Hand {
	private CardDeck cd = new CardDeck();
	private ArrayList<Card> hand = new ArrayList<Card>();
	/**
	 * Purpose of {@link #nextCard} is to keep track of where in the deck the
	 * program is at.
	 */
	private static int nextCard = 0;

	public Hand() {
		for (int i = 0; i < 2; i++) {
			addCard();
		}
	}

	public int getNextCard() {
		return nextCard;
	}

	public void addCard() {
		hand.add(cd.getCard(nextCard));
		nextCard++;
	}

	/**
	 * Resets {@link #hand} so that there are no more cards in anyone's hand.
	 */
	public void clearAllHands() {
		hand.clear();
	}

	/**
	 * Running total of cards in a hand. Ace is always counted as 1.
	 * 
	 * @return Sum of values of all the cards in a hand.
	 */
	public int sumOfCards() {
		int sum = 0;
		for (Card c : hand) {
			if (c.getValue() >= 10 && c.getValue() <= 13) {
				sum += 10;
			} else {
				sum += c.getValue();
			}
		}
		if (containsAce() && sum() <= 11) {
			sum += 10;
		}
		return sum;
	}
	/**
	 * Helper method for {@link #sumOfCards()}. Not used elsewhere.
	 * @return Sum of cards not considering an ace.
	 */
	private int sum() {
		int sum = 0;
		for (Card c : hand) {
			if (c.getValue() >= 10 && c.getValue() <= 13) {
				sum += 10;
			} else {
				sum += c.getValue();
			}
		}
		return sum;
	}

	/**
	 * Simpler way to see if a certain hand contains an ace, rather than a specific
	 * card.
	 * 
	 * @return true or false if the current hand contains an ace.
	 */
	public boolean containsAce() {
		for (Card c : hand) {
			if (c.getValue() == 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true or false based upon whether or not a certain value exists in a
	 * hand.
	 * 
	 * @param val The value to check for.
	 * @return Existence of a certain value in a hand.
	 */
	public boolean contains(int val) {
		for (Card c : hand) {
			if (val == c.getValue()) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		String temp = "[";
		for (int i = 0; i < hand.size(); i++) {
			if (i == hand.size() - 1) {
				temp += hand.get(i) + "]";
			} else {
				temp += hand.get(i) + ", ";
			}
		}
		return temp;
	}
}
