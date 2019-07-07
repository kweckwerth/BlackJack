package com.blackjack;

/*
 * A helper class for blackjack to assist with initializing all the cards/hands/decks.
 * Use enums for strong type casting and ease of initialization.
 */
public class BlackJackHelper 
{	
	private static final String SPADE="Spades";
	private static final String DIAMOND="Diamonds";
	private static final String HEART="Hearts";
	private static final String CLUB="Clubs";
	
	private static final String PLAYER="player";
	private static final String DEALER="dealer";
	
	public enum PlayType
	{
		START,DEAL,STAND;
	};
	
	public enum Winner
	{
		PLAYER,DEALER,NO_ONE;
	};
	
	public enum PlayerDealer 
	{ 
	    PLAYERTYPE(PLAYER), DEALERTYPE(DEALER); 
		private final String val;
		
		private PlayerDealer(String st) 
		{
			this.val = st;
		}		
	} 
	
	//ace,diamond,club, or spade
	public enum Suit 
	{ 
	    SPADES(SPADE), DIAMONDS(DIAMOND),HEARTS(HEART),CLUBS(CLUB); 
		private final String val;
		
		private Suit(String st) 
		{
			this.val = st;
		}		
	} 
	
	public enum CardNames 
	{ 
	    ACE(11), KING(10), QUEEN(10), JACK(10), TEN(10),NINE(9),EIGHT(8),SEVEN(7),SIX(6),FIVE(5),FOUR(4),THREE(3),TWO(2);
		private final int val;
		
		private CardNames(int val) 
		{
			this.val = val;
		}
		
		public int getInt() 
		{
			return val;
		}
	} 	
}
