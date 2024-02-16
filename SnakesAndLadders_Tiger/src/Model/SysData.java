package Model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import Model.Game;

import org.json.simple.parser.JSONParser;


public class SysData {
   //singleton
	private static SysData sysData = null;
	private static final String QJSON = "JSON/questions_scheme.json";
	private static final String HJSON = "JSON/History.json";
	
	public ArrayList<Question> deleted = new ArrayList();
	private HashSet<GameHistory> games = new HashSet(); 
	private HashSet<Question> questions = new HashSet(); // we use HashSet to prevent duplication of question in the table
	
	

		public HashSet<GameHistory> getGames() {
		return games;
	}
	public void setGames(HashSet<GameHistory> games) {
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
		
		FileInputStream file = new FileInputStream(QJSON);
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
		
		FileInputStream file = new FileInputStream(QJSON);
		BufferedReader reader = new BufferedReader(new InputStreamReader(file));
		Object obj = parser.parse(reader);
		JSONObject jsonObj = (JSONObject)obj;
		JSONArray questionArr = (JSONArray) jsonObj.get("questions");

		JSONObject json = new JSONObject(); // new object type json
	    JSONArray queAnswers = new JSONArray(); 	    // add new question about software engineering and QA


	    queAnswers.add(question.getAnswer1()); // adding answers to the array
	    queAnswers.add(question.getAnswer2());
	    queAnswers.add(question.getAnswer3());
	    queAnswers.add(question.getAnswer4());

	    Difficulty diff = question.getDifficulty();
	    String str; 	  //convert the question level from enum to string 

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
	        FileWriter file2 = new FileWriter(QJSON);
	        file2.write(Json2.toJSONString());
	        file2.close();
		} 
	    catch (IOException e) {
	        e.printStackTrace();
	    } 
	    finally {
        SysData.getInstance().readFromJson();
	    }
	}
	
	public void updateInJson(Question oldQuestion, Question newQuestion) throws IOException, ParseException {
	    questions.remove(oldQuestion); 		  // Remove the old question from the HashSet

	    questions.add(newQuestion);	    // Add the new question to the HashSet

	    deleteFromJson(oldQuestion);
	    writeToJson(newQuestion);
	
	}

	
	public void deleteFromJson(Question question) throws IOException, ParseException {
	    JSONParser parser = new JSONParser();

	    FileInputStream file = new FileInputStream(QJSON);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(file));
	    Object obj = parser.parse(reader);
	    JSONObject jsonObj = (JSONObject) obj;
	    JSONArray questionArr = (JSONArray) jsonObj.get("questions");

	    // Remove from JSONArray
	    Iterator<JSONObject> quesIterator = questionArr.iterator();
	    while (quesIterator.hasNext()) {
	        JSONObject jsonObject = quesIterator.next();
	        String questionText = (String) jsonObject.get("question");
	        if (questionText.equals(question.getQuestion())) {
	            quesIterator.remove();
	        }
	    }

	    // Update HashSet
	    questions.remove(question);

	    // Write back to JSON file
	    JSONObject jsonObject2 = new JSONObject();
	    jsonObject2.put("questions", questionArr);

	    try {
	        FileWriter writeFile = new FileWriter(QJSON);
	        writeFile.write(jsonObject2.toJSONString());
	        writeFile.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        SysData.getInstance().readFromJson();
	    }
	}

	
	// Saving the Games history in json file, Not checked yet! 
	public void writeToJsonGames(GameHistory g) throws IOException, ParseException { 
		JSONParser parser = new JSONParser();
		FileInputStream fis = new FileInputStream(HJSON);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		Object obje = parser.parse(reader);
		JSONObject jo = (JSONObject) obje;
		JSONArray gamesArray = (JSONArray) jo.get("gamesHistory");
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("GameNumber", g.getGameID());
		jsonObject.put("Winner", g.getWinner().getPlayerName());
		  Difficulty diff = g.getDiff();
		    String str; 	  //convert the question level from enum to string 

		  		if (diff.equals(Difficulty.Easy)) {
		  			str = "1";
		  		} else if (diff.equals(Difficulty.Medium)) {	
		  			str = "2";
		  		} else {     //diff.equals(Difficulty.Hard)
		  			str = "3"; 	
		  		}
		jsonObject.put("Difficulty", str);
		String dur = g.getDuration();
		jsonObject.put("Duration", dur);
		gamesArray.add(jsonObject);
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("gamesHistory", gamesArray); 
		try {
			FileWriter file = new FileWriter(HJSON);
			file.write(jsonObject2.toJSONString());
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ReadFromJsonGames() throws IOException, ParseException {
		
		try ( FileInputStream fis = new FileInputStream(HJSON))
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	         if (isJsonNull(reader)==true)
	         {
	        	 return;
	         }
	         else {
	 			JSONParser parser2 = new JSONParser();
	 			FileInputStream fis2 = new FileInputStream(HJSON);
				BufferedReader reader2 = new BufferedReader(new InputStreamReader(fis2));
				Object obje = parser2.parse(reader2);
			
				JSONObject jo = (JSONObject) obje;
	
				JSONArray quesArray = (JSONArray) jo.get("gamesHistory");

				Iterator<JSONObject> QuestionIter = quesArray.iterator();
				while (QuestionIter.hasNext()) {
	
					JSONObject que = QuestionIter.next();
					Integer gID = (Integer) que.get("gameID");
					String durr = (String)que.get("Duration");
					Player PlayerName = (Player)que.get("Winner");
					String diff = (String) que.get("difficulty");
					Difficulty d;
					if (diff.equals("1")) 
						d = Difficulty.Easy;		
					else if (diff.equals("2"))
						d = Difficulty.Medium;
					else // if (diff == "3")
						d = Difficulty.Hard;
					
	            	GameHistory gh = new GameHistory(gID,PlayerName,d,durr);
	            	SysData.getInstance().getGames().add(gh);
				}}
			}
			catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			catch (IOException e) {
					e.printStackTrace();
				}	
			catch (ParseException e) {
					e.printStackTrace();
					}	
		
	}
	
	public boolean isJsonNull(BufferedReader reader) throws IOException {
	    StringBuilder json = new StringBuilder();
	    String line;
	    
	    while ((line = reader.readLine()) != null) {
	        json.append(line);
	    }
	    
	    reader.close();

	    // Check if the JSON file is empty
	    if (json.toString().trim().isEmpty()) {
	        System.out.println("The JSON file is empty");
	        return true;
	    } else {
	        return false;
	    }
	}
		
}
	
	
