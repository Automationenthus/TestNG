package tests;

import java.io.IOException;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import hooks.Hooks;
import driverFactory.DriverFactory;
import pageObject.ArrayPF;
import pageObject.HomePF;
import pageObject.LoginPF;
import pageObject.HomePF;
import utilities.ConfigReader;
import utilities.DataProvider_Class;
import utilities.ExcelReader;
import utilities.LogHandler;

public class ArrayTest extends Hooks {

	private String username;
	private String password;
	String ExpectedOutput;
	String pagetitle;
	ArrayPF arpf;
	LoginPF lpf;

	@BeforeMethod
	public void initPageObjects() {

		lpf = new LoginPF(driver);
		arpf = new ArrayPF(driver);

		lpf.click_homesignin();
		
		lpf.login_user(username);
		lpf.login_passowrd(password);
		lpf.login_button();

	}
	

	public ArrayTest(String username, String password) throws IOException {
		this.username = username;
		this.password = password;

	}

	@Factory(dataProvider = "ValidLoginData", dataProviderClass = DataProvider_Class.class)

	public static Object[] loginData(String username, String password) throws IOException {
		return new Object[] { new ArrayTest(username, password) };

	}

	@Test(priority = 1)
	public void verifyUserIsInArrayPage() {
		arpf.navigateToArraysPage();
		Assert.assertTrue(driver.getTitle().contains("Array"), "incorrect page");
	}
	 
    
   
        
	 @Test (dataProvider = "ArraysLinks",dataProviderClass =DataSupply.class,priority=2 )
	 public void verifyArraysNavigation(String linktext) {
	 
		 arpf.clickGettingStarted();
		 Assert.assertTrue(driver.getTitle().contains("Array"), "Navigation to Arrays failed");
		 arpf.clickArraysLink(linktext);
		 Assert.assertTrue(driver.getTitle().contains(linktext), "Navigation to " + linktext + " failed");
	 }
	 
	 
	 @Test(priority=3) 
	 public void VerifyTryHere() {
		 
		 arpf.navigateToTryEditorPage();
		 LogHandler.info("Navigated to Try Editor page.");
		 Assert.assertTrue(arpf.isRunButtonVisible(), "Run button not visible");
	 }
	 
	 @Test(priority=4) 
	    public void verifyRunButtonWithoutEnteringCode() {
	        
		 arpf.navigateToTryEditorPage();
	        Assert.assertTrue(arpf.isRunButtonVisible(), "Run button not visible");
	        arpf.clickRunButton();
	        String alertText = arpf.getAlertTextAndAccept();
	        if (alertText == null) {
	        	LogHandler.info("Bug - No alert shown for empty code.");
	            Assert.fail("Bug: No alert displayed when clicking Run without entering code.");
	        } else {
	            Assert.assertTrue(alertText.toLowerCase().contains("error"),
	                    "Expected error message in alert, but got: " + alertText);
	        }
         driver.navigate().back();
	    }
	
	
	 @Test(priority=5)
		public void validPythonCodeforTryEditor() {
		 arpf.navigateToTryEditorPage();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String output = arpf.getEditorOutput();
	         Assert.assertNotNull(output, "Output is null");
	         Assert.assertFalse(output.trim().isEmpty(), "Output is empty");
	         LogHandler.info("answer is: " +output);
	 }

	 @Test(priority=6)
		public void InvalidPythonCodeforTryEditor() {
		 arpf.navigateToTryEditorPage();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String alertText = arpf.getAlertTextAndAccept(); // Or AlertUtils.getAlertTextAndAccept()
		    Assert.assertNotNull(alertText, "Expected alert was not present.");
		    Assert.assertTrue(alertText.toLowerCase().contains("nameerror"),
		            "Expected alert to contain 'NameError', but got: " + alertText);
		}

	 @Test(priority=7) 
	 public void VerifyPracticeQuestion() {
		 
		 arpf.navigateToArraysPracticeQuestions();
		 LogHandler.info("Navigated to Practice Question page.");
		 Assert.assertTrue(arpf.isSearchTheArrayLinkVisible(), "Search the Array link is not visible on Practice page");
	 }
	 
	 

