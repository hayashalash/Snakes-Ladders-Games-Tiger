package Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import Model.Difficulty;
import Model.Question;
import Model.SysData;


class Tests {

	@Test
	  public void writeToJsonTest() throws IOException, ParseException {

        Question question = new Question("a1" , "a2" , "a3" , "a4" , "Question is" , Difficulty.Easy , 4);
        SysData.getInstance().writeToJson(question);
        assertTrue(SysData.getInstance().getQuestions().contains(question));

    }
	
	
	 
	@Test
	  public void readFromJsonTest() throws IOException, ParseException {

		   SysData.getInstance().readFromJson();
	       SysData.getInstance().getQuestions();
	       assertNotNull("Questions", SysData.getInstance().getQuestions());

	}
	
	
}
