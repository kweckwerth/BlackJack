package com.blackjack;  

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.blackjack.BlackJackHelper.PlayType;
  
@Path("/bj") 


/*
 * This restful jersey servlet controls the blackjack game.  It has 3 entry points, one for 
 * starting the application, dealing a card and standing on a hand.  There is a singleton
 * reference to BlackJack which is to manage the state of the game.
 * 
 * The json that is returned includes all aspects of 2 hands so that front-end can display.
 * Returned values include winners, player winnings, who won if not blank, all cards for both players,
 * hand total, and status.
 */
public class BlackJackService implements Serializable
{   
   @GET  
   @Path("/start4")  
   @Produces(MediaType.APPLICATION_JSON)
   /*
    * Not needed but is a sniffer on existing game for debugging
    */ 
   public Response start4() 
   {
	   BlackJack.getInstance().play(PlayType.START);
	   return Response.status(200).entity(BlackJack.gson.toJson(BlackJack.getInstance())).build();	   
   }       

   @PUT 
   @Path("/start")  
   @Produces("application/json") 
   /*
    * initialize the blackjack game and initialize parameters.  Deal 2 cards out per person.
    */
   public Response start()
   { 
	   BlackJack.getInstance().play(PlayType.START);
	   return Response.status(200).entity(BlackJack.gson.toJson(BlackJack.getInstance())).build();
   }       

   @PUT 
   @Path("/deal")  
   @Produces("application/json")
   /*
    * Deal a card to the player.  Only the player is allowed to deal.  Dealer is auto generated afterwards.
    */
   public Response deal()
   {   
	   BlackJack.getInstance().play(PlayType.DEAL);	   
	   return Response.status(200).entity(BlackJack.gson.toJson(BlackJack.getInstance())).build();
   }         
   
   @PUT 
   @Path("/stand") 
   @Produces("application/json")  
   /*
    * Player stands on his hand.  Auto generate dealer after.
    */
   public Response stand()
   { 
	   BlackJack.getInstance().play(PlayType.STAND);	   
	   return Response.status(200).entity(BlackJack.gson.toJson(BlackJack.getInstance())).build();
   }   
   
  // @POST
//	@Path("/upload")
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	public Response uploadFile(
			//@FormDataParam("file") InputStream uploadedInputStream,
	//		@FormDataParam("file") FormDataContentDisposition fileDetail
//			)
  // {
			
	//		System.out.println("uploading...");
			// check if all form parameters are provided
				
				// create our destination folder, if it not exists
				
		//		final String UPLOAD_FOLDER = "c:/uploadedFiles/";
				
				//String uploadedFileLocation = UPLOAD_FOLDER + fileDetail.getFileName();
				
			//	return Response.status(200).entity("File saved to " + UPLOAD_FOLDER).build();
			
   //}
	
	
}