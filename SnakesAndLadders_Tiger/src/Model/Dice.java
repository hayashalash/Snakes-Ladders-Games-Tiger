package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Dice {
	private int side;
	

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
	        	   int rollResult = rand.nextInt(n) + 1;
	             
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

}
