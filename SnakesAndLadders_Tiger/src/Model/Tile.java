package Model;

public class Tile {
	protected Integer tNum;
	protected TileType tType;
	protected Integer row;
	protected Integer column;
	
	public Tile(Integer tNum, TileType tType, Integer row, Integer column) {
		super();
		this.tNum = tNum;
		this.tType = tType;
		this.row = row;
		this.column = column;
	}
	
	// default creation of tile is of type classic.
	public Tile(Integer tNum, Integer row, Integer column) {
		super();
		this.tNum = tNum;
		this.tType = TileType.Classic;
		this.row = row;
		this.column = column;
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

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}
	
}
