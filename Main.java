package blackjack;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static int currentPlayer, numPlayers, turns = 0;
	public static final int TIME = 1000; //simulates a computer thinking
	public static ArrayList<Hand> hands;//every player has their own hand
	public static ArrayList<Bet> bets;
	public static Scanner kbd = new Scanner(System.in);
	public static ArrayList<String> winners = new ArrayList<String>(); 

	public static void main(String[] args) {
		begin();
	}

	public static void begin() {
		hands = new ArrayList<Hand>(); // where index 0 is dealer
		bets = new ArrayList<Bet>();
		System.out.println("Welcome to Blackjack! How many players will be playing (not including the dealer)?");
		numPlayers = kbd.nextInt();
		System.out.println("How much is everyone starting with?");
		int startAmount = kbd.nextInt();
		currentPlayer = 1;
		for (int i = 0; i <= numPlayers; i++) {
			bets.add(new Bet(startAmount));
			hands.add(new Hand());
		}
		Bet.createPot();
		startTurn();
	}

	public static void startTurn() {
		if (hands.get(currentPlayer).containsAce()) { //will show additional amount if an ace is in the hand
			System.out.println("Player " + currentPlayer + ", it's your turn. Your first two cards are "
					+ hands.get(currentPlayer) + "\t(Total: " + hands.get(currentPlayer).sumOfCards() + " / " + (hands.get(currentPlayer).sumOfCards() + 10) + ")");
		} else {
			System.out.println("Player " + currentPlayer + ", it's your turn. Your first two cards are "
					+ hands.get(currentPlayer) + "\t(Total: " + hands.get(currentPlayer).sumOfCards() + ")");
		}
		if (hands.get(currentPlayer).sumOfCards() == 21
				|| (hands.get(currentPlayer).sumOfCards() == 11 && hands.get(currentPlayer).containsAce())) {
			System.out.println("BLACKJACK! Next player's turn...");
			winners.add("Player " + currentPlayer);
			if (numPlayers == 1) {
				dealersTurn();
			}
			nextPlayer();
		}
		turn();
	}

	public static void turn() {
		System.out.println("Player " + currentPlayer + ", place your bet!");
		bets.get(currentPlayer).placeBet(kbd.nextInt());
		System.out.println("What would you like to do? \n1: Hit \n2: Stand");
		int decision = kbd.nextInt();
		sleep();
		if (decision == 1) {
			hands.get(currentPlayer).addCard();
			System.out.println("Your hand is now: " + hands.get(currentPlayer) + "\t(Total: " + hands.get(currentPlayer).sumOfCards() + ")");
			if (hands.get(currentPlayer).containsAce()) {
				System.out.println("Would you like your ace to count as a 1 or 11?");
				int choice = kbd.nextInt();
				if (choice == 11) {
					hands.get(currentPlayer).addCard(true);
				}
			}
			if (hands.get(currentPlayer).sumOfCards() > 21) {
				sleep();
				System.out.println("BUST! Your turn is over.");
				if (numPlayers == 1) {
					dealersTurn();
				} else {
					nextPlayer();
				}
			} else {
				turn();
			}
		} else if (decision == 2) {
			if (hands.get(currentPlayer).containsAce()) {
				System.out.println("Would you like your ace to count as a 1 or 11?");
				int choice = kbd.nextInt();
				if (choice == 11) {
					hands.get(currentPlayer).addCard(true);
				}
			}
			nextPlayer();
		}
	}

	public static void dealersTurn() {
		System.out.println("Dealer's Turn");
		while (hands.get(0).sumOfCards() < 17) {
			hands.get(0).addCard();
			if (hands.get(0).sumOfCards() > 21) {
				sleep();
				System.out.println("DEALER BUSTS");
				afterDealerBust();
				break;
			} else if (hands.get(0).containsAce() && hands.get(0).sumOfCards() + 10 >= 17) {
				break; // If dealer's hand contains an ace (1 or 11)
			} else if (hands.get(0).sumOfCards() >= 17) {
				break;
			}
		}
		sleep();
		dealerDidNotBust();
	}

	public static void afterDealerBust() {
		for (int i = 1; i < numPlayers + 1; i++) {
			if (hands.get(i).sumOfCards() <= 21 && !winners.contains("Player " + i)) {
				winners.add("Player " + i);
			}
		}
		displayWinners();
	}

	public static void dealerDidNotBust() {
		for (int i = 1; i < numPlayers + 1; i++) {
			if (hands.get(0).containsAce() && 10 + hands.get(0).sumOfCards() > hands.get(i).sumOfCards() && 10 + hands.get(0).sumOfCards() <= 21) {
				i++;
			} else if (hands.get(0).sumOfCards() < hands.get(i).sumOfCards() && hands.get(i).sumOfCards() <= 21
					&& !winners.contains("Player " + i)) {
				winners.add("Player " + i);
			}
		}
		displayWinners();
	}

	public static void displayWinners() {
		System.out.println("Dealer's hand: " + hands.get(0) + "\t(Total: " + hands.get(0).sumOfCards() + ")");
		if (winners.size() == 0) {
			winners.add("Dealer won!");
		}
		System.out.println("Winners: " + winners);
		playAgain();
	}

	public static void playAgain() {
		System.out.println("Play again? \n1: Yes\n2: No");
		int willPlay = kbd.nextInt();
		if (willPlay == 1) {
			clearHands();
			numPlayers = 0;
			turns = 0;
			begin();
		} else if (willPlay == 2) {
			System.out.println("Thanks for playing!");
			System.exit(0);
		}
	}

	public static void nextPlayer() {
		turns++;
		if (turns == numPlayers) {
			dealersTurn();
		} else {
			if (currentPlayer == numPlayers) {
				currentPlayer = 1;
			} else {
				currentPlayer++;
			}
			startTurn();
		}

	}
	
	public static void clearHands() {
		winners.clear();
		for (Hand h: hands) {
			h.clearAllHands();
		}
		hands.clear();
	}
	
	public static void sleep() {
		try {
			Thread.sleep(TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
