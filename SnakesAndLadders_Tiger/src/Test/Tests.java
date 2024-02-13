package Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import Model.Difficulty;
import Model.Question;
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
    
	@Test
	  public void writeToJsonTest() throws IOException, ParseException {
	
	    SysData.getInstance().writeToJson(question);
	    assertTrue(SysData.getInstance().getQuestions().contains(question));
	
	}
	
	// Delete a question from question_scheme.json expects not to find the deleted question in the Question HashSet and SysData
   @Test
    public void deleteFromJsonTest() throws IOException, ParseException {
	   
        SysData.getInstance().deleteFromJson(question);
        SysData.getInstance().readFromJson();
        assertTrue(!SysData.getInstance().getQuestions().contains(question));
   }
   
   // Check if the SysData and the Data Structure HashSet is not Null
   @Test
   public void isJsonNullTest() throws IOException, ParseException {
       	SysData.getInstance().readFromJson();
       	SysData.getInstance().getQuestions();
       	assertNotNull("Questions", SysData.getInstance().getQuestions());

  }
   
   // Updating the question in the HashSet and Json File
   @Test
   public void updateInJsonTest() throws IOException, ParseException {

       SysData.getInstance().getQuestions().add(oldQuestion);

       SysData.getInstance().updateInJson(oldQuestion, newQuestion);
       SysData.getInstance().readFromJson();

       // Check conditions
       assertFalse(SysData.getInstance().getQuestions().contains(oldQuestion)); // Old question should not be present
       assertTrue(SysData.getInstance().getQuestions().contains(newQuestion));  // New question should be present
   }

   
	/*
	 * After check I have to keep the originJson file, without any changes. 
	 * so we clean up the SysData from the changes that happen in the JUnit test
	 */
   
   @AfterEach
   public void cleanup() throws IOException, ParseException {
       
	   SysData.getInstance().deleteFromJson(question);
	   SysData.getInstance().deleteFromJson(newQuestion);

	   SysData.getInstance().readFromJson();
   }

}
