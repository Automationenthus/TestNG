package tests;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import base.BaseTest;
import driverFactory.DriverFactory;
import pageObject.ArrayPF;
import pageObject.DataStructurePF;
import pageObject.LoginPF;

import driverFactory.DriverFactory;
import pageObject.ArrayPF;
import pageObject.HomePF;
import pageObject.LoginPF;
import pageObject.HomePF;

import utilities.ExcelReader;
import utilities.LogHandler;



public class ArrayTest extends BaseTest {
	ArrayPF arraysPage;
	LoginPF lp;


	@BeforeMethod
	public void loginAndSetUp() {
		arraysPage = new ArrayPF(driver);
		arraysPage.clickMainGetStartedButton();
		lp=new LoginPF(driver);
		 lp.login();
	}

	@Test(priority=1) 
	public void verifyUserIsInArrayPage() {
		arraysPage.navigateToArraysPage();
		Assert.assertTrue(driver.getTitle().contains("Array"), "incorrect page");
	}



	@Test (dataProvider = "ArraysLinks",dataProviderClass =DataProviders.class,priority=2 )
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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "valid");
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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "invalid");
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



	@Test(dataProvider = "questionLinks",dataProviderClass =DataProviders.class, priority=8)
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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "valid.sa");

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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "invalid.sa");

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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "valid.sa");

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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "invalid.sa");

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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "valid.max");

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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "invalid.max");

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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "valid.max");

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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "invalid.max");

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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "valid.Num");

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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "invalid.Num");

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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "valid.Num");

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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "invalid.Num");

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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "valid.SQ");

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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "invalid.SQ");

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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "valid.SQ");

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
		Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "invalid.SQ");

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