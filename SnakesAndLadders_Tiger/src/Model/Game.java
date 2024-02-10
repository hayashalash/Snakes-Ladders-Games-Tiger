package Model;

import java.sql.Date;
import java.util.ArrayList;

public class Game {

	private static int idCounter = 1;
	private int GameID;
	private Difficulty type;
	private Date date;
	private int playersNum;
	private Double gameDuration;
	private ArrayList<Player> players;
	private Player winner;
	
	public Game(Difficulty type, ArrayList<Player> players) {
		super();
		GameID = idCounter++;
		this.type = type;
		this.playersNum = players.size();
		this.players = players;
	}


	public int getGameID() {
		return GameID;
	}

	public void setGameID(int gameID) {
		GameID = gameID;
	}

	public Difficulty getType() {
		return type;
	}

	public void setType(Difficulty type) {
		this.type = type;
	}

	public int getPlayersNum() {
		return playersNum;
	}

	public void setPlayersNum(int playersNum) {
		this.playersNum = playersNum;
	}

	public Double getGameDuration() {
		return gameDuration;
	}

	public void setGameDuration(Double gameDuration) {
		this.gameDuration = gameDuration;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public final Date getDate() {
		return date;
	}

	public final void setDate(Date date) {
		this.date = date;
	}


	
}

