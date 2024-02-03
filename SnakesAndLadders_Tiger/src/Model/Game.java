package Model;

import java.util.ArrayList;

public class Game {

	private int GameID;
	private Difficulty type;
	private Dice dice;
	private int playersNum;
	private int gameDuration;
	private ArrayList<Player> players;
	private String winner;
	
	public Game(int gameID, Difficulty type, Dice dice, int playersNum, int gameDuration, ArrayList<Player> players,
			String winner) {
		super();
		GameID = gameID;
		this.type = type;
		this.dice = dice;
		this.playersNum = playersNum;
		this.gameDuration = gameDuration;
		this.players = players;
		this.winner = winner;
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

	public Dice getDice() {
		return dice;
	}

	public void setDice(Dice dice) {
		this.dice = dice;
	}

	public int getPlayersNum() {
		return playersNum;
	}

	public void setPlayersNum(int playersNum) {
		this.playersNum = playersNum;
	}

	public int getGameDuration() {
		return gameDuration;
	}

	public void setGameDuration(int gameDuration) {
		this.gameDuration = gameDuration;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	@Override
	public String toString() {
		return "Game [GameID=" + GameID + ", type=" + type + ", dice=" + dice + ", playersNum=" + playersNum
				+ ", gameDuration=" + gameDuration + ", players=" + players + ", winner=" + winner + "]";
	}
	
}

