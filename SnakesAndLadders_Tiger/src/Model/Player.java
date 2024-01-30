package Model;

public class Player {
	private int playerID;
	private String playerName;
	private Color playerColor;
	private int playerPlace=1;
	private int hasStarted=0;
	
	public Player(int playerId, String playerName, Color playerColor) {
		super();
		this.playerID = playerID;
		this.playerName = playerName;
		this.playerColor = playerColor;
		hasStarted=1;
	}
	
	public void printPlayerAddedSuccess() {
        System.out.println("New player" + playerName + "added successfully!");
    }

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Color getPlayerColor() {
		return playerColor;
	}

	public void setPlayerColor(Color playerColor) {
		this.playerColor = playerColor;
	}

	public int getPlayerPlace() {
		return playerPlace;
	}

	public void setPlayerPlace(int playerPlace) {
		this.playerPlace = playerPlace;
	}

	public int getHasStarted() {
		return hasStarted;
	}

	public void setHasStarted(int hasStarted) {
		this.hasStarted = hasStarted;
	}

	@Override
	public String toString() {
		return "Player [playerID=" + playerID + ", playerName=" + playerName + ", playerColor=" + playerColor
				+ ", playerPlace=" + playerPlace + ", hasStarted=" + hasStarted + "]";
	}
}
