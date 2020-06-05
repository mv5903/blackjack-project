package blackjack;

public class Test {

	public static void main(String[] args) {
		CardDeck c = new CardDeck();
		System.out.println(c);
		c.shuffleDeck();
		System.out.println(c);
		System.out.println(c.getOrder());

	}

}
