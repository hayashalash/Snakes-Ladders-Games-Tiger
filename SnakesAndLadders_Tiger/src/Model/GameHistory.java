package Model;

import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;

public class GameHistory { 

//	private Player players;
	private Duration date;
	private Difficulty diff;
	private HashMap <Player, Integer> winner ;

	public GameHistory() {
		super();
		// TODO Auto-generated constructor stub
	}


	public GameHistory( Duration date, Difficulty diff, HashMap<Player, Integer> win) {
		super();
		this.date = date;
		this.diff = diff;
		winner = new HashMap<>();
	}


	public HashMap<Player, Integer> getWinner() {
		return winner;
	}



	public void setWinner(HashMap<Player, Integer> winner) {
		this.winner = winner;
	}


	public Duration getDate() {
		return date;
	}


	public void setDate(Duration date) {
		this.date = date;
	}


	public Difficulty getDiff() {
		return diff;
	}


	public void setDiff(Difficulty diff) {
		this.diff = diff;
	}


	@Override
	public int hashCode() {
		return Objects.hash(date, diff, winner);
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameHistory other = (GameHistory) obj;
		return Objects.equals(date, other.date) && diff == other.diff && Objects.equals(winner, other.winner);
	}


}