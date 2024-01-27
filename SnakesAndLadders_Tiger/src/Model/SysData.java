package Model;

import java.io.BufferedReader;
import java.io.FileInputStream;
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
	
	private ArrayList<Question> EasyQuestions; 
	private ArrayList<Question> MediumQuestions; 
	private ArrayList<Question> HardQuestions; 
		
	private ArrayList<Game> games;
	private ArrayList<Question> questions;

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

	public void readFromJson() throws IOException,  ParseException {
		
		JSONParser parser = new JSONParser();
		
		FileInputStream file = new FileInputStream("JSON/questions_schema.json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(file));
		Object obj = parser.parse(reader);
		JSONObject jsonObj = (JSONObject)obj;
		JSONArray queArray = (JSONArray) jsonObj.get("questions");
		
		Iterator<JSONObject> QuestionIter = queArray.iterator();
		while (QuestionIter.hasNext()) {

			JSONObject que = QuestionIter.next();
			String ques = (String) que.get("question");
			JSONArray ans = (JSONArray) que.get("answers");
			ArrayList<Answer> answers = new  ArrayList<Answer>();
			for (int i = 0; i < ans.size(); i++) {
				String answercontent = (String) ans.get(i);
				Answer answerCo = new Answer(answercontent);
				answers.add(answerCo);	

	}
		
			int corrAns = Integer.valueOf(que.get("correct_ans").toString());
			String diff = (String) que.get("difficulty");
			Question newQues = new Question(answers.get(0),answers.get(1), answers.get(2), answers.get(3),ques,diff,corrAns);
		}
	}
}