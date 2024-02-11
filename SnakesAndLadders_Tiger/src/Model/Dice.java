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


	public int RollingDiceStartingGame(Player p) {
		Random random = new Random();
		return random.nextInt(4);
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
	
	
}
