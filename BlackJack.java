package com.blackjack;

import java.lang.reflect.Type;

import com.blackjack.BlackJackHelper.PlayType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
 * Represents the lifecycle of blackjack.  Includes sequential games.
 * This represents a singleton and simply resets in between games.
 * Only supports a dealer vs player.  Consider breaking this out into a Player and Dealer class.
 */
public class BlackJack 
{	
	public static Gson gson;
	
	@SerializedName("winnings") 
	@Expose
	private int playerAmount=100;
	
	@Expose(serialize = false)
	private static final int BET=5;
	
	@Expose
	private int numGames=0;
	
	@Expose(serialize = false)
	private static Object mutex=new Object();
	
	@Expose(serialize = false)
	private static volatile BlackJack instance;
	
	@Expose
	private Game game=new Game(BET);
		
	private BlackJack()
	{}
	
	public static BlackJack getInstance()
	{			
		BlackJack result = instance;
		
		if (result==null)	
		{
			synchronized(mutex)
			{
				if (result==null)	
				{
					result=instance=new BlackJack();					
					 BJSerializer();
				}
			}
		}
		
		return result;
	}	
	
	public Game getGame()
	{
		return game;
	}
	
	/*
	 * don't allow dup games
	 */
	public synchronized void play(PlayType playType)
	{
		if (game.isGameOver() && (playType==PlayType.DEAL || playType==PlayType.STAND))
		{
			System.out.println("Game is over.  Please start a new game.");
			return;
		}
		else if (playerAmount<=0)
		{
			System.out.println("Player is out of money.  No more game.");
			return;
		}
		else if (playType==PlayType.START)
		{
			synchronized(game)
			{
				game.play();
				payout();
			}
			numGames++;						
		}
		else if (playType==PlayType.DEAL)
		{
			synchronized(game)
			{				
				game.deal();
				payout();
			}					
		}		
		else if (playType==PlayType.STAND)
		{
			synchronized(game)
			{				
				game.stand();
				payout();				
			}	
		}
		else
		{
			throw new UnsupportedOperationException("Bad play type."+playType); 
		}
	}
	
	//monitor for game is over
	private void payout() 
	{
		if (game.isGameOver())
		{
			if (game.hasDealerWon())
			{
				playerAmount-=BET;
			}
			else if (game.hasPlayerWon())
			{
				playerAmount+=BET;
			}		
		}		
	}	

	@Override
	public String toString() 
	{	
		return "Game="+game+":numgames="+numGames+":player amount="+playerAmount;
	}	
	
	private static void BJSerializer() 
	{
		GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
					
		JsonSerializer<Hand> serializer = new HandSerialiser(); // will implement in a second  
		gsonBuilder.registerTypeAdapter(Hand.class, serializer);
		
		JsonSerializer<Game> serializerGame = new GameSerialiser(); // will implement in a second  
		gsonBuilder.registerTypeAdapter(Game.class, serializerGame);
		
		//do this last
		gson=gsonBuilder.excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();		
	}

	
	static class HandSerialiser implements JsonSerializer<Hand> 
	{
		@Override
		public JsonElement serialize(Hand hand, Type type, JsonSerializationContext context) 
		{				
			JsonObject jsonObject = new JsonObject();		
	        JsonElement jsonCards = context.serialize(hand.getCards());
	        jsonObject.add("cards", jsonCards);	            
	    	jsonObject.addProperty("points", hand.getValue());
			return jsonObject;
		}
	}
	
	static class GameSerialiser implements JsonSerializer<Game> 
	{
		@Override
		public JsonElement serialize(Game game, Type type, JsonSerializationContext context) 
		{			
			JsonObject jsonObject = new JsonObject();
			JsonElement playerHand = context.serialize(game.getPlayerHand());
			JsonElement dealerHand = context.serialize(game.getDealerHand());
						
			jsonObject.add("player", playerHand);
			jsonObject.addProperty("playerstatus", game.getStatus(game.getPlayerHand()));
			
			jsonObject.add("dealer", dealerHand);
			jsonObject.addProperty("dealerstatus", game.getStatus(game.getDealerHand()));
			
			jsonObject.addProperty("gameOver", game.isGameOver());
			
			if (game.isGameOver())
			{
				jsonObject.addProperty("winner",  game.hasPlayerWon()?"Player":game.hasDealerWon()?"Dealer":"");				
			}	
			
			return jsonObject;
		}
	}	
}
