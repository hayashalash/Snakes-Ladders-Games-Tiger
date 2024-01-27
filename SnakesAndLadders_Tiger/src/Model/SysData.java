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

		
	}
	
}