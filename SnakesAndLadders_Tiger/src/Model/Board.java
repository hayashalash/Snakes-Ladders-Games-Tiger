package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	private static int idCounter = 1;
	private Integer boardID;
	private Difficulty bType;
	private Integer boardLen; // equals width, based on board type - 7 / 10 / 13
	private int boardSize; //based on board type - 49 / 100 / 169

	private ArrayList<Integer> plusOnes; // tiles that provide an additional turn
	private ArrayList<Integer> surprises; // tiles with surprises [+10/-10]
	private Tile[][] grid;
	private HashMap<Integer, Tile> tiles; // all the board tiles easily retrieved by their number
	private HashMap<Integer, Snake> snakes; // all the snakes and the tile with their head
	private HashMap<Integer, Ladder> ladders; // all the ladder and the tile with their top
	private HashMap<Integer, Question> easyQuestions; // tiles with easy questions on them
	private HashMap<Integer, Question> mediumQuestions; // tiles with medium questions on them
	private HashMap<Integer, Question> hardQuestions; // tiles with hard questions on them
	private HashMap<Integer, Player> playerOn; // where the player is placed on the board
	
	public Board(Difficulty bType) {
		super();
		this.boardID = idCounter++;
		this.bType = bType;
		setBoardLen(bType);
		setBoardSize(boardLen);
		this.grid = new Tile[boardLen][boardLen];
		plusOnes = new ArrayList<>();
		surprises = new ArrayList<>();
		tiles = new HashMap<>();
		snakes = new HashMap<>();
		ladders = new HashMap<>();
		easyQuestions = new HashMap<>();
		mediumQuestions = new HashMap<>();
		hardQuestions = new HashMap<>();
		playerOn = new HashMap<>();
	}

	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		Board.idCounter = idCounter;
	}

	public Integer getBoardID() {
		return boardID;
	}

	public void setBoardID(Integer boardID) {
		this.boardID = boardID;
	}

	public Difficulty getbType() {
		return bType;
	}

	public void setbType(Difficulty bType) {
		this.bType = bType;
		setBoardLen(bType); // board length is set based on the board level
		setBoardSize(boardLen); // board size is set based on the board length
	}

	public Integer getBoardLen() {
		return boardLen;
	}

	public void setBoardLen(Difficulty bType) {
		if (bType == Difficulty.Easy)
			this.boardLen = 7;
		if (bType == Difficulty.Medium)
			this.boardLen = 10;
		if (bType == Difficulty.Hard)
			this.boardLen = 13;	
	}

	public ArrayList<Integer> getPlusOnes() {
		return plusOnes;
	}

	public void setPlusOnes(ArrayList<Integer> plusOnes) {
		this.plusOnes = plusOnes;
	}

	public ArrayList<Integer> getSurprises() {
		return surprises;
	}

	public void setSurprises(ArrayList<Integer> surprises) {
		this.surprises = surprises;
	}

	public Tile[][] getGrid() {
			return grid;
	}

	public void setGrid(Tile[][] grid) {
		this.grid = grid;
	}
 
	public HashMap<Integer, Tile> getTiles() {
		return tiles;
	}

	public void setTiles(HashMap<Integer, Tile> tiles) {
		this.tiles = tiles;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardLen) {
		this.boardSize = boardLen*boardLen;
	}

	public HashMap<Integer, Player> getPlayerOn() {
		return playerOn;
	}

	public void setPlayerOn(HashMap<Integer, Player> playerOn) {
		this.playerOn = playerOn;
	}

	public HashMap<Integer, Snake> getSnakes() {
		return snakes;
	}

	public void setSnakes(HashMap<Integer, Snake> snakes) {
		this.snakes = snakes;
	}

	public HashMap<Integer, Ladder> getLadders() {
		return ladders;
	}

	public void setLadders(HashMap<Integer, Ladder> ladders) {
		this.ladders = ladders;
	}

	public HashMap<Integer, Question> getEasyQuestions() {
		return easyQuestions;
	}

	public void setEasyQuestions(HashMap<Integer, Question> easyQuestions) {
		this.easyQuestions = easyQuestions;
	}

	public HashMap<Integer, Question> getMediumQuestions() {
		return mediumQuestions;
	}

	public void setMediumQuestions(HashMap<Integer, Question> mediumQuestions) {
		this.mediumQuestions = mediumQuestions;
	}

	public HashMap<Integer, Question> getHardQuestions() {
		return hardQuestions;
	}

	public void setHardQuestions(HashMap<Integer, Question> hardQuestions) {
		this.hardQuestions = hardQuestions;
	}

	public Tile getTile(int id) {
		return getTiles().get(id);
	}
	
	public void importQuestions () {
		for (Question q : SysData.getInstance().getQuestions()) {
		    addQuestion(q);
		}
	}
	
	public boolean addQuestion(Question q) {
		if(q == null || getEasyQuestions().containsKey(q.getQuestionID()) || getMediumQuestions().containsKey(q.getQuestionID()) || getHardQuestions().containsKey(q.getQuestionID()))
			return false;
		if (q.getDifficulty() == Difficulty.Easy)
			return getEasyQuestions().put(q.getQuestionID(), q) == null;
		else if (q.getDifficulty() == Difficulty.Medium) 
			return getMediumQuestions().put(q.getQuestionID(), q) == null;
		else // if difficulty isn't easy/medium - it is hard
			return getHardQuestions().put(q.getQuestionID(), q) == null;
	}

	public void createBoard() {
		int boardCounter = 1;
		int i = boardLen-1;
		while (i>=0) {
			for (int j = 0 ; j < boardLen ; j++) {
				this.grid[i][j] = new Tile(boardCounter++, i, j);
				getTiles().put(this.grid[i][j].gettNum(), this.grid[i][j]);
				if (j==boardLen-1) {
					i--;
					for (j = boardLen-1 ; j >= 0 ; j--) {
						this.grid[i][j] = new Tile(boardCounter++, i, j);
						getTiles().put(this.grid[i][j].gettNum(), this.grid[i][j]);
						if (j==0)
							i--;
					}
				}
			}
		}

		for (i=0 ; i < 3 ; i++) { // a board has 3 question tiles - one of each difficulty
			int random = (int) (Math.random() * (boardSize-1)) + 1;
			QuestionTile qt = (QuestionTile) getTile(random); // turn this randomly chosen tile from the board to a question tile
			if (i==0)
				qt.getQuestion().setDifficulty(Difficulty.Easy);
			if (i==1)
				qt.getQuestion().setDifficulty(Difficulty.Medium);
			if (i==2)
				qt.getQuestion().setDifficulty(Difficulty.Hard);
			this.grid[qt.xCoord][qt.yCoord] = qt; // put the question tile back in the board
		}
		
		
	}
}
