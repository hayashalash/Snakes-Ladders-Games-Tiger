package Model;

public class SnakeTile extends Tile {
	private Snake snake;
	
	public SnakeTile(Integer tNum, Integer xCoord, Integer yCoord, Snake snake) {
		super(tNum, TileType.Snake, xCoord, yCoord);
		super.settType(TileType.Snake);
		this.snake = snake;
	}
	
	public Snake getSnake() {
		return snake;
	}

	public void setSnake(Snake snake) {
		this.snake = snake;
	}
	
}
