package Model;

import java.util.Objects;

public class Answer {

	private static int idCounter = 1;
	private int answerID;
	private String answer;
	// private boolean isCorrect;
	public int getAnswerID() {
		return answerID;
	}
	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Override
	public int hashCode() {
		return Objects.hash(answer, answerID);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Answer other = (Answer) obj;
		return Objects.equals(answer, other.answer) && answerID == other.answerID;
	}
	public Answer(int answerID, String answer) {
		super();
		answerID = idCounter++;
		this.answerID = answerID;
		this.answer = answer;
	}
	public Answer(String answer) {
		super();
		this.answer = answer;
	}
	@Override
	public String toString() {
		return "Answer [answer=" + answer + "]";
	}
	
	
}
