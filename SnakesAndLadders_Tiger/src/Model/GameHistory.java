package Model;

import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;

public class GameHistory { 
	
	private int gameID;
	private Player winner;
	private Difficulty diff;
	private String duration;
	
	public GameHistory() {
		super();
		// TODO Auto-generated constructor stub
	}


	public GameHistory(int gameID, Player winner, Difficulty diff, String duration) {
		super();
		this.gameID = gameID;
		this.winner = winner;
		this.diff = diff;
		this.duration = duration;
	}



	public int getGameID() {
		return gameID;
	}



	public void setGameID(int gameID) {
		this.gameID = gameID;
	}


	public Player getWinner() {
		return winner;
	}



	public void setWinner(Player winner) {
		this.winner = winner;
	}



	public String getDuration() {
		return duration;
	}


	public void setDuration(String duration) {
		this.duration = duration;
	}


	public Difficulty getDiff() {
		return diff;
	}


	public void setDiff(Difficulty diff) {
		this.diff = diff;
	}



	@Override
	public int hashCode() {
		return Objects.hash(duration, diff, gameID, winner);
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
		return Objects.equals(duration, other.duration) && diff == other.diff && gameID == other.gameID
				&& Objects.equals(winner, other.winner);
	}




}