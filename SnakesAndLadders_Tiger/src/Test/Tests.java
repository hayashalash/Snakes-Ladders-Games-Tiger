package Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import Model.Board;
import Model.Difficulty;
import Model.Ladder;
import Model.Question;
import Model.QuestionTile;
import Model.Snake;
import Model.SysData;


class Tests {

    Question question = new Question("a1" , "a2" , "a3" , "a4" , "Test Question" , Difficulty.Easy , 4);
    
    // Old question
    Question oldQuestion = new Question("c1", "c2", "c3", "c4", "Old Question", Difficulty.Hard, 2);

    // New question
    Question newQuestion = new Question("b1", "b2", "b3", "b4", "New Question", Difficulty.Medium, 3);
    
    
	/*
	 * Create a new question and add it to the HashSet question and write it to
	 * question_scheme.Json file and check if it is done successfully
	 */
    
	@Test  //Test ID = A1
	public void writeToJsonTest() throws IOException, ParseException {
	
	    SysData.getInstance().writeToJson(question);
	    assertTrue(SysData.getInstance().getQuestions().contains(question));
	
	}
	
	
	/*
	 * Delete a question from question_scheme.json expects not to find the deleted
	 * question in the Question HashSet and SysData
	 */
	
	@Test   //Test ID = B1
    public void deleteFromJsonTest() throws IOException, ParseException {
	   
        SysData.getInstance().deleteFromJson(question);
        SysData.getInstance().readFromJson();
        assertTrue(!SysData.getInstance().getQuestions().contains(question));
   }
   
	/*
	 * Check if the SysData and the Data Structure HashSet is not Null
	 */
	
   @Test  //Test ID = D1
   public void isJsonNullTest() throws IOException, ParseException {
       	SysData.getInstance().readFromJson();
       	SysData.getInstance().getQuestions();
       	assertNotNull("Questions", SysData.getInstance().getQuestions());

  }
   
	/*
	 * Updating the question in the HashSet and Json File
	 */
   
   @Test   //Test ID = E1
   public void updateInJsonTest1() throws IOException, ParseException {

       SysData.getInstance().getQuestions().add(oldQuestion);

       SysData.getInstance().updateInJson(oldQuestion, newQuestion);
       SysData.getInstance().readFromJson();

       // Check conditions
       assertFalse(SysData.getInstance().getQuestions().contains(oldQuestion)); // Old question should not be present
       assertTrue(SysData.getInstance().getQuestions().contains(newQuestion));  // New question should be present
   }
   
 

   /*
    * Check that random tiles are created within the board limits
    * */
   @Test   //Test ID = F1
   public void testChooseRandomTile() {
	   Board board = new Board(Difficulty.values()[new Random().nextInt(Difficulty.values().length)]); // choose a random difficulty
       board.createBoard();
       int randomTile = board.chooseRandomTile(0);
       assertTrue(randomTile > 0 && randomTile <= board.getBoardSize());
   }
   
   /*
    * Check that the method choosing a random tile from a row, returns a tile in the given row.
    * */
   
   @Test   //Test ID = G1
   public void testChooseRandomInRow() {
	   Board board = new Board(Difficulty.values()[new Random().nextInt(Difficulty.values().length)]); // choose a random difficulty
	   board.createBoard();
       int randomInRow = board.chooseRandomInRow(board.getBoardLen()-1); // choose a random tile from the first (bottom) row
       assertTrue(randomInRow > 0 && randomInRow <= board.getBoardLen()); // the board length is the number of the last tile in the first row
   }
   
   /*
    * Check that when a board is created, it has the needed items: questions, snakes and ladders
    * This test does not check for surprises as easy boards don't have surprise tiles
    * */
   
   @Test   //Test ID = H1
   public void testSpecialTiles() {
	   Board board = new Board(Difficulty.values()[new Random().nextInt(Difficulty.values().length)]); // choose a random difficulty
       board.createBoard();
       
       ArrayList<QuestionTile> questionTiles = board.getQuestionTiles();
       assertFalse(questionTiles.isEmpty());
       for (QuestionTile questionTile : questionTiles) {
           assertNotNull(questionTile);
       }
       
       HashMap<Integer, Snake> snakes = board.getSnakes();
       assertFalse(snakes.isEmpty());
       for (Snake snake : snakes.values()) {
           assertNotNull(snake);
       }
       
       HashMap<Integer, Ladder> ladders = board.getLadders();
       assertFalse(ladders.isEmpty());
       for (Ladder ladder : ladders.values()) {
           assertNotNull(ladder);
       }
   }
	/*
	 * After check I have to keep the origin Json file, without any changes. 
	 * so we clean up the SysData from the changes that happen in the JUnit test
	 */
   
   @AfterEach
   public void cleanup() throws IOException, ParseException {
       
	   SysData.getInstance().deleteFromJson(question);
	   SysData.getInstance().deleteFromJson(newQuestion);

	   SysData.getInstance().readFromJson();
   }

}