	    @Test(dataProvider = "questionLinks",dataProviderClass =DataSupply.class, priority=8)
	    public void verifyPracticeQuestionNavigation(String questionLinkText) {
	    	
	    	arpf.navigateToArraysPracticeQuestions();
	    	arpf.clickPracticeQuestion(questionLinkText);

	        Assert.assertTrue(arpf.isQuestionDisplayed(), "Question not displayed for: " + questionLinkText);
	        Assert.assertTrue(arpf.isRunButton1Visible(), "Run button not visible for: " + questionLinkText);
	        Assert.assertTrue(arpf.isSubmitButtonVisible(), "Submit button not visible for: " + questionLinkText);
	        driver.navigate().back();
	    }
	    
	    @Test(priority=9) 
	    public void ValidcodeandRunSearchArray() {
	    	arpf.navigateToSearchthearray();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid.sa");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String output = arpf.getConsoleOutput();
			LogHandler.info("Editor Output: " + output);
		     Assert.assertNotNull(output, "Console output is null");
		     Assert.assertFalse(output.trim().isEmpty(), "Expected output in console, but found none");
	 }
	   
	    @Test(priority=10) 
	    public void inValidcodeandRunSearchArray() {
	    	arpf.navigateToSearchthearray();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid.sa");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String alertText = arpf.getAlertTextAndAccept(); 
			LogHandler.info("Alert Text: " + alertText);
		    Assert.assertNotNull(alertText, "Expected alert was not present.");
		    Assert.assertTrue(alertText.toLowerCase().contains("error"),
			         "Expected alert to contain (SyntaxError/NameError), but got: " + alertText );
	    }
	    
	    @Test(priority=11) 
	    public void ValidcodeandSubmitSearchArray() {
	    	arpf.navigateToSearchthearray();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid.sa");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String output = arpf.getEditorOutput();
			LogHandler.info("Editor Output: " + output);
			Assert.assertNotNull(output, "Console output is null");
		     Assert.assertFalse(output.trim().isEmpty(), "Console output is empty");
		     Assert.assertTrue(output.contains("Submission Successful"),
		         "BUG: Expected message 'Submission Successful' but got: '" + output + "'");
	    }
	    
	    @Test(priority=12) 
	    public void inValidcodeandSubmitSearchArray() {
	    	arpf.navigateToSearchthearray();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid.sa");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String output = arpf.getEditorOutput();
			LogHandler.info("Editor Output: " + output);
			Assert.assertNotNull(output, "Console output is null");
		     Assert.assertFalse(output.trim().isEmpty(), "Console output is empty");
		     Assert.assertTrue(output.contains("Error occurred"), 
		         "BUG: Expected message 'Error occurred' but got: '" + output + "'");
	    }
	    
	
	    @Test(priority=13) 
	    public void ValidcodeandRunMAX() {
	    	arpf.navigateToSearchthearray();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid.max");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String output = arpf.getConsoleOutput();
			LogHandler.info("Editor Output: " + output);
		     Assert.assertNotNull(output, "Console output is null");
		     Assert.assertFalse(output.trim().isEmpty(), "Expected output in console, but found none");
	 }
	   
	    @Test(priority=14) 
	    public void inValidcodeandRunMAX() {
	    	arpf.navigateToSearchthearray();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid.max");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String alertText = arpf.getAlertTextAndAccept(); 
			LogHandler.info("Alert Text: " + alertText);
		    Assert.assertNotNull(alertText, "Expected alert was not present.");
		    Assert.assertTrue(alertText.toLowerCase().contains("error"),
			         "Expected alert to contain (SyntaxError/NameError), but got: " + alertText );
	    }
	    
	    @Test(priority=15) 
	    public void ValidcodeandSubmitMAX() {
	    	arpf.navigateToSearchthearray();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid.max");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String output = arpf.getEditorOutput();
			LogHandler.info("Editor Output: " + output);
			Assert.assertNotNull(output, "Console output is null");
		     Assert.assertFalse(output.trim().isEmpty(), "Console output is empty");
		     Assert.assertTrue(output.contains("Submission Successful"),
		         "BUG: Expected message 'Submission Successful' but got: '" + output + "'");
	    }
	    
	    @Test(priority=16) 
	    public void inValidcodeandSubmitMAX() {
	    	arpf.navigateToSearchthearray();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid.max");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String output = arpf.getEditorOutput();
			LogHandler.info("Editor Output: " + output);;
			Assert.assertNotNull(output, "Console output is null");
		     Assert.assertFalse(output.trim().isEmpty(), "Console output is empty");
		     Assert.assertTrue(output.contains("Error occurred"), 
		         "BUG: Expected message 'Error occurred' but got: '" + output + "'");
	    }
	    
	
	    
