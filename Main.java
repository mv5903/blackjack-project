package blackjack;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is where everything comes together! Run Blackjack from this class!
 * 
 * @author Matthew Vandenberg
 *
 */
public class Main {
	static Scanner s = new Scanner(System.in);
	public static int numPlayers, currentPlayer = 1;
	public static final int SLEEPTIME = 1000;
	public static ArrayList<Hand> hands = new ArrayList<Hand>(); // dealer gets hand index at 0
	public static ArrayList<Bet> bets = new ArrayList<Bet>();
	public static ArrayList<String> winners = new ArrayList<String>();
	public static boolean hasRunOnce = false; // ensures that a player keeps their amount and new bets aren't created

	public static void main(String[] args) {
		initGame();
	}

	/**
	 * Reinitializes and resets parts of the game so that it can be ran again.
	 */
	public Main() {

		hands.get(0).grabNewCardDeck(); // dealer needs to shuffle the cards
		winners.clear();
		currentPlayer = 1;
		for (Hand h : hands) {
			h.clearAllHands();
		}
		hands.clear();
		initGame();
	}

	/**
	 * Initializes the game according to player count.
	 */
	public static void initGame() {
		int startAmount = 0;
		System.out.println("How many players will be playing?");
		numPlayers = s.nextInt();
		if (!hasRunOnce) {
			System.out.println("What would you like the initial starting amount to be for all the players?");
			startAmount = s.nextInt();
		}
		for (int i = 0; i <= numPlayers; i++) {
			hands.add(new Hand());
			if (!hasRunOnce) {
				bets.add(new Bet(startAmount));
			}
		}
		hasRunOnce = true;
		for (int i = 1; i <= numPlayers; i++) {
			System.out.println("Player " + i + ", how much would you like to bet for the round?");
			bets.get(i).placeBet(s.nextInt());
		}
		System.out.println("Dealing cards...");
		sleep();
		nextPlayersTurn();
	}

	/**
	 * Handles a players turn and all things that could come along with it.
	 */
	public static void nextPlayersTurn() {
		boolean isHitting = true;
		displayHand();
		if (hands.get(currentPlayer).sumOfCards() == 21) {
			System.out.println("Blackjack! You won this round!");
			bets.get(currentPlayer).blackjack();
			winners.add("Player " + currentPlayer);
			determineNextPlayer();
		}
		System.out.println("Player " + currentPlayer + ", what would you like to do?");
		while (isHitting) {
			if (hands.get(currentPlayer).sumOfCards() > 21) {
				System.out.println("Bust!");
				bets.get(currentPlayer).lose();
				sleep();
				determineNextPlayer();
			}
			System.out.println("1. Hit\n2. Stand");
			if (s.nextInt() == 1) {
				hands.get(currentPlayer).addCard();
				displayHand();
			} else {
				isHitting = false;
				determineNextPlayer();
			}
		}

	}

	/**
	 * Handles the dealer's turn. The dealer will keep drawing until he busts or
	 * reaches 17.
	 */
	public static void dealersTurn() {
		System.out.print("Dealer's Turn. The dealer's starting hand is ");
		displayHand();
		if (hands.get(currentPlayer).sumOfCards() == 21) {
			System.out.println("Dealer got blackjack. Everyone loses!");
			sleep();
			playAgain();
		}
		while (hands.get(currentPlayer).sumOfCards() < 17) {
			sleep();
			hands.get(currentPlayer).addCard();
			System.out.print("Dealer drew another card. His hand is now: ");
			displayHand();
			System.out.println();
		}
		compareWithOthers();
	}

	/**
	 * Calculates the winners. A player will be added to the list of winners if:
	 * 
	 * <br>
	 * 1. The sum of a player's hand is greater than that of the dealer's hand, and
	 * the player's hand is less than 21. <br>
	 * 2. The sum of the dealer's hand is greater than 21.<br>
	 * 3. Any player's hand is equal to 21 as a result of a blackjack, which is
	 * handled automatically in {@link #nextPlayersTurn()}.<br>
	 * <br>
	 * Note that the purpose of the "continue" will skip checking for a winner for
	 * that current player if it has already been added because of a player
	 * receiving a blackjack during their turn.
	 * 
	 */
	public static void compareWithOthers() {
		if (hands.get(0).sumOfCards() > 21) {
			System.out.println("Dealer Busts.");
		}
		for (int i = 1; i <= numPlayers; i++) {
			if (winners.contains("Player " + i)) {
				continue;
			}
			if (((hands.get(i).sumOfCards() > hands.get(0).sumOfCards()) && hands.get(i).sumOfCards() <= 21)
					|| (hands.get(0).sumOfCards() > 21 && hands.get(i).sumOfCards() <= 21)
					|| hands.get(i).sumOfCards() == 21) {
				winners.add("Player " + i);
				bets.get(i).win();
			}
		}
		System.out.println("Winners: ");
		if (winners.isEmpty()) {
			System.out.println("[No one!]");
		} else {
			System.out.println(winners);
		}
		sleep();
		for (int i = 1; i <= numPlayers; i++) {
			if (bets.get(i).getCurrentBalance() == 0) {
				System.out.println("Yikes, player " + i
						+ "! Looks like you have nothing left to bet! You can participate, however you can't win anything anymore!");
			}
		}
		sleep();
		playAgain();
	}

	/**
	 * Asks the user if the game should be played again. If so, the class is ran
	 * again via the constructor.
	 */
	public static void playAgain() {
		System.out.println("Play again? \n1. Yes\n2. No\n3. Display balances");
		int choice = s.nextInt();
		if (choice == 1) {
			new Main();
		} else if (choice == 3) {
			ArrayList<String> currentAmounts = new ArrayList<String>();
			for (int i = 1; i <= numPlayers; i++) {
				currentAmounts.add("Player " + i + ": " + bets.get(i).getCurrentBalance());
			}
			System.out.println(currentAmounts);
			playAgain();
		} else {
			System.out.println("Ending game. Thanks for playing!");
			System.exit(0);
		}
	}

	/**
	 * Simulates a computer thinking by allowing the code to be paused briefly, also
	 * allowing for an easier read. The time is controlled by {@link #SLEEPTIME}.
	 * And in case you are wondering, yes I already knew about try/catch,
	 * Thead.sleep(), and System.exit() prior to starting this project!
	 */
	public static void sleep() {
		try {
			Thread.sleep(SLEEPTIME);
		} catch (InterruptedException e) {
			System.out.println("Couldn't sleep, an Interrupted Exception occured.");
		}
	}

	/**
	 * Decides, based upon the {@link #currentPlayer}, who should take their turn.
	 * The dealer always takes their turn last.
	 */
	public static void determineNextPlayer() {
		if (currentPlayer == numPlayers) {
			currentPlayer = 0;
			dealersTurn();
		} else {
			currentPlayer++;
			nextPlayersTurn();
		}
	}

	/**
	 * Displays the hand of a player.
	 */
	public static void displayHand() {
		System.out.println(hands.get(currentPlayer) + "\tTotal: " + hands.get(currentPlayer).sumOfCards());
	}
}
