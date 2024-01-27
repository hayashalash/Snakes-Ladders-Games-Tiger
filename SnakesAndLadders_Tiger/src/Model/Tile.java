package Model;

public class Tile {
	private Integer tNum;
	private TileType tType;
	private Integer xCoord;
	private Integer yCoord;
	
	public Tile(Integer tNum, TileType tType, Integer xCoord, Integer yCoord) {
		super();
		this.tNum = tNum;
		this.tType = tType;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}
	
	// default creation of tile is of type classic.
	public Tile(Integer tNum, Integer xCoord, Integer yCoord) {
		super();
		this.tNum = tNum;
		this.tType = TileType.Classic;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}

	public Integer gettNum() {
		return tNum;
	}

	public void settNum(Integer tNum) {
		this.tNum = tNum;
	}

	public TileType gettType() {
		return tType;
	}

	public void settType(TileType tType) {
		this.tType = tType;
	}

	public Integer getxCoord() {
		return xCoord;
	}

	public void setxCoord(Integer xCoord) {
		this.xCoord = xCoord;
	}

	public Integer getyCoord() {
		return yCoord;
	}

	public void setyCoord(Integer yCoord) {
		this.yCoord = yCoord;
	}
	
	
}
