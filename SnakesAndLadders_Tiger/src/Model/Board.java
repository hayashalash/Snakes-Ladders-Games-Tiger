package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Board {
	private static int idCounter = 1;
	private Integer boardID;
	private BoardType bType;
	private Integer boardLen; //based on board type - 7 / 10 / 13
	private int boardSize;
//	private ArrayList<Integer> snakes;
//	private ArrayList<Integer> ladders;
//	private ArrayList<Integer> questions;
	private ArrayList<Integer> plusOnes;
	private ArrayList<Integer> surprises;
	private Tile[][] grid;
	private HashMap<Integer, Tile> tiles;
	
	public Board(BoardType bType, ArrayList<Integer> plusOnes, ArrayList<Integer> surprises) {
		super();
		this.boardID = idCounter++;
		this.bType = bType;
		setBoardLen(bType);
		this.boardSize = boardLen*boardLen;
		this.plusOnes = plusOnes;
		this.surprises = surprises;
		this.grid = new Tile[boardLen][boardLen];
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

	public BoardType getbType() {
		return bType;
	}

	public void setbType(BoardType bType) {
		this.bType = bType;
	}

	public Integer getBoardLen() {
		return boardLen;
	}

	public void setBoardLen(BoardType bType) {
		if (bType == BoardType.Easy)
			this.boardLen = 7;
		if (bType == BoardType.Medium)
			this.boardLen = 10;
		if (bType == BoardType.Hard)
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

	public void setBoardLen(Integer boardLen) {
		this.boardLen = boardLen;
	}

	public Tile getTile(int id) {
		return getTiles().get(id);
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
		if (this.bType == BoardType.Easy) {
			for (i=0 ; i < 3 ; i++) { // an easy board has 3 question tiles - one of each difficulty
				int random = (int) (Math.random() * (boardSize-1)) + 1;
				QuestionTile qt = (QuestionTile) getTile(random); // turn this randomly chosen tile from the board to a question tile
//				if (i==0)
//					qt.getQuestion().
				this.grid[qt.xCoord][qt.yCoord] = qt; // put the question tile back in the board
			}
		}
	}
}
