package Model;

public class QuestionTile extends Tile {
	private Question question;

	public QuestionTile(Integer tNum, Integer xCoord, Integer yCoord, Question question) {
		super(tNum, xCoord, yCoord);
		super.settType(TileType.Question);
		this.question = question;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
}
