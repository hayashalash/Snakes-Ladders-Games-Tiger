package Model;

public class Ladder {
	private static int idCounter = 1;
	private Integer ladderID;
	private Integer ladderLen;
	private Integer ladderTop; //Tile number of the top of the ladder
	private Integer ladderBottom; //Tile number of the bottom of the ladder
	
	public Ladder(Integer ladderLen, Integer ladderTop, Integer ladderBottom) {
		super();
		this.ladderID = idCounter++;
		this.ladderLen = ladderLen;
		this.ladderTop = ladderTop;
		this.ladderBottom = ladderBottom;
	}

	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		Ladder.idCounter = idCounter;
	}

	public Integer getLadderID() {
		return ladderID;
	}

	public void setLadderID(Integer ladderID) {
		this.ladderID = ladderID;
	}

	public Integer getLadderLen() {
		return ladderLen;
	}

	public void setLadderLen(Integer ladderLen) {
		this.ladderLen = ladderLen;
	}

	public Integer getLadderTop() {
		return ladderTop;
	}

	public void setLadderTop(Integer ladderTop) {
		if (ladderTop > this.ladderBottom) // ladderTop cannot be equal to or smaller than ladderBottom
			this.ladderTop = ladderTop;
	}

	public Integer getLadderBottom() {
		return ladderBottom;
	}

	public void setLadderBottom(Integer ladderBottom) {
		if (ladderBottom < this.ladderTop) // ladderBottom cannot be equal to or bigger than ladderTop
			this.ladderBottom = ladderBottom;
	}
	
	
}
