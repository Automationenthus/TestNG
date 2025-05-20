package tests;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import driverFactory.DriverFactory;
import hooks.Hooks;
import pageObject.DataStructureIntroPF;
import utilities.ExcelReader;
import utilities.LogHandler;


public class DataStructureIntroTest extends Hooks {
	
	DataStructureIntroPF dp;
	SoftAssert softAssert;
	
	@BeforeMethod
    public void pageSetup() {
		dp = new DataStructureIntroPF(driver);  
        dp.loginBackgroundForPages();
        softAssert = new SoftAssert(); 
        
    }
	
	
	@Test(dataProvider="dataStructureTitle",dataProviderClass =DataSupply.class ,priority = 1)
		public void dataStructureValidation(String expectedTitle)  {
		dp.getStartbtnclick();
		 String actualTitle=driver.getTitle();
		 softAssert=new SoftAssert();
		 softAssert.assertEquals(actualTitle, expectedTitle);
		Assert.assertTrue(dp.headerElementsValidation());
		LogHandler.info("user is on: " +actualTitle);
		 
				
	}
	
	@Test(priority = 2)
	public void timeComplexity(@Optional("Time Complexity") String expectd) {
		
		dp.getStartbtnclick();
		dp.timeComplexity();
		String actualTitle=driver.getTitle();
		String expectedTitle=expectd;
		Assert.assertEquals(actualTitle, expectedTitle);
		LogHandler.info("user is on: " +actualTitle);
		 
	}
	
	
	@Test(dataProvider = "practiceQuestTitle" , priority = 3,dataProviderClass = DataSupply.class)
	public void practiceQuestionsLinkValidation(String expectedTitle) {
		dp.getStartbtnclick();
		dp.timeComplexity();
		dp.clickOnPracticeQuest();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
		LogHandler.info("user is on: " +actualTitle);
		 
		
		
	}
	
	@Test(dataProvider = "tryEditorTitle" , priority = 4,dataProviderClass = DataSupply.class)
	public void tryhereValidation(String expectedTitle) {
		dp.getStartbtnclick();
		dp.timeComplexity();
		dp.clickOnTryHere();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
		LogHandler.info("user is on: " +actualTitle);
		 
			
	}
	
	@Test(priority = 5)
	public void tryEditorValidationWithoutCode() {
		dp.getStartbtnclick();
		dp.timeComplexity();
		dp.clickOnTryHere();
		softAssert.assertTrue(dp.runButtonDisplayed());
		dp.clickOnRunBtn();
		boolean alertPresent = dp.isAlertIsPresent();
		softAssert.assertFalse(alertPresent, "Alert is not present after clicking Run");
		LogHandler.info("alert not found");
		softAssert.assertAll();
		
	}
	@Test(priority=6)
	public void incorrectPythonCodeValidation() {
		dp.getStartbtnclick();
		dp.timeComplexity();
		dp.clickOnTryHere();
		Map<String, String> data = ExcelReader.getDataByScenario("Pythoncode", "incorrectCode");
		String codeToEnter=data.get("Pcode");
		dp.editor(codeToEnter);
		dp.clickOnRunBtn();
		Assert.assertTrue(dp.isAlertIsPresent());
		String text=dp.getAlertText();
		LogHandler.info("alert text is: " +text);
		 

		
		
	}
	@Test(priority=7)
	public void validPythonCodeValidation() {
		dp.getStartbtnclick();
		dp.timeComplexity();
		dp.clickOnTryHere();
		Map<String, String> data = ExcelReader.getDataByScenario("Pythoncode", "valid");
		String codeToEnter=data.get("Pcode");
		dp.editor(codeToEnter);
		dp.clickOnRunBtn();
		String actualResult=dp.getOutputData();
		String expectedResult=data.get("Result");
		Assert.assertEquals(actualResult, expectedResult);
		LogHandler.info("answer is: " +actualResult);
		
	}
	
	@Test(priority=8)
	public void navigateBack(@Optional("Time Complexity") String expectd) {
		dp.getStartbtnclick();
		dp.timeComplexity();
		dp.clickOnTryHere();
		driver.navigate().back();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectd);
		LogHandler.info("user successfully navigated back to: "+expectd);
		
				
	}
	
	@Test(priority=9)
	public void signoutValidation(@Optional("NumpyNinja") String expectd) {
		dp.getStartbtnclick();
		dp.timeComplexity();
		dp.clickOnTryHere();
		driver.navigate().back();
		dp.signOut();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectd);
		LogHandler.info("user successfully navigated back to: "+expectd);
		
	}
	

}
