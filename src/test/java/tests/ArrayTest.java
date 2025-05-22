package tests;

import java.io.IOException;
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
import utilities.LogHandler;

public class ArrayTest extends Hooks {

	private String username;
	private String password;
	String ExpectedOutput;
	String pagetitle;
	ArrayPF arpf;
	HomePF hmpf;
	LoginPF lpf;

	@BeforeMethod
	public void initPageObjects() {
		hmpf = new HomePF();
		lpf = new LoginPF(driver);
		hmpf.launchurl();
		hmpf.getstartedbuttonclick();

		lpf.click_homesignin();
		arpf = new ArrayPF(driver);
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
		 
			 arraysPage.clickGettingStarted();
			 Assert.assertTrue(driver.getTitle().contains("Array"), "Navigation to Arrays failed");
			 arraysPage.clickArraysLink(linktext);
			 Assert.assertTrue(driver.getTitle().contains(linktext), "Navigation to " + linktext + " failed");
		 }
		 
		 
		 @Test(priority=3) 
		 public void VerifyTryHere() {
			 
			 arraysPage.navigateToTryEditorPage();
			 Assert.assertTrue(arraysPage.isRunButtonVisible(), "Run button not visible");
		 }
		 
		 @Test(priority=4) 
		    public void verifyRunButtonWithoutEnteringCode() {
		        
		        arraysPage.navigateToTryEditorPage();
		        Assert.assertTrue(arraysPage.isRunButtonVisible(), "Run button not visible");
		        arraysPage.clickRunButton();
		        String alertText = arraysPage.getAlertTextAndAccept();
		        if (alertText == null) {
		            System.out.println("WARN: Bug - No alert shown for empty code.");
		            Assert.fail("Bug: No alert displayed when clicking Run without entering code.");
		        } else {
		            Assert.assertTrue(alertText.toLowerCase().contains("error"),
		                    "Expected error message in alert, but got: " + alertText);
		        }
	         driver.navigate().back();
		    }
		
		
		 @Test(priority=5)
			public void validPythonCodeforTryEditor() {
			arraysPage.navigateToTryEditorPage();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String output = arraysPage.getEditorOutput();
		         Assert.assertNotNull(output, "Output is null");
		         Assert.assertFalse(output.trim().isEmpty(), "Output is empty");
		         LogHandler.info("answer is: " +output);
		 }

		 @Test(priority=6)
			public void InvalidPythonCodeforTryEditor() {
			arraysPage.navigateToTryEditorPage();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String alertText = arraysPage.getAlertTextAndAccept(); // Or AlertUtils.getAlertTextAndAccept()
			    Assert.assertNotNull(alertText, "Expected alert was not present.");
			    Assert.assertTrue(alertText.toLowerCase().contains("nameerror"),
			            "Expected alert to contain 'NameError', but got: " + alertText);
			}

		 @Test(priority=7) 
		 public void VerifyPracticeQuestion() {
			 
			 arraysPage.navigateToArraysPracticeQuestions();
			 Assert.assertTrue(arraysPage.isSearchTheArrayLinkVisible(), "Search the Array link is not visible on Practice page");
		 }
		 
		 

		    @Test(dataProvider = "questionLinks",dataProviderClass =DataSupply.class, priority=8)
		    public void verifyPracticeQuestionNavigation(String questionLinkText) {
		    	
		    	arraysPage.navigateToArraysPracticeQuestions();
		        arraysPage.clickPracticeQuestion(questionLinkText);

		        Assert.assertTrue(arraysPage.isQuestionDisplayed(), "Question not displayed for: " + questionLinkText);
		        Assert.assertTrue(arraysPage.isRunButton1Visible(), "Run button not visible for: " + questionLinkText);
		        Assert.assertTrue(arraysPage.isSubmitButtonVisible(), "Submit button not visible for: " + questionLinkText);

		        driver.navigate().back();
		    }
		    
		    @Test(priority=9) 
		    public void ValidcodeandRunSearchArray() {
		    	arraysPage.navigateToSearchthearray();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid.sa");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String output = arraysPage.getConsoleOutput();
				System.out.println("Editor Output: " + output);
			     Assert.assertNotNull(output, "Console output is null");
			     Assert.assertFalse(output.trim().isEmpty(), "Expected output in console, but found none");
		 }
		   
