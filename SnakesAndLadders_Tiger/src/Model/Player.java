package Model;

public class Player {
	private int playerId;
	private String playerName;
	private Color playerColor;
	private int playerPlace=1;
	private int hasStarted=0;
	
	public Player(int playerId, String playerName, Color playerColor) {
		super();
		this.playerId = playerId;
		this.playerName = playerName;
		this.playerColor = playerColor;
	}
	
	public void printPlayerAddedSuccess() {
        System.out.println("New player" + playerName + "added successfully!");
    }

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
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
		return "Player [playerId=" + playerId + ", playerName=" + playerName + ", playerColor=" + playerColor
				+ ", playerPlace=" + playerPlace + ", hasStarted=" + hasStarted + "]";
	}
}
