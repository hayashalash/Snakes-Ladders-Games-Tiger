package Model;
import java.util.ArrayList;
import java.util.Objects;


public  class  Question extends Sort implements Comparable<Question>{
	
	//private ArrayList<Answer> answers;
	public static int idCounter = 1;
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private int questionID;
	private String question;
	private Difficulty difficulty;
	private int correctAnswer;

//	private int stepsForward = 0;
//	private int stepsBack = 0;

	public Question(String answer1, String answer2, String answer3, String answer4, String question, Difficulty difficulty,
			int correctAnswer) {
		super();
		this.questionID = idCounter++;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
		this.answer4 = answer4;
		this.question = question;
		this.difficulty = difficulty;
		this.correctAnswer = correctAnswer;
	}
	
	
	public Question(String question, Difficulty difficulty) {
	super();
	this.question = question;
	this.difficulty = difficulty;
}


	public Question(String question) {
		super();
		this.question = question;
	}
	
	public Question() {
		super();
	}
	
	public int getQuestionID() {
		return questionID;
	}
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Difficulty getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
	public int getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public String getAnswer1() {
		return answer1;
	}
	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}
	public String getAnswer2() {
		return answer2;
	}
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}
	public String getAnswer3() {
		return answer3;
	}
	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}
	public String getAnswer4() {
		return answer4;
	}
	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}
	@Override
	public int hashCode() {
		return Objects.hash(question);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		return Objects.equals(question, other.question);
	}
	@Override
	public int compareTo(Question o) {
		return this.question.compareToIgnoreCase(o.getQuestion());
		
	}

	@Override
	public String toString() {
		return "Question [answer1=" + answer1 + ", answer2=" + answer2 + ", answer3=" + answer3 + ", answer4=" + answer4
				+ ", questionID=" + questionID + ", question=" + question + ", difficulty=" + difficulty
				+ ", correctAnswer=" + correctAnswer + "]";
	}


	@Override
	public ArrayList<Object> getSorted(String s) {
        // disregard received value of string (which indicated how to sort) as the questions are only sorted by difficulty
        ArrayList<Question> sortDiff = new ArrayList<>(SysData.getInstance().getQuestions());
        ArrayList<Object> sorted = new ArrayList<>();

        for (Question q : sortDiff) {
            if (q.getDifficulty().equals(Difficulty.Easy)) {
                sorted.add(q);
            }
        }
        for (Question q : sortDiff) {
            if (q.getDifficulty().equals(Difficulty.Medium)) {
                sorted.add(q);
            }
        }
        for (Question q : sortDiff) {
            if (q.getDifficulty().equals(Difficulty.Hard)) {
                sorted.add(q);
            }
        }
        return sorted;
	}
}