		    @Test(priority=10) 
		    public void inValidcodeandRunSearchArray() {
		    	arraysPage.navigateToSearchthearray();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid.sa");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String alertText = arraysPage.getAlertTextAndAccept(); 
				System.out.println("Alert Text: " + alertText);
			    Assert.assertNotNull(alertText, "Expected alert was not present.");
			    Assert.assertTrue(alertText.toLowerCase().contains("error"),
				         "Expected alert to contain (SyntaxError/NameError), but got: " + alertText );
		    }
		    
		    @Test(priority=11) 
		    public void ValidcodeandSubmitSearchArray() {
		    	arraysPage.navigateToSearchthearray();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid.sa");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String output = arraysPage.getEditorOutput();
				System.out.println("Editor Output: " + output);
				Assert.assertNotNull(output, "Console output is null");
			     Assert.assertFalse(output.trim().isEmpty(), "Console output is empty");
			     Assert.assertTrue(output.contains("Submission Successful"),
			         "BUG: Expected message 'Submission Successful' but got: '" + output + "'");
		    }
		    
		    @Test(priority=12) 
		    public void inValidcodeandSubmitSearchArray() {
		    	arraysPage.navigateToSearchthearray();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid.sa");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String output = arraysPage.getEditorOutput();
				System.out.println("Editor Output: " + output);
				Assert.assertNotNull(output, "Console output is null");
			     Assert.assertFalse(output.trim().isEmpty(), "Console output is empty");
			     Assert.assertTrue(output.contains("Error occurred"), 
			         "BUG: Expected message 'Error occurred' but got: '" + output + "'");
		    }
		    
		
		    @Test(priority=13) 
		    public void ValidcodeandRunMAX() {
		    	arraysPage.navigateToSearchthearray();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid.max");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String output = arraysPage.getConsoleOutput();
				LogHandler.info("Editor Output: " + output);
			     Assert.assertNotNull(output, "Console output is null");
			     Assert.assertFalse(output.trim().isEmpty(), "Expected output in console, but found none");
		 }
		   
		    @Test(priority=14) 
		    public void inValidcodeandRunMAX() {
		    	arraysPage.navigateToSearchthearray();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid.max");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String alertText = arraysPage.getAlertTextAndAccept(); 
				System.out.println("Alert Text: " + alertText);
			    Assert.assertNotNull(alertText, "Expected alert was not present.");
			    Assert.assertTrue(alertText.toLowerCase().contains("error"),
				         "Expected alert to contain (SyntaxError/NameError), but got: " + alertText );
		    }
		    
		    @Test(priority=15) 
		    public void ValidcodeandSubmitMAX() {
		    	arraysPage.navigateToSearchthearray();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid.max");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String output = arraysPage.getEditorOutput();
				System.out.println("Editor Output: " + output);
				Assert.assertNotNull(output, "Console output is null");
			     Assert.assertFalse(output.trim().isEmpty(), "Console output is empty");
			     Assert.assertTrue(output.contains("Submission Successful"),
			         "BUG: Expected message 'Submission Successful' but got: '" + output + "'");
		    }
		    
		    @Test(priority=16) 
		    public void inValidcodeandSubmitMAX() {
		    	arraysPage.navigateToSearchthearray();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid.max");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String output = arraysPage.getEditorOutput();
				System.out.println("Editor Output: " + output);
				Assert.assertNotNull(output, "Console output is null");
			     Assert.assertFalse(output.trim().isEmpty(), "Console output is empty");
			     Assert.assertTrue(output.contains("Error occurred"), 
			         "BUG: Expected message 'Error occurred' but got: '" + output + "'");
		    }
		    
		
		    
		    @Test(priority=17) 
		    public void ValidcodeandRunNumber() {
		    	arraysPage.navigateToSearchthearray();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid.Num");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String output = arraysPage.getConsoleOutput();
				LogHandler.info("Editor Output: " + output);
			     Assert.assertNotNull(output, "Console output is null");
			     Assert.assertFalse(output.trim().isEmpty(), "Expected output in console, but found none");
		 }
		   
