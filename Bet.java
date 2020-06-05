package blackjack;

public class Bet {
	private int startAmount;
	private static int pot;
	
	public Bet(int startAmount) {
		this.startAmount = startAmount;
	}
	
	public static void createPot() {
		pot = 0;
	}
	
	public void setBet(int startAmount) {
		this.startAmount = startAmount;
	}
	
	public int getStartAmount() {
		return startAmount;
	}
	
	public void placeBet(int bet) {
		startAmount-=bet;
		pot+=bet;
	}
	
	public int getPot() {
		return pot;
	}
	

	
	public String toString() {
		return "Bet: " + startAmount;
	}
}
