package Model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import org.json.simple.parser.JSONParser;


public class SysData {
	   //singleton
		private static SysData sysData = null;
	
//	private ArrayList<Question> EasyQuestions; 
//	private ArrayList<Question> MediumQuestions; 
//	private ArrayList<Question> HardQuestions; 
//	private Difficulty d;	
	private ArrayList<Game> games = new ArrayList();
	private ArrayList<Question> questions = new ArrayList();
	

	public ArrayList<Game> getGames() {
			return games;
		}
		public void setGames(ArrayList<Game> games) {
			this.games = games;
		}
		public ArrayList<Question> getQuestions() {
			return questions;
		}
		public void setQuestions(ArrayList<Question> questions) {
			this.questions = questions;
		}
		
			
	public static SysData getInstance() {
			if (sysData == null) {

				sysData = new SysData();
				return sysData;
			}
			return sysData;
		}

	public void importJson() throws IOException,  ParseException {
		
		JSONParser parser = new JSONParser();
		
		FileInputStream file = new FileInputStream("JSON/questions_scheme.json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(file));
		Object obj = parser.parse(reader);
		JSONObject jsonObj = (JSONObject)obj;
		JSONArray qArray = (JSONArray) jsonObj.get("questions");
		
		Iterator<JSONObject> QuestionIter = qArray.iterator();
		while (QuestionIter.hasNext()) {

			JSONObject que = QuestionIter.next();
			String q = (String) que.get("question");
			JSONArray ans = (JSONArray) que.get("answers");
			ArrayList<String> answers = new  ArrayList<String>();
			for (int i = 0; i < ans.size(); i++) {
				String answerText = (String) ans.get(i);
				String answerT = new String(answerText);
				answers.add(answerT);	

	}
		
			int corrAns = Integer.valueOf(que.get("correct_ans").toString());
			String diff = (String) que.get("difficulty");
			Difficulty d;
			if (diff.equals("1")) 
				d = Difficulty.Easy;		
			else if (diff.equals("2"))
				d = Difficulty.Medium;
			else // if (diff == "3")
				d = Difficulty.Hard;
		
			Question newQues = new Question(answers.get(0),answers.get(1), answers.get(2), answers.get(3),q,d,corrAns);
			this.questions.add(newQues);
		//	System.out.println(newQues.getDifficulty());
		}
	}
		

	public void writeJson(Question question) throws IOException, ParseException {
	    JSONParser parser = new JSONParser();

	    FileInputStream file = new FileInputStream("JSON/questions.json");
	    BufferedReader reader = new BufferedReader(new InputStreamReader(file));
	    Object obj = parser.parse(reader);
	    JSONObject jsonObject = (JSONObject) obj;
	    JSONArray qArray = (JSONArray) jsonObject.get("questions");

	    JSONObject json = new JSONObject();
	    // add new question about software engineering and QA
	    json.put("question", question.getQuestion()); // maps the name question to a specific question

	    JSONArray newQuestion = new JSONArray();

	    newQuestion.add(question.getAnswer1()); // adding answers to the array
	    newQuestion.add(question.getAnswer2());
	    newQuestion.add(question.getAnswer3());
	    newQuestion.add(question.getAnswer4());
	  //convert the question level from enum to int 
	    Difficulty diff = question.getDifficulty();
	    String str;
	  		if (diff == Difficulty.Easy) {
	  			str = "1";
	  		} else if (diff == Difficulty.Medium) {	
	  			str = "2";
	  		} else {
	  			str = "3";
	  		}
	  	json.put("difficulty", str); // choosing the difficulty for each question
	    json.put("correct_ans", Integer.toString(question.getCorrectAnswer())); // specifying which answer is the correct answer
	    qArray.add(newQuestion); // adding 4 answers to each question

	    JSONObject Json2 = new JSONObject();
	    Json2.put("questions", qArray);

	    try {
	        FileWriter file2 = new FileWriter("JSON/questions.json");
	        file2.write(Json2.toJSONString());
	        file2.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        SysData.getInstance().importJson();
	    }
	}
						
		
}
	
	
