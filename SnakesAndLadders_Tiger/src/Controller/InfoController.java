package Controller;

public class InfoController {
	    private View.infoStage infoView;

	    public void GameController(View.infoStage infoView) {
	        this.infoView = infoView;
	    }

	    public void startGame() {
	        // Get players name from the view 
	        String playerName = infoView.getPlayerName();

	        // Create a new Player object with the provided name
	        Player player = new Player(playerName);

	        // Initialize the game with the player's information
	        initializeGame(player);
	    }
}
