

function newgame(){
	enablePlayButtons();
	clearCards();
	doProcess("start");
}

function clearCards() {
	var player=document.getElementById("player");
	var dealer=document.getElementById("dealer");

	clearDivs(player);
	clearDivs(dealer);

	player.innerHTML = '&nbsp';
	dealer.innerHTML = '&nbsp';
}

function clearDivs(masterDiv){

 	while (masterDiv.firstChild)
 	{
 		masterDiv.removeChild(masterDiv.firstChild);
 	}
}

function hit(){
	clearCards();
	doProcess("deal");
}

function stand(){
	clearCards();
	doProcess("stand");
}
function disablePlayButtons(){
	document.getElementById("standbutton").disabled = true;
	document.getElementById("hitbutton").disabled = true;
}

function setNewGameButtonStatus(flag){
	document.getElementById("newgamebutton").disabled = !flag;
}

function enablePlayButtons(){
	document.getElementById("standbutton").disabled = false;
	document.getElementById("hitbutton").disabled = false;
}

function getSuit(suitInput){
	var suit=suitInput;
	switch(suitInput) {
	  case 'DIAMONDS':
		suit='diamond';
		break;
	  case 'CLUBS':
		suit='club';
		break;
	  case 'SPADES':
		suit='spade';
		break;
	  case 'HEARTS':
		suit='heart';
		break;
	}
	return suit;
}

function getFaceValue(cardValue, cardName){
	var faceValue=cardValue;

	switch(cardName) {
	  case 'ACE':
		faceValue='A';
		break;
	  case 'KING':
		faceValue='K';
		break;
	  case 'QUEEN':
		faceValue='Q';
		break;
	  case 'JACK':
		faceValue='J';
		break;
	}
	return faceValue;
}

function addAChild (divPlayer, suit, cardFace) {
	var divCol = document.createElement('div');
	divCol.setAttribute('class', 'column');

	var divCard = document.createElement('div');
	divCard.setAttribute('class', 'card');

	var imgSuit = document.createElement('img');
	imgSuit.setAttribute('src', suit);

	divCard.innerHTML = cardFace;

	divCard.appendChild(imgSuit);
	divCol.appendChild(divCard);
	divPlayer.appendChild(divCol);
 }

function doProcess(appender){
	var request = new XMLHttpRequest();


	request.open('PUT', 'http://localhost:8080/bj/rest/bj/'+appender, true);

	request.onreadystatechange = function()
	{
		if (this.readyState == 4 && this.status == 200)
		{
			var data = JSON.parse(this.response);
			var game=data.game;
			var player=game.player;
			var dealer=game.dealer;

			var hand = document.getElementById('game.player');
			var playerPoints;
			var dealerPoints;

			for (var i = 0; i < player.cards.length; i++)
			{
				var card=player.cards[i];

				var faceValue=getFaceValue(card.value,card.name);
				var suit=getSuit(card.suit);
				addAChild(document.getElementById("player"),suit+".jpg",faceValue);
			}
			playerPoints=player.points;

			//------------------------------------------------------
			//now do dealer cards
			for (var i = 0; i < dealer.cards.length; i++)
			{
				var card=dealer.cards[i];
				var faceValue=getFaceValue(card.value,card.name);
				var suit=getSuit(card.suit);
				addAChild(document.getElementById("dealer"),suit+".jpg",faceValue);
			}
			dealerPoints=dealer.points;

			var gameWinner=game.winner;

			if (typeof gameWinner == 'undefined') winner='';
			else if (gameWinner=='') winner='Winner: PUSH';
			else if (gameWinner=='Dealer') winner='Winner: Dealer, You Lose';
			else if (gameWinner=='Player') winner='Winner, Winner, Chicken Dinner';

			var dealerStatus='';
			if (game.dealerstatus!='') dealerStatus='Dealer Status: Dealer has '+game.dealerstatus;
			var playerStatus='';
			if (game.playerstatus!='') playerStatus='Player Status: Player has '+game.playerstatus;


			document.getElementById("status").innerHTML="Dealer Points="+dealerPoints + "<br />" + "Player Points="+playerPoints + "<br />" +
			  dealerStatus+ "<br />" + playerStatus+ "<br />"+  winner+"<br />"+ "Winnings:"+data.winnings + "<br />"+ "Num Games:"+data.numGames;


			setNewGameButtonStatus(game.gameOver);

			if (winner != '')
			{
				disablePlayButtons();
			}

		}};

	request.send();
}