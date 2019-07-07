package com.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.Expose;

/*
 * Represents a dealer or betting hand.  No constructor since I want to add cards sequentially.
 * Abstract class to force Dealer/Player to implement abstract methods below.
 */
public class Hand 
{		
	@Expose
	private final List<Card> cards=new ArrayList<Card>();

	//consider returning an iterator
	protected List<Card> getCards()
	{		
		return Collections.unmodifiableList(cards);		
	}
	
	//get the total hand value
	//don't really neet this.  Calc this real-time in deal() method.  Then you can remove getCards().
	protected int getValue()
	{
		int val=0;
		int aceCount=0;
		
		for (Card card:cards)
		{
			val+=card.getVal();
			if (card.getName().equals(BlackJackHelper.CardNames.ACE))
			{
				aceCount++;
			}
		}
		
		//now look at possible ace reduction to get under 21
		if (val>21 && aceCount>0)
		{
			while(aceCount>0 && val>21)
		    {
				val-=10;
				aceCount--;
		    }			
		}  
		
		return val;
	}
	
	protected boolean containsAce()
	{
		for (Card card:cards)
		{
			if (card.getName().equals(BlackJackHelper.CardNames.ACE))
			{
				return true;
			}
		}
		
		return false;
	}
	
	protected void deal(Card card)
	{
		cards.add(card);
	}
	
	protected int numCards()
	{
		return cards.size();
	}
		
	protected void init() 
	{
		cards.clear();		
	}
	
	@Override
	public String toString() 
	{
		String ret="";
		
		for (Card c:cards) 
		{
			ret+=c.toString() + ":";
		}
		
		return ret + ":value="+getValue();
	}
}
