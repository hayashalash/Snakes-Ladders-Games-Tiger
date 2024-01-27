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
	
//	private ArrayList<Integer> EasyQuestions; // change "Integer" to class "Question" once created
//	private ArrayList<Integer> MediumQuestions; // change "Integer" to class "Question" once created
//	private ArrayList<Integer> HardQuestions; // change "Integer" to class "Question" once created
		
	private ArrayList<Game> games;
	private ArrayList<Question>questions;

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

	public void ReadFromJson() throws IOException, ParseException {
		
		JSONParser parser = new JSONParser();
		
		FileInputStream fis = new FileInputStream("JSON/questions_scheme.json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		Object obj = parser.parse(reader);
		JSONObject jsonobj = (JSONObject) obj;
		JSONArray quesArray = (JSONArray) jsonobj.get("questions");
		
		Iterator<JSONObject> QuestionIter = quesArray.iterator();
		while (QuestionIter.hasNext()) {

			JSONObject que = QuestionIter.next();
			String AnswerText = (String) que.get("question");
			JSONArray answeer = (JSONArray) que.get("answers");
			ArrayList<Answer> answers = new  ArrayList<Answer>();
			for (int i = 0; i < answeer.size(); i++) {
				String answercontent = (String) answeer.get(i);
				Answer answer = new Answer(answercontent);
				answers.add(answer);
				
			}

			int correctAnswerNum = Integer.valueOf(que.get("correct_ans").toString());

			String teams = (String) que.get("team");
			String levelDiffuclty = (String) que.get("level");
			//read and prints the json
			Question questionToAdd = new Question(AnswerText,correctAnswerNum,levelDiffuclty,teams,answers);
		}
	}
		
	}
	
}