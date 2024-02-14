 package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	static final int minItemLen = 0;
	static final int maxItemLen = 8;
	static final int yellowSnakeLen = 1;
	static final int greenSnakeLen = 2;
	static final int blueSnakeLen = 3;
	static final int redSnakeLen = 0;
	private static int idCounter = 1;
	private Integer boardID;
	private Difficulty bType;
	private Integer boardLen; // equals width, based on board type - 7 / 10 / 13
	private int boardSize; //based on board type - 49 / 100 / 169
	private int lastTile;
	
	private ArrayList<Integer> surprises; // tiles with surprises [+10/-10]
	private Tile[][] grid;
	private HashMap<Integer, Tile> tiles; // all the board tiles easily retrieved by their number
	private HashMap<Integer, Snake> snakes; // all the snakes and the tile with their head
	private HashMap<Integer, Ladder> ladders; // all the ladder and the tile with their top
	private ArrayList<QuestionTile> questionTiles; // tiles with questions on them
	private ArrayList<Tile> surpriseTiles; // tiles with questions on them
	private HashMap<Integer, Question> easyQuestions; // all the easy questions in the game
	private HashMap<Integer, Question> mediumQuestions; // all the medium questions in the game
	private HashMap<Integer, Question> hardQuestions; // all the hard questions in the game
	private HashMap<Integer, Player> playerOn; // where the player is placed on the board
	
	public Board(Difficulty bType) {
		super();
		this.boardID = idCounter++;
		this.bType = bType;
		setBoardLen(bType);
		setBoardSize(boardLen);
		setLastTile();
		this.grid = new Tile[boardLen][boardLen];
		surprises = new ArrayList<>();
		tiles = new HashMap<>();
		snakes = new HashMap<>();
		ladders = new HashMap<>();
		questionTiles = new ArrayList<>();
		surpriseTiles = new ArrayList<>();
		easyQuestions = new HashMap<>();
		mediumQuestions = new HashMap<>();
		hardQuestions = new HashMap<>();
		playerOn = new HashMap<>();
	}

	public int getLastTile() {
		return lastTile;
	}

	public void setLastTile() {
		this.lastTile = boardLen * boardLen;
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

/*	public boolean createBoard() {
		int boardCounter = 1;
		int i = boardLen-1;
		
		while (i>0) {
			for (int j = 0 ; j < boardLen ; j++) { // go over row from left to right
				System.out.println("["+i+","+j+"]");
				this.grid[i][j] = new Tile(boardCounter++, i, j);
				getTiles().put(this.grid[i][j].gettNum(), this.grid[i][j]);
				if (j==boardLen-1 && i == 0 && (bType == Difficulty.Easy || bType == Difficulty.Hard)) { // in these two board, last tile is on the top right
					j = boardLen; // exit loop as we've reached the last tile
				}
				else if (j==boardLen-1) { // reached end of row
					i--; // go to the next row
					for (j = boardLen-1 ; j >= 0 ; j--) { // go over row from right to left
						System.out.println("["+i+","+j+"]");
						this.grid[i][j] = new Tile(boardCounter++, i, j);
						getTiles().put(this.grid[i][j].gettNum(), this.grid[i][j]);
						if (j==0 && i == 0 && (bType == Difficulty.Medium)) { // in the normal board, last tile is on the top left
							j = boardLen; // exit loop as we've reached the last tile
						}
						else if (j==0) // reached end of row
							i--; // go to the next row
					}
				}
			}
		}
		addSnakeTiles();
		addQuestionTiles(); // adds 3 question tiles to the board
		addSurpriseTiles();
		System.out.println("boardCounter is "+boardCounter);
		System.out.println("boardSize is "+boardSize);
		return (--boardCounter == boardSize); // if all board tiles were successfully added, method will return true, otherwise false
	}*/
	
	public boolean createBoard() {
	    int boardCounter = 1;
	    int i = boardLen - 1;

	    while (i >= 0) { // Changed loop condition to i >= 0
	        for (int j = 0; j < boardLen; j++) { // go over row from left to right
	            System.out.println("[" + i + "," + j + "]");
	            this.grid[i][j] = new Tile(boardCounter++, i, j);
	            getTiles().put(this.grid[i][j].gettNum(), this.grid[i][j]);
	        }
	        i--; // Decrement i after each row iteration
	        if (i >= 0) { // Check if i is still valid before entering the loop again
	            for (int j = boardLen - 1; j >= 0; j--) { // go over row from right to left
	                System.out.println("[" + i + "," + j + "]");
	                this.grid[i][j] = new Tile(boardCounter++, i, j);
	                getTiles().put(this.grid[i][j].gettNum(), this.grid[i][j]);
	            }
	            i--; // Decrement i again after each row iteration
	        }
	    }
	    getTile(lastTile).settType(TileType.LastTile);
	    addSnakeTiles();
	    addQuestionTiles(); // adds 3 question tiles to the board
	    addSurpriseTiles();
	    System.out.println("boardCounter is " + boardCounter);
	    System.out.println("boardSize is " + boardSize);
	    return (--boardCounter == boardSize); // if all board tiles were successfully added, method will return true, otherwise false
	}

	
	public void addQuestionTiles() {
		// boolean retVal = false; //value to return indicating of success 
		for (int i=0 ; i < 3 ; i++) { // a board has 3 question tiles - one of each difficulty
			int random = chooseRandomTile(0); 
			Question q;
			if (i==0) { // in the first iteration add an easy question
				int rand = (int) (Math.random() * (getEasyQuestions().size()));
				q = getEasyQuestions().get(rand);
			}
			else if (i==1) { // in the second iteration add a medium question
				int rand = (int) (Math.random() * (getMediumQuestions().size()));
				q = getMediumQuestions().get(rand);
			}
			else { // if (i==2) - in the third iteration add a hard question
				int rand = (int) (Math.random() * (getHardQuestions().size()));
				q = getHardQuestions().get(rand);
			}
			QuestionTile qt = new QuestionTile(random, getTile(random).getxCoord(), getTile(random).getyCoord(), q);
			this.grid[qt.xCoord][qt.yCoord] = qt; // put the question tile back in the board
			tiles.put(qt.gettNum(), qt); // add the question tile to the tiles HashMap
			questionTiles.add(qt);
		}
	}
	
	public void addSurpriseTiles() {
		if (this.bType == Difficulty.Easy)
			return;
		if (this.bType == Difficulty.Medium || this.bType == Difficulty.Hard) { // add 1 surprise tile to medium/hard boards
			int rand = chooseRandomTile(0);
			getTile(rand).settType(TileType.Surprise);
			surpriseTiles.add(getTile(rand));
		}
		if (this.bType == Difficulty.Hard) { // if board is hard, add another surprise tile
			int random = chooseRandomTile(0);
			getTile(random).settType(TileType.Surprise);
			surpriseTiles.add(getTile(random));
		}
	}
	
//	public Integer chooseRandomTile() {
//		int random = (int) (Math.random() * (boardSize-1)) + 1;
//		while (getTile(random).gettType() != TileType.Classic) { // ensure we don't change a non-classic tile
//			random = (int) (Math.random() * (boardSize-1)) + 1;
//		}
//		return random;
//	}
	
	public Integer chooseRandomTile(int itemLen) {
		int partialSize = boardSize; // the part of the board we will take a random tile from
		int eliminatedRowsSize = 0; // the part of the board that will be eliminated
		if (itemLen < minItemLen || itemLen > maxItemLen)
			return 0;
		if (itemLen > minItemLen) { // [snake head]/[ladder top] cannot be at a tile lower than 'length' number of rows
			eliminatedRowsSize = itemLen * boardLen;
			partialSize = boardSize - eliminatedRowsSize;
		}
		// else if itemLen == 0, item doesn't have a length and therefore no rows are eliminated (item = question/surprise/red snake)
		int random = (int) (Math.random() * (partialSize-1)) + eliminatedRowsSize + 1;
		while (getTile(random).gettType() != TileType.Classic) { // ensure we don't change a non-classic tile
			random = (int) (Math.random() * (partialSize-1)) + eliminatedRowsSize + 1;
		}
		return random;
	}
	
	public Integer chooseRandomInRow (int XrowNum) {
		int random = (int) (Math.random() * (boardLen-1)) + ((XrowNum-1) * boardLen) + 1; // choose a tile  from given row X
		System.out.println("boardLen is: "+boardLen);
		System.out.println("XrowNum is: "+XrowNum);
		System.out.println("random is: "+random);
		while (getTile(random).gettType() != TileType.Classic) { // ensure we don't land on a non-classic tile
			random = (int) (Math.random() * (boardLen-1)) + ((XrowNum-1) * boardLen) + 1;
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
			int snakeBottomRow = getTile(snakeHead).getxCoord()+yellowSnakeLen; 
			snakeTail = chooseRandomInRow (snakeBottomRow);
			System.out.println("yellow snake is on: "+snakeHead+", "+ snakeTail);
			s = new Snake(SnakeColor.Yellow, snakeHead, snakeTail);
		}
		else if (color == SnakeColor.Green) {
			snakeHead = chooseRandomTile(greenSnakeLen);
			int snakeBottomRow = getTile(snakeHead).getxCoord()+greenSnakeLen; 
			snakeTail = chooseRandomInRow (snakeBottomRow);
			System.out.println("green snake is on: "+snakeHead+", "+ snakeTail);
			s = new Snake(SnakeColor.Green, snakeHead, snakeTail);
		}
		else { // if (color == SnakeColor.Blue)
			snakeHead = chooseRandomTile(blueSnakeLen);
			int snakeBottomRow = getTile(snakeHead).getxCoord()+blueSnakeLen; 
			snakeTail = chooseRandomInRow (snakeBottomRow);
			System.out.println("Blue snake is on: "+snakeHead+", "+ snakeTail);
			s = new Snake(SnakeColor.Blue, snakeHead, snakeTail);
		}
		snakes.put(s.getSnakeID(), s);
		SnakeTile st = new SnakeTile (snakeHead, getTile(snakeHead).getxCoord(), getTile(snakeHead).getyCoord(), s); // create a snake tile to replace the regular tile
		getTile(snakeTail).settType(TileType.SnakeTail);
		this.grid[st.xCoord][st.yCoord] = st; // put the snake tile back in the board
		tiles.put(st.gettNum(), st); // add the snake tile to the tiles HashMap
	}
	
	public int getRows() {
	    return grid.length; // Number of rows is the length of the grid array
	}

	public int getColumns() {
	    return grid.length > 0 ? grid[0].length : 0; // Number of columns is the length of the first row of the grid array
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
		int ladderBottomRow = getTile(laddertop).getxCoord()+length;
		int ladderbottom = chooseRandomInRow (ladderBottomRow);
		Ladder l = new Ladder(length, laddertop, ladderbottom);
		ladders.put(l.getLadderID(), l);
		LadderTile lt = new LadderTile (ladderbottom, getTile(ladderbottom).getxCoord(), getTile(ladderbottom).getyCoord(), l); // create a ladder tile to replace the regular tile
		getTile(laddertop).settType(TileType.LadderTop);
		this.grid[lt.xCoord][lt.yCoord] = lt; // put the ladder tile back in the board
		tiles.put(lt.gettNum(), lt); // add the ladder tile to the tiles HashMap
	}
}
