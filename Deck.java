package com.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Represents a Deck of 52 cards.  Manages initializing(shuffling), adding, and removing of cards.
 */
public class Deck 
{
	private static List<Card> deck=new ArrayList<Card>();
	
	//later add a static lazy loaded initialization deck to prevent constantly reloading deck

	private static Random random = new Random();
	
	//return a random card from this deck and then remove it from deck
	public Card dealCard()
	{
		if (deck.size()==0)
		{
			initDeck();
		}
		int index = random.nextInt(deck.size());		
		Card card=deck.get(index);		
		deck.remove(card);		
		return card;
	}
	
	//reinitialize deck
	public void initDeck()
	{		
		deck.clear();
		
		//fast way to initialize all 52 cards
		for (BlackJackHelper.Suit suit : BlackJackHelper.Suit.values()) 
		{
			for (BlackJackHelper.CardNames card : BlackJackHelper.CardNames.values()) 
			{
				initCard(card, suit);		
			}
		}	
	}
	
	private void initCard(BlackJackHelper.CardNames card, BlackJackHelper.Suit suite) 
	{
		Card c=new Card(card, suite);		
		deck.add(c);
	}	
	
	public int getCardCount()
	{
		return deck.size();
	}

	@Override
	public String toString()
	{
		String ret="";
		
		for (Card c:deck) 
		{
			ret+=c.toString() + ":";
		}
		
		return ret+":count="+getCardCount();
	}
}
