package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Dice {
	private ArrayList<Player> players;
	private int side;
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public int getSide() {
		return side;
	}
	public void setSide(int side) {
		this.side = side;
	}
	@Override
	public int hashCode() {
		return Objects.hash(players, side);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dice other = (Dice) obj;
		return Objects.equals(players, other.players) && side == other.side;
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
