package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class QuestionFactory {

	// Design Pattern #1 - Factory Method
	
	public Question returnQuestion(Difficulty difficulty) {
    	HashSet<Question> questions = SysData.getInstance().getQuestions();
        HashMap<Difficulty, ArrayList<Question>> questionMap = new HashMap<>();

        // Initialize ArrayList for each difficulty
        for (Question question : questions) {
            Difficulty diff = question.getDifficulty();
            ArrayList<Question> q = questionMap.getOrDefault(diff, new ArrayList<>());
            q.add(question);
            questionMap.put(diff, q);
        }
        Random random = new Random();
        int r = random.nextInt(questionMap.get(difficulty).size()); // choose a random question from the given difficulty
        // Ensure that the ArrayList for the specified difficulty is not null and contains questions
        while (questionMap.get(difficulty).get(r) == null) {
            r = random.nextInt(questionMap.get(difficulty).size());
        }
        System.out.println("I finished returnQuestion");
        return questionMap.get(difficulty).get(r);
    }
}
