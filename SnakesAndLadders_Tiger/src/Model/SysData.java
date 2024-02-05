package Model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
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
	public ArrayList<Question> deleted = new ArrayList();
//	public ArrayList<Question> edited = new ArrayList();
	private HashSet<Game> games = new HashSet(); // we use HashSet to prevent duplication of question in the table
	private HashSet<Question> questions = new HashSet();
	

	public HashSet<Game> getGames() {
			return games;
		}
		public void setGames( HashSet<Game> games) {
			this.games = games;
		}
		public  HashSet<Question> getQuestions() {
			return questions;
		}
		public void setQuestions( HashSet<Question> questions) {
			this.questions = questions;
		}
		
			
	public static SysData getInstance() {
			if (sysData == null) {

				sysData = new SysData();
				return sysData;
			}
			return sysData;
		}

	public void readFromJson() throws IOException,  ParseException {
		
		JSONParser parser = new JSONParser();
		
		FileInputStream file = new FileInputStream("JSON/demo.json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(file));
		Object obj = parser.parse(reader);
		JSONObject jsonObj = (JSONObject)obj;
		JSONArray questionArr = (JSONArray) jsonObj.get("questions");
		
		Iterator<JSONObject> QuestionIter = questionArr.iterator();
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
		questions.removeAll(deleted);
	}
		
	public void writeToJson(Question question) throws IOException, ParseException {
		
		JSONParser parser = new JSONParser();
		
		FileInputStream file = new FileInputStream("JSON/demo.json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(file));
		Object obj = parser.parse(reader);
		JSONObject jsonObj = (JSONObject)obj;
		JSONArray questionArr = (JSONArray) jsonObj.get("questions");

		JSONObject json = new JSONObject(); // new object type json
	    // add new question about software engineering and QA
	    JSONArray queAnswers = new JSONArray();

	    queAnswers.add(question.getAnswer1()); // adding answers to the array
	    queAnswers.add(question.getAnswer2());
	    queAnswers.add(question.getAnswer3());
	    queAnswers.add(question.getAnswer4());

	    Difficulty diff = question.getDifficulty();
	    String str; 	  //convert the question level from enum to int 

	  		if (diff.equals(Difficulty.Easy)) {
	  			str = "1";
	  		} else if (diff.equals(Difficulty.Medium)) {	
	  			str = "2";
	  		} else {     //diff.equals(Difficulty.Hard)
	  			str = "3"; 	
	  		}
	  	json.put("question", question.getQuestion()); // adding a question text to the json file
		json.put("answers",queAnswers); //adding answers to the json file
	  	json.put("difficulty", str); // adding difficulty for each question to the json file
	    json.put("correct_ans", Integer.toString(question.getCorrectAnswer())); // specifying which answer is the correct answer
	    questionArr.add(json); // adding the question to Json

	    JSONObject Json2 = new JSONObject();
	    Json2.put("questions", questionArr);

	    try {
	        FileWriter file2 = new FileWriter("JSON/demo.json");
	        file2.write(Json2.toJSONString());
	        file2.close();
		} catch (IOException e) {
	        e.printStackTrace();
	    } finally {
        SysData.getInstance().readFromJson();
	    }
	}
	
	public void deleteFromJson(Question question) throws IOException, ParseException{
		
		JSONParser parser = new JSONParser();
		
		FileInputStream file = new FileInputStream("JSON/demo.json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(file));
		Object obj = parser.parse(reader);
		JSONObject jsonObj = (JSONObject)obj;
		JSONArray questionArr = (JSONArray) jsonObj.get("questions");
		
		Iterator<JSONObject> quesIterator = questionArr.iterator();
		
		while(quesIterator.hasNext()) {
			JSONObject jsonObject = quesIterator.next();
			String questionText = (String) jsonObject.get("question");
			if(questionText.equals(question.getQuestion())) {
				quesIterator.remove();
			}
			
		}
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("questions", questionArr);
		try {
			FileWriter writeFile = new FileWriter("json/demo.json");
			writeFile.write(jsonObject2.toJSONString());
			writeFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			deleted.add(question);
			SysData.getInstance().readFromJson();
		}


		}
		
		
}
	
	