	    @Test(priority=17) 
	    public void ValidcodeandRunNumber() {
	    	arpf.navigateToSearchthearray();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid.Num");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String output = arpf.getConsoleOutput();
			LogHandler.info("Editor Output: " + output);
		     Assert.assertNotNull(output, "Console output is null");
		     Assert.assertFalse(output.trim().isEmpty(), "Expected output in console, but found none");
	 }
	   
	    @Test(priority=18) 
	    public void inValidcodeandRunNumber() {
	    	arpf.navigateToSearchthearray();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid.Num");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String alertText = arpf.getAlertTextAndAccept(); 
			LogHandler.info("Alert Text: " + alertText);
		    Assert.assertNotNull(alertText, "Expected alert was not present.");
		    Assert.assertTrue(alertText.toLowerCase().contains("error"),
			         "Expected alert to contain (SyntaxError/NameError), but got: " + alertText );
	    }
	    
	    @Test(priority=19) 
	    public void ValidcodeandSubmitNumber() {
	    	arpf.navigateToSearchthearray();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid.Num");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String output = arpf.getEditorOutput();
			LogHandler.info("Editor Output: " + output);
			Assert.assertNotNull(output, "Console output is null");
		     Assert.assertFalse(output.trim().isEmpty(), "Console output is empty");
		     Assert.assertTrue(output.contains("Submission Successful"),
		         "BUG: Expected message 'Submission Successful' but got: '" + output + "'");
	    }
	    
	    @Test(priority=20) 
	    public void inValidcodeandSubmitNumber() {
	    	arpf.navigateToSearchthearray();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid.Num");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String output = arpf.getEditorOutput();
			LogHandler.info("Editor Output: " + output);
			Assert.assertNotNull(output, "Console output is null");
		     Assert.assertFalse(output.trim().isEmpty(), "Console output is empty");
		     Assert.assertTrue(output.contains("Error occurred"), 
		         "BUG: Expected message 'Error occurred' but got: '" + output + "'");
	    }
	    
	 
	    
	    @Test(priority=21) 
	    public void ValidcodeandRunSortedSquares() {
	    	arpf.navigateToSearchthearray();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid.SQ");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String output = arpf.getConsoleOutput();
			LogHandler.info("Editor Output: " + output);
		     Assert.assertNotNull(output, "Console output is null");
		     Assert.assertFalse(output.trim().isEmpty(), "Expected output in console, but found none");
	 }
	   
	    @Test(priority=22) 
	    public void inValidcodeandRunSortedSquares() {
	    	arpf.navigateToSearchthearray();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid.SQ");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String alertText = arpf.getAlertTextAndAccept(); 
			LogHandler.info("Alert Text: " + alertText);
		    Assert.assertNotNull(alertText, "Expected alert was not present.");
		    Assert.assertTrue(alertText.toLowerCase().contains("error"),
			         "Expected alert to contain (SyntaxError/NameError), but got: " + alertText );
	    }
	    
	    @Test(priority=23) 
	    public void ValidcodeandSubmitSortedSquares() {
	    	arpf.navigateToSearchthearray();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid.SQ");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String output = arpf.getEditorOutput();
			LogHandler.info("Editor Output: " + output);
			Assert.assertNotNull(output, "Console output is null");
		     Assert.assertFalse(output.trim().isEmpty(), "Console output is empty");
		     Assert.assertTrue(output.contains("Submission Successful"),
		         "BUG: Expected message 'Submission Successful' but got: '" + output + "'");
	    }
	    
	    @Test(priority=24) 
	    public void inValidcodeandSubmitSortedSquares() {
	    	arpf.navigateToSearchthearray();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid.SQ");
			String Codetoenter=data.get("Code");
			arpf.enterpythonCode(Codetoenter);
			arpf.clickRun();
			String output = arpf.getEditorOutput();
			LogHandler.info("Editor Output: " + output);
			Assert.assertNotNull(output, "Console output is null");
		     Assert.assertFalse(output.trim().isEmpty(), "Console output is empty");
		     Assert.assertTrue(output.contains("Error occurred"), 
		         "BUG: Expected message 'Error occurred' but got: '" + output + "'");
	    }
	    
	    @Test(priority=25) 
		 public void Signout(@Optional("NumpyNinja") String expectd) {
			 
			 arpf.navigateToArraysPracticeQuestions();
			 arpf.signOut();
			 String actualTitle=driver.getTitle();
				Assert.assertEquals(actualTitle, expectd);
				LogHandler.info("user successfully navigated back to: "+expectd);
				
		 } 
	    
}