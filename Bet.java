package blackjack;

/**
 * Betting is now fully implemented and works as expected.
 * 
 * @author Matthew Vandenberg
 *
 */
public class Bet {
	private int amountBet;
	private int playerBalance;

	public Bet(int startAmount) {
		playerBalance = startAmount;
	}

	public void placeBet(int bet) {
		amountBet = bet;
	}

	public int getCurrentBalance() {
		return playerBalance;
	}

	public void win() {
		playerBalance += amountBet;
	}

	public void lose() {
		playerBalance -= amountBet;
	}

	public void blackjack() {
		playerBalance += (amountBet * 1.5);
	}
}
