package com.blackjack;

import com.google.gson.annotations.Expose;

/*
 * represents a single game with 2 players and bets.  Manages all aspects of a game.
 * Should not be able to start a new game when prior game hasn't finished
 */
public class Game 
{ 
	@Expose
	private Hand dealerHand=new Hand();
	
	@Expose 
	private Hand playerHand=new Hand();
	
	@Expose(serialize = false)
	int bet;
	 
	@Expose
	private boolean gameOver=false;

	private Deck deck=new Deck();
	
	//delete this later
	protected Hand getPlayerHand()
	{
		return playerHand;		
	}
	protected Hand getDealerHand()
	{
		return dealerHand;		
	}
	
	public Game(int betAmount) 
	{
		bet=betAmount;		
	}
	
	protected boolean isGameOver()
	{
		return gameOver;
	}
	/*
	 * new game but only deal 2 cards per
	 */
	public void play() 
	{		
		deck.initDeck();
		playerHand.init();
		dealerHand.init();
		gameOver=false;
		//player goes first
		playerHand.deal(deck.dealCard());		
		dealerHand.deal(deck.dealCard());		
		playerHand.deal(deck.dealCard());		
		dealerHand.deal(deck.dealCard());		
		
		if (isBlackJack(playerHand) && isBlackJack(dealerHand))
		{
			gameOver=true;
		}		
		else if (isBlackJack(playerHand))
		{				
			gameOver=true;
		}		
		else if (isBlackJack(dealerHand))
		{
			gameOver=true;		
		}					
	}
	
	//return true if more than 21 points
	private boolean isBusted(Hand hand)
	{
		if (hand.getValue()>21) 
		{
			return true; 
		}
		return false;
	}		

	/*
	 * Game must be over for a player to win
	 */
	protected boolean hasDealerWon()
	{
		if (gameOver)
		{
			int dealerVal=dealerHand.getValue();
			int playerVal=playerHand.getValue();			
			
			if (isBusted(playerHand) || (dealerVal>=17 && dealerVal<=21 && playerVal<dealerVal))
			{
				return true;
			}
		}
		return false;		
	}
	
	/*
	 * Game must be over for a player to win
	 */
	protected boolean hasPlayerWon()
	{
		if (gameOver)
		{		
			int dealerVal=dealerHand.getValue();
			int playerVal=playerHand.getValue();
			
			if ( playerVal<=21 && (playerVal>dealerVal || dealerVal>21))
			{
				return true;
			}
		}
		return false;		
	}
	
	private boolean isSoft17(Hand hand)
	{
		if (hand.getValue()==17 && hand.containsAce())
		{
			return true;
		}
		return false;
	}
	
	//return true if only 2 cards and sum is 21
	private boolean isBlackJack(Hand hand)
	{
		if (hand.getValue()==21 && hand.numCards()==2 ) 
		{
			return true; 
		}
		return false;
	}	
	
	//what to show to the client	 
	protected String getStatus(Hand hand)
	{
		if (isBlackJack(hand))
		{
			return "BlackJack";
		}
		else if (isBusted(hand))
		{
			return "Busted";
		}
		else
		{
			return "";
		}
	}
		
	//player deals a card.
	protected void deal() 
	{	
		playerHand.deal(deck.dealCard());
		
		//if player busts, game over
		if (isBusted(playerHand))
		{
			gameOver=true;			
		}
	}

	private boolean needCard(Hand hand) 
	{
		if (isSoft17(hand) || hand.getValue()<17)
		{
			return true;
		}
		
		return false;
	}
	
	/*
	 * only a player can stand so perform dealer operations here
	 */
	protected void stand() 
	{
		//now dealer deals until dealer either wins or loses
		while (needCard(dealerHand) )
		{
			dealerHand.deal(deck.dealCard());					
		}
		
		//game is always over when player stands and dealers cards up
		gameOver=true;
	}	
	
	@Override
	public String toString() 
	{	
		return "dealerHand="+dealerHand+":playerHand="+playerHand+ ":gameOver="+gameOver;
	}
}
