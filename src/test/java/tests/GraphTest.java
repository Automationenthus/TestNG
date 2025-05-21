package tests;



import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import hooks.Hooks;
import pageObject.DataStructureIntroPF;
import pageObject.GraphPF;
import utilities.ExcelReader;
import utilities.LogHandler;



public class GraphTest extends Hooks {
	
	GraphPF gp;
	SoftAssert softAssert;
	DataStructureIntroPF lp;
	
	@BeforeMethod
    public void pageSetup() {
		//WebDriver driver=DriverFactory.initDriver();
		lp= new DataStructureIntroPF(driver);
        gp = new GraphPF(driver);  
        lp.loginBackgroundForPages();
        softAssert = new SoftAssert(); 
        
    }
	
	
	@Test(dataProvider="graphPageTitle", dataProviderClass =DataSupply.class, priority = 1)
		public void graphPageValidation(String expectedTitle)  {
		gp.getStartbtnclick();
		 String actualTitle=driver.getTitle();
		 softAssert=new SoftAssert();
		 softAssert.assertEquals(actualTitle, expectedTitle);
		Assert.assertTrue(gp.headerElementsValidation());
		//LogHandler.info("user is on: " +actualTitle);
		 
				
	}
	
	@Test(priority = 2)
	public void clickGraph(@Optional("Graph") String expectd) {
		
		gp.getStartbtnclick();
		gp.clickGraph();
		String actualTitle=driver.getTitle();
		String expectedTitle=expectd;
		Assert.assertEquals(actualTitle, expectedTitle);
		//LogHandler.info("user is on: " +actualTitle);
		 
	}
	
	@Test(priority = 3)
	public void clickGraphRep(@Optional("Graph Representations") String expectd) {
		
		gp.getStartbtnclick();
		gp.clickGraphRepresentations();
		String actualTitle=driver.getTitle();
		String expectedTitle=expectd;
		Assert.assertEquals(actualTitle, expectedTitle);
		//LogHandler.info("user is on: " +actualTitle);
		 
	}
	
	
	@Test(dataProvider = "practiceQuestTitle", dataProviderClass = DataSupply.class, priority = 4)
	public void practiceQuestionsLinkValidation(String expectedTitle) {
		gp.getStartbtnclick();
		gp.clickGraph();
		gp.clickPracticeQuestionsLink();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
		//LogHandler.info("user is on: " +actualTitle);
		 
		
		
	}
	
	@Test(dataProvider = "tryEditorTitle" , priority = 5, dataProviderClass = DataSupply.class)
	public void tryhereValidation(String expectedTitle) {
		gp.getStartbtnclick();
		gp.clickGraph();
		gp.clickTryHere();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
		//LogHandler.info("user is on: " +actualTitle);
		 
			
	}
	
	@Test(priority = 6)
	public void tryEditorValidationWithoutCode() {
		gp.getStartbtnclick();
		gp.clickGraph();
		gp.clickTryHere();
		softAssert.assertTrue(gp.runButtonDisplayed());
		gp.clickRunButton();
		boolean alertPresent = gp.isAlertIsPresent();
		softAssert.assertFalse(alertPresent, "Alert is not present after clicking Run");
		//LogHandler.info("alert not found");
		softAssert.assertAll();
		
	}
	@Test(priority=7)
	public void incorrectPythonCodeValidation() {
		gp.getStartbtnclick();
		gp.clickGraph();
		gp.clickTryHere();
		Map<String, String> data = ExcelReader.getDataByScenario("Pythoncode", "incorrectCode");
		String codeToEnter=data.get("Pcode");
		gp.editor(codeToEnter);
		gp.clickRunButton();
		Assert.assertTrue(gp.isAlertIsPresent());
		String text=gp.getAlertText();
		LogHandler.info("alert text is: " +text);
		 

		
		
	}
	@Test(priority=8)
	public void validPythonCodeValidation() {
		gp.getStartbtnclick();
		gp.clickGraph();
		gp.clickTryHere();
		Map<String, String> data = ExcelReader.getDataByScenario("Pythoncode", "valid");
		String codeToEnter=data.get("Pcode");
		gp.editor(codeToEnter);
		gp.clickRunButton();
		String actualResult=gp.getOutputData();
		String expectedResult=data.get("Result");
		Assert.assertEquals(actualResult, expectedResult);
		LogHandler.info("answer is: " +actualResult);
		
	}
	
	@Test(priority=9)
	public void navigateBack(@Optional("Graph") String expectd) {
		gp.getStartbtnclick();
		gp.clickGraph();
		gp.clickTryHere();
		driver.navigate().back();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectd);
		LogHandler.info("user successfully navigated back to: "+expectd);
		
				
	}
	
	
	@Test(dataProvider = "tryEditorTitle" , priority = 10,dataProviderClass = DataSupply.class)
	public void tryhereValidationForGR(String expectedTitle) {
		gp.getStartbtnclick();
		gp.clickGraphRepresentations();
		gp.clickTryHere();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
		LogHandler.info("user is on: " +actualTitle);
		 
			
	}
	
	@Test(priority =11 )
	public void tryEditorValidationWithoutCodeForGR() {
		gp.getStartbtnclick();
		gp.clickGraphRepresentations();
		gp.clickTryHere();
		softAssert.assertTrue(gp.runButtonDisplayed());
		gp.clickRunButton();
		boolean alertPresent = gp.isAlertIsPresent();
		softAssert.assertFalse(alertPresent, "Alert is not present after clicking Run");
		LogHandler.info("alert not found");
		softAssert.assertAll();
		
	}
	@Test(priority=12)
	public void incorrectPythonCodeValidationForGR() {
		gp.getStartbtnclick();
		gp.clickGraphRepresentations();
		gp.clickTryHere();
		Map<String, String> data = ExcelReader.getDataByScenario("Pythoncode", "incorrectCode");
		String codeToEnter=data.get("Pcode");
		gp.editor(codeToEnter);
		gp.clickRunButton();
		Assert.assertTrue(gp.isAlertIsPresent());
		String text=gp.getAlertText();
		LogHandler.info("alert text is: " +text);
		 

		
		
	}
	@Test(priority=13)
	public void validPythonCodeValidationForGR() {
		gp.getStartbtnclick();
		gp.clickGraphRepresentations();
		gp.clickTryHere();
		Map<String, String> data = ExcelReader.getDataByScenario("Pythoncode", "valid");
		String codeToEnter=data.get("Pcode");
		gp.editor(codeToEnter);
		gp.clickRunButton();
		String actualResult=gp.getOutputData();
		String expectedResult=data.get("Result");
		Assert.assertEquals(actualResult, expectedResult);
		LogHandler.info("answer is: " +actualResult);
		
	}
	
	@Test(priority=14)
	public void navigateBackForGR(@Optional("Graph Representations") String expectd) {
		gp.getStartbtnclick();
		gp.clickGraphRepresentations();
		gp.clickTryHere();
		driver.navigate().back();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectd);
		LogHandler.info("user successfully navigated back to: "+expectd);
	}	
	
	@Test(priority=15)
	public void signoutValidation() {
		gp.getStartbtnclick();
		gp.clickGraph();
		gp.clickSignOut();
	    String logoutText=	gp.logOutMessage();	
		Assert.assertEquals(logoutText,"Logged out successfully");
		LogHandler.info("user successfully navigated back to");
		
	}
	

}


