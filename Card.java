package com.blackjack;


import com.blackjack.BlackJackHelper.CardNames;
import com.blackjack.BlackJackHelper.Suit;
import com.google.gson.annotations.Expose;

/*
 * Represents a single card in a deck.  Mostly metadata.  The equals is overriden to support
 * list deletions of a Card.
 */  


public class Card  
{	
	@Expose
	private final BlackJackHelper.Suit suit;
	
	@Expose
	private final BlackJackHelper.CardNames name;
		
	@Expose
	private final int value;
		
	public Card(CardNames cardName, Suit Suit) 
	{	
		this.suit = Suit;
		this.name = cardName;
		this.value=cardName.getInt();
	}
	
	
	protected int getVal() 
	{
		return value;
	}	

	private Suit getSuit() 
	{
		return suit;
	}
	
	protected CardNames getName() 
	{
		return name;
	}

	@Override
	public String toString() 
	{
		return name+" "+suit + " "+ name.getInt();
	}
	
	@Override
	public boolean equals(Object obj) 
	{		
		return (getVal()==((Card)obj).getVal() && getName().equals(((Card)obj).getName())
				&& getSuit().equals(((Card)obj).getSuit()));
	}	
}
