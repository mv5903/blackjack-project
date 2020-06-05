package blackjack;

import java.util.ArrayList;

public class Hand {
	private CardDeck cd = new CardDeck();
	private ArrayList<Card> hand = new ArrayList<Card>();
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
	 * Overloaded version of {@link #addCard()} which will add a fake card worth 10 points to the player's
	 *  hand to simulate a situation where a player may want an ace to count for 11 points 
	 *  rather than 1. This will cause also cause {@link #sumOfCards()} to add 10 points becuase of this
	 *  extra card.
	 * @param hasAce True indicates that this method should be used over {@link #addCard()}.
	 */
	public void addCard(boolean hasAce) {
		hand.add(new Card(0, 10));
	}
	
	public void clearAllHands() {
		hand.clear();
	}
	
	public int sumOfCards() {
		int sum = 0;
		for (Card c: hand) {
			if (c.getValue() >= 10 && c.getValue() <= 13) {
				sum+=10;
			} else {
				sum+=c.getValue();
			}
		}
		return sum;
	}
	/**
	 * Simpler way to see if a certain hand contains an ace, rather than a specific card.
	 * @return True if the current hand contains an ace.
	 */
	public boolean containsAce() {
		for (Card c: hand ) {
			if (c.getValue() == 1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(int val) {
		for (Card c: hand) {
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
				temp+=hand.get(i) + "]";
			} else {
				temp+=hand.get(i) + ", ";
			}
		}
		return temp;
	}
}
