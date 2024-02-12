package Model;

public class Snake {
	private static int idCounter = 1;
	private Integer snakeID;
	private SnakeColor color;
	private Integer snakeLen;
	private Integer snakeHead;
	private Integer snakeTail;
	
	public Snake(SnakeColor color, Integer snakeHead, Integer snakeTail) {
		super();
		this.snakeID = idCounter++;
		this.color = color;
		setSnakeLen(color); //snakeLen is determined based on its color
		this.snakeHead = snakeHead;
		this.snakeTail = snakeTail;
	}

	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		Snake.idCounter = idCounter;
	}

	public Integer getSnakeID() {
		return snakeID;
	}

	public void setSnakeID(Integer snakeID) {
		this.snakeID = snakeID;
	}

	public SnakeColor getColor() {
		return color;
	}

	public void setColor(SnakeColor color) {
		this.color = color;
		setSnakeLen(color);
	}

	public Integer getSnakeLen() {
		return snakeLen;
	}

	public void setSnakeLen(SnakeColor color) {
		if (color == SnakeColor.Red)
			this.snakeLen = 0;
		if (color == SnakeColor.Yellow)
			this.snakeLen = 1;
		if (color == SnakeColor.Green)
			this.snakeLen = 2;
		if (color == SnakeColor.Blue)
			this.snakeLen = 3;
	}

	public Integer getSnakeHead() {
		return snakeHead;
	}

	public void setSnakeHead(Integer snakeHead) {
		this.snakeHead = snakeHead;
	}

	public Integer getSnakeTail() {
		return snakeTail;
	}

	public void setSnakeTail(Integer snakeTail) {
		this.snakeTail = snakeTail;
	}


	
	
}