		    @Test(priority=18) 
		    public void inValidcodeandRunNumber() {
		    	arraysPage.navigateToSearchthearray();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid.Num");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String alertText = arraysPage.getAlertTextAndAccept(); 
				System.out.println("Alert Text: " + alertText);
			    Assert.assertNotNull(alertText, "Expected alert was not present.");
			    Assert.assertTrue(alertText.toLowerCase().contains("error"),
				         "Expected alert to contain (SyntaxError/NameError), but got: " + alertText );
		    }
		    
		    @Test(priority=19) 
		    public void ValidcodeandSubmitNumber() {
		    	arraysPage.navigateToSearchthearray();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid.Num");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String output = arraysPage.getEditorOutput();
				System.out.println("Editor Output: " + output);
				Assert.assertNotNull(output, "Console output is null");
			     Assert.assertFalse(output.trim().isEmpty(), "Console output is empty");
			     Assert.assertTrue(output.contains("Submission Successful"),
			         "BUG: Expected message 'Submission Successful' but got: '" + output + "'");
		    }
		    
		    @Test(priority=20) 
		    public void inValidcodeandSubmitNumber() {
		    	arraysPage.navigateToSearchthearray();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid.Num");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String output = arraysPage.getEditorOutput();
				System.out.println("Editor Output: " + output);
				Assert.assertNotNull(output, "Console output is null");
			     Assert.assertFalse(output.trim().isEmpty(), "Console output is empty");
			     Assert.assertTrue(output.contains("Error occurred"), 
			         "BUG: Expected message 'Error occurred' but got: '" + output + "'");
		    }
		    
		 
		    
		    @Test(priority=21) 
		    public void ValidcodeandRunSortedSquares() {
		    	arraysPage.navigateToSearchthearray();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid.SQ");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String output = arraysPage.getConsoleOutput();
				LogHandler.info("Editor Output: " + output);
			     Assert.assertNotNull(output, "Console output is null");
			     Assert.assertFalse(output.trim().isEmpty(), "Expected output in console, but found none");
		 }
		   
		    @Test(priority=22) 
		    public void inValidcodeandRunSortedSquares() {
		    	arraysPage.navigateToSearchthearray();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid.SQ");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String alertText = arraysPage.getAlertTextAndAccept(); 
				System.out.println("Alert Text: " + alertText);
			    Assert.assertNotNull(alertText, "Expected alert was not present.");
			    Assert.assertTrue(alertText.toLowerCase().contains("error"),
				         "Expected alert to contain (SyntaxError/NameError), but got: " + alertText );
		    }
		    
		    @Test(priority=23) 
		    public void ValidcodeandSubmitSortedSquares() {
		    	arraysPage.navigateToSearchthearray();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid.SQ");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String output = arraysPage.getEditorOutput();
				System.out.println("Editor Output: " + output);
				Assert.assertNotNull(output, "Console output is null");
			     Assert.assertFalse(output.trim().isEmpty(), "Console output is empty");
			     Assert.assertTrue(output.contains("Submission Successful"),
			         "BUG: Expected message 'Submission Successful' but got: '" + output + "'");
		    }
		    
		    @Test(priority=24) 
		    public void inValidcodeandSubmitSortedSquares() {
		    	arraysPage.navigateToSearchthearray();
				Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid.SQ");
				String Codetoenter=data.get("Code");
				arraysPage.enterpythonCode(Codetoenter);
				arraysPage.clickRun();
				String output = arraysPage.getEditorOutput();
				System.out.println("Editor Output: " + output);
				Assert.assertNotNull(output, "Console output is null");
			     Assert.assertFalse(output.trim().isEmpty(), "Console output is empty");
			     Assert.assertTrue(output.contains("Error occurred"), 
			         "BUG: Expected message 'Error occurred' but got: '" + output + "'");
		    }
		    
		    @Test(priority=25) 
			 public void Signout(@Optional("NumpyNinja") String expectd) {
				 
				 arraysPage.navigateToArraysPracticeQuestions();
				 arraysPage.signOut();
				 String actualTitle=driver.getTitle();
					Assert.assertEquals(actualTitle, expectd);
					LogHandler.info("user successfully navigated back to: "+expectd);
					
			 } 
		    
	}

}
