package Model;

public class QuestionTile extends Tile{

	private Difficulty questionDiff;

	public QuestionTile(Integer tNum, Integer xCoord, Integer yCoord, Difficulty dif) {
		super(tNum, xCoord, yCoord);
		super.settType(TileType.Question);
		this.questionDiff = dif;
	}

	public Difficulty getQuestionDiff() {
		return questionDiff;
	}

	public void setQuestionDiff(Difficulty questionDiff) {
		this.questionDiff = questionDiff;
	}

}
