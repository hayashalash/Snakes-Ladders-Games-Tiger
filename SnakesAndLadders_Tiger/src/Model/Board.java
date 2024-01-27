package Model;

import java.util.ArrayList;

public class Board {
	private static int idCounter = 1;
	private Integer boardID;
	private BoardType bType;
	private Integer boardLen; //based on board type - 7 / 10 / 13
	private ArrayList<Integer> snakes;
	private ArrayList<Integer> ladders;
	private ArrayList<Integer> questions;
	private ArrayList<Integer> plusOnes;
	private ArrayList<Integer> surprises;
	private Tile[][] grid;
	
	public Board(BoardType bType, ArrayList<Integer> snakes,
			ArrayList<Integer> ladders, ArrayList<Integer> questions, ArrayList<Integer> plusOnes,
			ArrayList<Integer> surprises) {
		super();
		this.boardID = idCounter++;
		this.bType = bType;
		setBoardLen(bType);
		this.snakes = snakes;
		this.ladders = ladders;
		this.questions = questions;
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

	public ArrayList<Integer> getSnakes() {
		return snakes;
	}

	public void setSnakes(ArrayList<Integer> snakes) {
		this.snakes = snakes;
	}

	public ArrayList<Integer> getLadders() {
		return ladders;
	}

	public void setLadders(ArrayList<Integer> ladders) {
		this.ladders = ladders;
	}

	public ArrayList<Integer> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Integer> questions) {
		this.questions = questions;
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
		//if (this.boardLen != 1) //if boardLen is 1, it hasn't been initialized yet - boardLen can only be 7 / 10 / 13
			return grid;
	}

	public void setGrid(Tile[][] grid) {
		this.grid = grid;
	}
	
	
}
