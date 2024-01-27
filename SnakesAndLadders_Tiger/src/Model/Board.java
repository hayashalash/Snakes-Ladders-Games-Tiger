package Model;

import java.util.ArrayList;

public class Board {
	private static int idCounter = 1;
	private Integer boardID;
	private BoardType bType;
	private Integer boardLen; //based on board type - 7 / 10 / 13
//	private ArrayList<Integer> snakes;
//	private ArrayList<Integer> ladders;
//	private ArrayList<Integer> questions;
	private ArrayList<Integer> plusOnes;
	private ArrayList<Integer> surprises;
	private Tile[][] grid;
	
	public Board(BoardType bType, ArrayList<Integer> plusOnes, ArrayList<Integer> surprises) {
		super();
		this.boardID = idCounter++;
		this.bType = bType;
		setBoardLen(bType);
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
	
	public Tile getTile(int id) {
		// how??
	}
	
	public void createBoard() {
		private int boardCounter = 1;
		private int i = boardLen-1;
		while (i>=0) {
			for (j = 0 ; j < boardLen : j++) {
				this.grid[i][j] = new Tile(boardCounter++, i, j);
				if (j==boardLen-1) {
					i--;
					for (j = boardLen-1 ; j >= 0 ; j--) {
						this.grid[i][j] = new Tile(boardCounter++, i, j);
						if (j==0)
							i--;
					}
				}
			}
		}
		private int boardSize = boardLen*boardLen;
		if (this.bType == BoardType.Easy) {
			for (i=0 ; i < 3 ; i++) {
				
				(Math.random() * (boardSize-1))+1;
			}
		}
	}
}
