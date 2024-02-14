package Model;

public class LadderTile extends Tile {
	private Ladder ladder;

	public LadderTile(Integer tNum, Integer xCoord, Integer yCoord, Ladder ladder) {
		super(tNum, xCoord, yCoord);
		super.settType(TileType.LadderBottom);
		this.ladder = ladder;
	}

	public Ladder getLadder() {
		return ladder;
	}

	public void setLadder(Ladder ladder) {
		this.ladder = ladder;
	}
	
}
