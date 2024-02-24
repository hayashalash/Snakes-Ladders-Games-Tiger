package Model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javafx.util.Duration;

public class Game extends Sort{

	private static int idCounter = 1;
	private int GameID;
	private Difficulty type;
	private LocalDate date;
	private int playersNum;
	private Duration gameDuration = Duration.ZERO;
	private ArrayList<Player> players = new ArrayList<>();//should delete?
	private Player winner;
	private Queue<Player> playersOrder = new LinkedList<Player>();
	
	public Game(Difficulty type, ArrayList<Player> players, LocalDate date) {
		super();
		GameID = idCounter++;
		this.type = type;
		this.playersNum = players.size();
		this.players = players;
		this.date = date;
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

	public Duration getGameDuration() {
		return gameDuration;
	}

	public void setGameDuration(Duration gameDuration) {
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

	public final LocalDate getDate() {
		return date;
	}

	public final void setDate(LocalDate date) {
		this.date = date;
	}


	public Queue<Player> getPlayersOrder() {
		return playersOrder;
	}


	public void setPlayersOrder(Queue<Player> playersOrder) {
		this.playersOrder = playersOrder;
	}
    public void changeplayersorder() {
    	Player p=playersOrder.poll();
    	playersOrder.add(p);
    }


	@Override
	public ArrayList<Object> getSorted(String sortedBy) {
		// TODO Auto-generated method stub
		//sort by dificulty or by duration based on the string received
		return null;
	}
	
}

