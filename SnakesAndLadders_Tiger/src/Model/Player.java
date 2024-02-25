package Model;

import java.util.Objects;

public class Player {
	private static int idCounter = 1;
	private int playerID;
	private String playerName;
	private Color playerColor;
	private int playerPlace=0;
	private int playerPrevPlace=0; //player previous position
	private int numberOrder=0;
	private int enteredTile=0; // if the player entered their current position (playerPlace) first/second/third/fourth
	
	public Player(String playerName, Color playerColor) {
		super();
		this.playerID = idCounter++;
		this.playerName = playerName;
		this.playerColor = playerColor;
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

	public void setPlayerPrevPlace(int playerPrevPlace) {
		this.playerPrevPlace = playerPrevPlace;
	}
	
	public int getPlayerPrevPlace() {
		return playerPrevPlace;
	}

	public void setPlayerPlace(int playerPlace) {
		this.playerPlace = playerPlace;
	}

	public int getNumberOrder() {
		return numberOrder;
	}

	public void setNumberOrder(int numberOrder) {
		this.numberOrder = numberOrder;
	}
	
	
	public int getEnteredTile() {
		return enteredTile;
	}

	public void setEnteredTile(int enteredTile) {
		this.enteredTile = enteredTile;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numberOrder, playerColor, playerID, playerName, playerPlace);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return numberOrder == other.numberOrder && playerColor == other.playerColor && playerID == other.playerID
				&& Objects.equals(playerName, other.playerName);
	}



	
}
