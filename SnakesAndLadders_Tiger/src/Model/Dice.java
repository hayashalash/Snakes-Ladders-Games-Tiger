package Model;

import java.util.ArrayList;
import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;

public class Dice {
//	private int side; // current result of the dice
//	
//
//	public int getSide() {
//		return side;
//	}
//
//
//	public void setSide(int side) {
//		this.side = side;
//	}

	public static void RollingDiceStartingGame(Game game) {//set orders for the players
		ArrayList<Player> gamePlayers = new ArrayList<>();
		for (Player p : game.getPlayers()) {
			gamePlayers.add(p);
		}
		 Queue<Player> playersOrder = new LinkedList<>();	
		int order =1;//to change the player order in the player class
		while(!gamePlayers.isEmpty()) {//check remain players without order 
			Random random = new Random();
			int r= random.nextInt(gamePlayers.size());
			if(r>0) 
				r=r-1;
			playersOrder.add(gamePlayers.get(r));    //add the player to the queue
			gamePlayers.get(r).setNumberOrder(order);
			gamePlayers.remove(r);	
            order++;//for the player order
		}
		 game.setPlayersOrder(playersOrder);//change the original queue to the random order of play
		 System.out.println(playersOrder);
	}
	
	public static  int RandomNumberGenerator(Difficulty type){
		Random random = new Random();
		if(type==Difficulty.Easy)
			return random.nextInt(8);//the player can move up to 4 steps and 5+6+7 is a question for each difficulty  because the chance is dual statistic
		else if(type==Difficulty.Medium)
			return random.nextInt(13);//the player can move up to 6 steps and 7+8 is a  easy question and middle 9+10 11+12 hard   because the chance is dual statistic
		else // if(type==Difficulty.Hard)
			return random.nextInt(15);//7+8 for easy question, 9+10 normal , 10-14 hard question 
	}
}
