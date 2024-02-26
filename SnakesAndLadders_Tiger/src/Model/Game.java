package Model;

import java.sql.Date;
import javafx.util.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;


public class Game extends Sort{

	private static int idCounter = 1;
	private int GameID;
	private Difficulty type;
	private LocalDate date;
	private int playersNum;
//	private Duration gameDuration = Duration.ZERO;
	private String gameDuration = null;
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

	public Game(Difficulty dif, LocalDate date, String dur, Player winner) { // constructor to use for game history loading from Json
		this.type = dif;
		this.date = date;
		this.gameDuration = dur;
		this.winner = winner;
	}
	public Game() {
		super();
	}

	public int getGameID() {
		return GameID;
	}

	public void setGameID(int gameID) {
		GameID = gameID;
	}

	public Difficulty getDifficulty() {
		return type;
	}

	public void setDifficulty(Difficulty type) {
		this.type = type;
	}

	public int getPlayersNum() {
		return playersNum;
	}

	public void setPlayersNum(int playersNum) {
		this.playersNum = playersNum;
	}

	public String getGameDuration() {
		return gameDuration;
	}

	public void setGameDuration(String gameDuration) {
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
	public int hashCode() {
		return Objects.hash(GameID, date, gameDuration, players, playersNum, playersOrder, type, winner);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		return GameID == other.GameID && Objects.equals(date, other.date)
				&& Objects.equals(gameDuration, other.gameDuration) && Objects.equals(players, other.players)
				&& playersNum == other.playersNum && Objects.equals(playersOrder, other.playersOrder)
				&& type == other.type && Objects.equals(winner, other.winner);
	}

	@Override
    public ArrayList<Object> getSorted(String sortedBy) {
        HashSet<Game> games = new HashSet<>(SysData.getInstance().getGames());
        ArrayList<Object> sorted = new ArrayList<>();

        if (sortedBy.equals("Duration")) {
            sorted.addAll(sortByDuration(games));
        } else if (sortedBy.equals("Difficulty")) {
            sorted.addAll(sortByDifficulty(games));
        } else if (sortedBy.equals("Date")) {
            sorted.addAll(sortByDate(games));
        }

        return sorted;
    }

    private ArrayList<Game> sortByDate(HashSet<Game> games) {//sort the  games based on the date .
        return games.stream()
                .sorted(Comparator.comparing(Game::getDate))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Game> sortByDifficulty(HashSet<Game> games) {//sort the  games based on the Difficulty.
        ArrayList<Game> sorted = new ArrayList<>();
        ArrayList<Game> hard = new ArrayList<>();
        ArrayList<Game> medium = new ArrayList<>();
        ArrayList<Game> easy = new ArrayList<>();
        for(Game g : games) {
        	if(g.getDifficulty()==Difficulty.Easy) {
        		easy.add(g);
        	}
        	else if(g.getDifficulty()==Difficulty.Medium) {
        		medium.add(g);
        	}
        	else hard.add(g);
        }
        sorted.addAll(easy);
        sorted.addAll(medium);
        sorted.addAll(hard);
        
      return sorted;
    }

    private ArrayList<Game> sortByDuration(HashSet<Game> games) {//sort the  games based on the duration game .
        return games.stream()
                .sorted(Comparator.comparing(Game::getGameDuration))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
	// Custom method to format duration as "mm:ss" string
    public static String formatDuration(Duration duration) {
//        long minutes = duration.toMinutes();
//        long seconds = duration.getSeconds() % 60;
	    long totalSeconds = (long) duration.toSeconds();
	    long minutes = totalSeconds / 60;
	    long seconds = totalSeconds % 60;
	    // Convert long values to String with two digits
	    String minutesString = String.format("%02d", minutes);
	    String secondsString = String.format("%02d", seconds);
	    String formattedTime = minutesString + " : " + secondsString;
        return formattedTime;

//        return String.format("%02d:%02d", formattedTime);
    }
 // Custom method to parse duration string in "mm:ss" format and convert to JavaFX Duration
    public static Duration parseDuration(String durationString) {
        String[] parts = durationString.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid duration format: " + durationString);
        }
        try {
            long minutes = Long.parseLong(parts[0]);
            long seconds = Long.parseLong(parts[1]);
            long totalMillis = (minutes * 60 + seconds) * 1000;
            return new Duration(totalMillis);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid duration format: " + durationString, e);
        }
    }
 // Custom method to parse duration string in "mm:ss" format and convert to Duration
//    public static Duration parseDuration(String durationString) {
//        String[] parts = durationString.split(":");
//        if (parts.length != 2) {
//            throw new IllegalArgumentException("Invalid duration format: " + durationString);
//        }
//        try {
//            long minutes = Long.parseLong(parts[0]);
//            long seconds = Long.parseLong(parts[1]);
//            return Duration.toMinutes(minutes).plusSeconds(seconds);
//        } catch (NumberFormatException e) {
//            throw new IllegalArgumentException("Invalid duration format: " + durationString, e);
//        }
//    }

	@Override
	public String toString() {
		return "Game [type=" + type + ", date=" + date + ", gameDuration=" + gameDuration + ", winner=" + winner.getPlayerName() +"]";
	}

	
}

/*	private List<String> sortByWinner(HashSet<Game> games) {
        Map<String, Long> playerWinsCount = games.stream()
                .filter(game -> game.getWinner() != null)
                .collect(Collectors.groupingBy(Game::getWinner, Collectors.counting()));

        return playerWinsCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());}
  */  

	


