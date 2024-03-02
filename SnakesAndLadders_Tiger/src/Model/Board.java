package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Board {
	static final int minItemLen = -1;
	static final int maxItemLen = 8;
	static final int surpriseTile = -1;
	static final int yellowSnakeLen = 1;
	static final int greenSnakeLen = 2;
	static final int blueSnakeLen = 3;
	static final int redSnakeLen = 0;
	private static int idCounter = 1;
	private Integer boardID;
	private Difficulty bType;
	private Integer boardLen; // equals width, based on board type - 7 / 10 / 13
	private int boardSize; //based on board type - 49 / 100 / 169
	private ArrayList<Integer> surprises; // tiles with surprises [+10/-10]
	private HashMap<Integer, Tile> tiles; // all the board tiles easily retrieved by their number
	private HashMap<Integer, Snake> snakes; // all the snakes and the tile with their head
	private HashMap<Integer, Ladder> ladders; // all the ladder and the tile with their top
	private ArrayList<QuestionTile> questionTiles; // tiles with questions on them
	private ArrayList<Tile> surpriseTiles; // tiles with surprises on them
	public HashMap<Integer, Player> playerOn; // where the player is placed on the board
	
	public Board(Difficulty bType) {
		super();
		this.boardID = idCounter++;
		this.bType = bType;
		setBoardLen(bType);
		setBoardSize(boardLen);
//		this.grid = new Tile[boardLen][boardLen];
		surprises = new ArrayList<>();
		tiles = new HashMap<>();
		snakes = new HashMap<>();
		ladders = new HashMap<>();
		questionTiles = new ArrayList<>();
		surpriseTiles = new ArrayList<>();
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

	public ArrayList<Integer> getSurprises() {
		return surprises;
	}

	public void setSurprises(ArrayList<Integer> surprises) {
		this.surprises = surprises;
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

	public ArrayList<QuestionTile> getQuestionTiles() {
		return questionTiles;
	}

	public void setQuestionTiles(ArrayList<QuestionTile> questionTiles) {
		this.questionTiles = questionTiles;
	}

	public ArrayList<Tile> getSurpriseTiles() {
		return surpriseTiles;
	}

	public void setSurpriseTiles(ArrayList<Tile> surpriseTiles) {
		this.surpriseTiles = surpriseTiles;
	}

	public Tile getTile(int id) {
		return getTiles().get(id);
	}
	
	public boolean createBoard() {
	    int boardCounter = 1;
	    int i = boardLen - 1;

	    while (i >= 0) { // Changed loop condition to i >= 0
	        for (int j = 0; j < boardLen; j++) { // go over row from left to right
	        	Tile tile = new Tile(boardCounter++, i, j);
                getTiles().put(tile.gettNum(), tile);
	        }
	        i--; // Decrement i after each row iteration
	        if (i >= 0) { // Check if i is still valid before entering the loop again
	            for (int j = boardLen - 1; j >= 0; j--) { // go over row from right to left
	            	Tile tile = new Tile(boardCounter++, i, j);
	                getTiles().put(tile.gettNum(), tile);
	            }
	            i--; // Decrement i again after each row iteration
	        }
	    }
	    getTile(boardLen * boardLen).settType(TileType.LastTile);
	    getTile(1).settType(TileType.FirstTile);
	    addSnakeTiles();
	    addQuestionTiles(); // adds 3 question tiles to the board
	    addLadderTiles();
	    addSurpriseTiles();
	    return (--boardCounter == boardSize); // if all board tiles were successfully added, method will return true, otherwise false
	}

	
	public void addQuestionTiles() {
		for (int i=0 ; i < 3 ; i++) { // a board has 3 question tiles - one of each difficulty
			int random = chooseRandomTile(0); 
			getTile(random).settType(TileType.Question);
			QuestionTile qt;
			if (i==0) { // in the first iteration add an easy question
				qt = new QuestionTile(random, getTile(random).getRow(), getTile(random).getColumn(), Difficulty.Easy);
			}
			else if (i==1) { // in the second iteration add a medium question
				qt = new QuestionTile(random, getTile(random).getRow(), getTile(random).getColumn(), Difficulty.Medium);
			}
			else { // if (i==2) - in the third iteration add a hard question
				qt = new QuestionTile(random, getTile(random).getRow(), getTile(random).getColumn(), Difficulty.Hard);
			}
			tiles.put(qt.gettNum(), qt); // add the question tile to the tiles HashMap
			questionTiles.add(qt);
		}
	}
	
	public void addSurpriseTiles() {
		if (this.bType == Difficulty.Easy) // No surprise tiles in an easy board
			return;
		if (this.bType == Difficulty.Medium || this.bType == Difficulty.Hard) // add 1 surprise tile to medium/hard boards
			addSurprise();
		if (this.bType == Difficulty.Hard) // if board is hard, add another surprise tile
			addSurprise();
	}
	
	public void addSurprise() {
		int random = chooseRandomTile(surpriseTile);
		// Ensure a surprise tile doesn't move the player to a ladder/snake
		while (getTile(random+10).gettType() == TileType.LadderBottom || getTile(random+10).gettType() == TileType.SnakeHead ||
				getTile(random-10).gettType() == TileType.LadderBottom || getTile(random-10).gettType() == TileType.SnakeHead)
			random = chooseRandomTile(surpriseTile);
					
		getTile(random).settType(TileType.Surprise);
		surpriseTiles.add(getTile(random));
	}
	
	public Integer chooseRandomTile(int itemLen) {
		int partialSize = boardSize; // the part of the board we will take a random tile from
		int eliminatedRowsSize = 0; // the part of the board that will be eliminated
		if (itemLen < minItemLen || itemLen > maxItemLen)
			return 0;
		else if (itemLen > 0) { // [snake head]/[ladder top] cannot be at a tile lower than 'length' number of rows
			eliminatedRowsSize = itemLen * boardLen;
			partialSize = boardSize - eliminatedRowsSize;
		}
		else if (itemLen == surpriseTile) { // surprises must be at least 10 tiles away from beginning/end
			partialSize = boardSize - 20;
			eliminatedRowsSize = 10;
		}
		// else if itemLen == 0, item doesn't have a length and therefore no rows are eliminated (item = question/surprise/red snake)
		int random = (int) (Math.random() * (partialSize-1)) + eliminatedRowsSize + 1;
		while (getTile(random).gettType() != TileType.Classic) { // ensure we don't change a non-classic tile
			random = (int) (Math.random() * (partialSize-1)) + eliminatedRowsSize + 1;
		}
		return random;
	}
	
	public Integer chooseRandomInRow (int XrowNum) {
		int random = (int) (Math.random() * (boardLen-1)) + ((boardLen-1 - XrowNum) * boardLen) + 1; // choose a tile  from given row X
		System.out.println("XrowNum is: "+XrowNum);
		System.out.println("random is: "+random);
		while (getTile(random).gettType() != TileType.Classic) { // ensure we don't land on a non-classic tile
			random = (int) (Math.random() * (boardLen-1)) + ((boardLen-1 - XrowNum) * boardLen) + 1;
		}
		return random;
	}
	
	public void addSnakeTiles() {
		// all board types have at least 4 snakes - one of each color/length
		createSnake(SnakeColor.Red);			
		createSnake(SnakeColor.Yellow);
		createSnake(SnakeColor.Green);
		createSnake(SnakeColor.Blue);
		if (this.bType == Difficulty.Medium || this.bType == Difficulty.Hard) { // add 1 green and 1 red snakes 
			createSnake(SnakeColor.Red);
			createSnake(SnakeColor.Green);
		}
		if (this.bType == Difficulty.Hard) { // add 1 yellow and 1 blue snakes 
			createSnake(SnakeColor.Yellow);
			createSnake(SnakeColor.Blue);
		}
	}
	
	public void createSnake(SnakeColor color) {
		int snakeHead;
		int snakeTail;
		Snake s;
		if (color == SnakeColor.Red) {
			snakeHead = chooseRandomTile(redSnakeLen);
			snakeTail = snakeHead; // in a red snake, head and tail are at the same tile
			System.out.println("Red snake is on: "+snakeHead+", "+ snakeTail);
			s = new Snake(SnakeColor.Red, snakeHead, snakeTail);
		}
		else if (color == SnakeColor.Yellow) {
			snakeHead = chooseRandomTile(yellowSnakeLen);
			int snakeBottomRow = getTile(snakeHead).getRow()+yellowSnakeLen; 
			snakeTail = chooseRandomInRow (snakeBottomRow);
			System.out.println("yellow snake is on: "+snakeHead+", "+ snakeTail);
			s = new Snake(SnakeColor.Yellow, snakeHead, snakeTail);
		}
		else if (color == SnakeColor.Green) {
			snakeHead = chooseRandomTile(greenSnakeLen);
			int snakeBottomRow = getTile(snakeHead).getRow()+greenSnakeLen; 
			snakeTail = chooseRandomInRow (snakeBottomRow);
			System.out.println("green snake is on: "+snakeHead+", "+ snakeTail);
			s = new Snake(SnakeColor.Green, snakeHead, snakeTail);
		}
		else { // if (color == SnakeColor.Blue)
			snakeHead = chooseRandomTile(blueSnakeLen);
			int snakeBottomRow = getTile(snakeHead).getRow()+blueSnakeLen; 
			snakeTail = chooseRandomInRow (snakeBottomRow);
			System.out.println("Blue snake is on: "+snakeHead+", "+ snakeTail);
			s = new Snake(SnakeColor.Blue, snakeHead, snakeTail);
		}
		snakes.put(s.getSnakeID(), s);
		SnakeTile st = new SnakeTile (snakeHead, getTile(snakeHead).getRow(), getTile(snakeHead).getColumn(), s); // create a snake tile to replace the regular tile
		getTile(snakeTail).settType(TileType.SnakeTail);
		tiles.put(st.gettNum(), st); // add the snake tile to the tiles HashMap
	}
	
	public void addLadderTiles () {
		for (int i = 1 ; i <= 4 ; i++) { // all board types have at least 4 ladders - one of each length: [1,2,3,4]
			createLadder(i); // i = ladder length
		}
		if (this.bType == Difficulty.Medium || this.bType == Difficulty.Hard) { // add 2 additional ladders - lengths: [5,6] 
			for (int i = 5 ; i <= 6 ; i++)
				createLadder(i);
		}
		if (this.bType == Difficulty.Hard) { // add 2 additional ladders - lengths: [7,8]  
			for (int i = 7 ; i <= 8 ; i++) 
				createLadder(i);
		}
	}
	
	public void createLadder(int length) {
		int laddertop = chooseRandomTile(length);
		int ladderBottomRow = getTile(laddertop).getRow()+length;
		int ladderbottom = chooseRandomInRow (ladderBottomRow);
		Ladder l = new Ladder(length, laddertop, ladderbottom);
		ladders.put(l.getLadderID(), l);
		LadderTile lt = new LadderTile (ladderbottom, getTile(ladderbottom).getRow(), getTile(ladderbottom).getColumn(), l); // create a ladder tile to replace the regular tile
		getTile(laddertop).settType(TileType.LadderTop);
		tiles.put(lt.gettNum(), lt); // add the ladder tile to the tiles HashMap
		System.out.println("ladder length: " + length);
		System.out.println("Ladder top is on: " + laddertop + " and ladder bottopn is on: " +ladderbottom);
	}
	
}
