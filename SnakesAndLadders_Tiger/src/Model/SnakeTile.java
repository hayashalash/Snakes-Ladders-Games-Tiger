package Model;

public class SnakeTile extends Tile {
	private Snake snake;
	
	public SnakeTile(Integer tNum, Integer xCoord, Integer yCoord, Snake snake) {
		super(tNum, TileType.Snake, xCoord, yCoord);
		this.snake = snake;
	}
	
	public Snake getSnakeID() {
		return snake;
	}

	public void setSnakeID(Snake snake) {
		this.snake = snake;
	}
	
}
