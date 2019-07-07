# BlackJack

This is a BlackJack simulator to be run from any browser using bj.js.  It has as Spring/Rest (Jax-RS) API back-end with some google GSON sprinkled throughout for serialization help.  It is a bit old school but was my first attempt at some gaming fun.

Below is the game design info:
● Single Player vs. Dealer (computer)
● Player starts with $100 and can bet $5 bet per hand.
● One standard 52-card deck
● Dealer shuffles before each game
● Dealer Hits on Soft 17

To Run
-------
1)  Run this project (I used Intellij)
2)  Run bj.js and try to beat the computer.

You can also hit the rest endpoints below from any browser to see endpoints in action.

http://localhost:8080/bj/rest/BlackJackService/start 
http://localhost:8080/bj/rest/BlackJackService/stand 
http://localhost:8080/bj/rest/BlackJackService/deal 

Enjoy!
