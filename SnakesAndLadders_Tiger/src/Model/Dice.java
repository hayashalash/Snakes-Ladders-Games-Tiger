package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Dice {
	private int side; // current result of the dice
	

	public int getSide() {
		return side;
	}


	public void setSide(int side) {
		this.side = side;
	}


	List<Integer> RollingDiceStartingGame(int n)
	{
	     List<Integer> playingOrder=null;
	     if(n<=1 || n>4)
	        {
	          playingOrder=Collections.emptyList(); // for not throwing the null pointer Exception
	       }
	     else
	        {
	           Random rand = new Random();
	           playingOrder=new ArrayList<Integer>();
	           for(int i=1 ; i<=n ; i++)
	              {
	        	   int rollResult = rand.nextInt(n);
	        	   
	        	   if (rollResult == 6) {
	        		   playingOrder.add(i);
	        	   }
	        	   else {
	        		   i--;
	        	   }	             
	             }     
	         }

	   return  playingOrder;      

	}
	
	public static int RandomNumberGenerator(Difficulty type){
		Random random = new Random();
		if(type==Difficulty.Easy)
        return random.nextInt(7);//the player can move up to 4 steps and 5+6+7 is a question foe each difficulty  because the chance is dual statistic
		else {
			if(type==Difficulty.Medium)
				return random.nextInt(12);//the player can move up to 6 steps and 7+8 is a  easy question and middle 9+10 11+12 hard   because the chance is dual statistic
			else
				if(type==Difficulty.Hard)
				return random.nextInt(14);//7+8 for ease question, 9+10 normal , 10-14 hard question 
	}
		return 0;//will not got to here 
	}
	
	
//	public static int roll(Difficulty type,Player player){
//		
//		
//	}
//	

}
