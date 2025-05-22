package tests;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import driverFactory.DriverFactory;
import hooks.Hooks;
import pageObject.ArrayPF;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LogHandler;


public class ArrayTest extends Hooks {
	ArrayPF arraysPage;
    WebDriver driver;

    @BeforeMethod
    public void loginAndSetUp() {
        driver = DriverFactory.getDriver();
        arraysPage = new ArrayPF(driver);
        driver.get(ConfigReader.getProperty("url"));
        driver.findElement(By.linkText("Sign in")).click();
        driver.findElement(By.id("id_username")).sendKeys(ConfigReader.getProperty("username"));
        driver.findElement(By.id("id_password")).sendKeys(ConfigReader.getProperty("password"));
        driver.findElement(By.xpath("//input[@value='Login']")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.titleContains("NumpyNinja"));

        Assert.assertTrue(driver.getTitle().contains("NumpyNinja"), "Login failed or incorrect page");
    }
    
    @Test(priority=1) 
    public void verifyUserIsInArrayPage() {
    	arraysPage.navigateToArraysPage();
    	LogHandler.info("Navigated to Arrays page.");
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
		 LogHandler.info("Navigated to Try Editor page.");
		 Assert.assertTrue(arraysPage.isRunButtonVisible(), "Run button not visible");
	 }
	 
	 @Test(priority=4) 
	    public void verifyRunButtonWithoutEnteringCode() {
	        
	        arraysPage.navigateToTryEditorPage();
	        Assert.assertTrue(arraysPage.isRunButtonVisible(), "Run button not visible");
	        arraysPage.clickRunButton();
	        String alertText = arraysPage.getAlertTextAndAccept();
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
		 LogHandler.info("Navigated to Practice Question page.");
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
			LogHandler.info("Editor Output: " + output);
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
			LogHandler.info("Alert Text: " + alertText);
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
			LogHandler.info("Editor Output: " + output);
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
			LogHandler.info("Editor Output: " + output);
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
			LogHandler.info("Alert Text: " + alertText);
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
			LogHandler.info("Editor Output: " + output);
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
			LogHandler.info("Editor Output: " + output);;
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
			LogHandler.info("Alert Text: " + alertText);
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
			LogHandler.info("Editor Output: " + output);
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
			LogHandler.info("Editor Output: " + output);
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
			LogHandler.info("Alert Text: " + alertText);
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
			LogHandler.info("Editor Output: " + output);
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
			LogHandler.info("Editor Output: " + output);
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