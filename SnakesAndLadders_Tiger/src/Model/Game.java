package Model;

import java.util.ArrayList;

public class Game {

	private static int idCounter = 1;
	private int GameID;
	private Difficulty type;
	//private Dice dice;
	private int playersNum;
	private int gameDuration = 0;
	private ArrayList<Player> players;
	private Player winner;
	
	public Game(Difficulty type, int playersNum, ArrayList<Player> players,	Player winner) {
		super();
		GameID = idCounter++;
		this.type = type;
		//this.dice = dice;
		this.playersNum = playersNum;
		//this.gameDuration = gameDuration;
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

//	public Dice getDice() {
//		return dice;
//	}
//
//	public void setDice(Dice dice) {
//		this.dice = dice;
//	}

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

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

//	@Override
//	public String toString() {
//		return "Game [GameID=" + GameID + ", type=" + type + ", dice=" + dice + ", playersNum=" + playersNum
//				+ ", gameDuration=" + gameDuration + ", players=" + players + ", winner=" + winner + "]";
//	}
	
}

