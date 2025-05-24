package tests;

import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import hooks.Hooks;
import pageObject.DataStructureIntroPF;
import pageObject.LinkedListPF;
import utilities.ExcelReader;
import utilities.LogHandler;


public class LinkedListTest extends Hooks {
	
	LinkedListPF llp;
	SoftAssert softAssert;
	DataStructureIntroPF lp;
	
	@BeforeMethod
    public void pageSetup() {
		//WebDriver driver=DriverFactory.initDriver();
		lp= new DataStructureIntroPF(driver);
        llp = new LinkedListPF(driver);  
        lp.loginBackgroundForPages();
        softAssert = new SoftAssert(); 
        
    }
	
	
	@Test(dataProvider="linkedListPageTitle",dataProviderClass =DataSupply.class ,priority = 1)
		public void dataStructureValidation(String expectedTitle)  {
		llp.clickLLgetStatBtn();
		 String actualTitle=driver.getTitle();
		 softAssert=new SoftAssert();
		 softAssert.assertEquals(actualTitle, expectedTitle);
		Assert.assertTrue(llp.headerElementsValidation());
		LogHandler.info("user is on: " +actualTitle);
		 
				
	}
		
	@Test(dataProvider = "linkedListTopics", dataProviderClass =DataSupply.class,  priority = 2)
	public void testClickEachQueueTopic(String topic) {
		llp.clickLLgetStatBtn();
	    llp.clickLinkedlistTopic(topic);
	    llp.verifyPageNavigation(topic);

	}
	
	@Test(dataProvider = "practiceQuestTitle" , priority = 3,dataProviderClass = DataSupply.class)
	public void practiceQuestionsLinkValidation(String expectedTitle) {
		llp.clickLLgetStatBtn();
		llp.clickIntroductionLink();
		llp.clickPracticeQuestionsLink();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
		LogHandler.info("user is on: " +actualTitle);
		 
		
		
	}
	
	@Test(dataProvider = "linkedListTopics" , priority = 4,dataProviderClass = DataSupply.class)
	public void tryhereValidationForQueue(String topic) {
		llp.clickLLgetStatBtn();
		llp.clickLinkedlistTopic(topic);
		llp.clickTryHere();
		llp.verifyTryEditortPage();
		
		 
			
	}
	
	@Test(priority = 5)
	public void tryEditorValidationWithoutCodeQueue() {
		llp.clickLLgetStatBtn();
		llp.clickIntroductionLink();
		llp.clickTryHere();
		softAssert.assertTrue(llp.runButtonDisplayed());
		llp.clickRunButton();
		boolean alertPresent = llp.isAlertIsPresent();
		softAssert.assertFalse(alertPresent, "Alert is not present after clicking Run");
		LogHandler.info("alert not found");
		softAssert.assertAll();
		
	}
	@Test(priority=6)
	public void incorrectPythonCodeValidationQueue() {
		llp.clickLLgetStatBtn();
		llp.clickIntroductionLink();
		llp.clickTryHere();
		Map<String, String> data = ExcelReader.getDataByScenario("Pythoncode", "incorrectCode");
		String codeToEnter=data.get("Pcode");
		llp.editor(codeToEnter);
		llp.clickRunButton();
		Assert.assertTrue(llp.isAlertIsPresent());
		String text=llp.getAlertText();
		LogHandler.info("alert text is: " +text);
		 	
	}
	
	
	@Test(priority=7)
	public void validPythonCodeValidationQueue() {
		llp.clickLLgetStatBtn();
		llp.clickIntroductionLink();
		llp.clickTryHere();
		Map<String, String> data = ExcelReader.getDataByScenario("Pythoncode", "valid");
		String codeToEnter=data.get("Pcode");
		llp.editor(codeToEnter);
		llp.clickRunButton();
		String actualResult=llp.getOutputData();
		String expectedResult=data.get("Result");
		Assert.assertEquals(actualResult, expectedResult);
		//LogHandler.info("answer is: " +actualResult);
		
	}
	
	@Test(priority=8)
	public void navigateBackForLL(@Optional("Introduction") String expectd) {
		llp.clickLLgetStatBtn();
		llp.clickIntroductionLink();
		llp.clickTryHere();
		driver.navigate().back();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectd);
		LogHandler.info("user successfully navigated back to: "+expectd);
		
				
	}
	
	@Test(priority=9)
	public void signoutValidationLL(@Optional("NumpyNinja") String expectd) {
		llp.clickLLgetStatBtn();
		llp.clickIntroductionLink();
		llp.clickSignOut();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectd);
		LogHandler.info("user successfully navigated back to: "+expectd);
		String logoutText=	llp.logOutMessage();	
		Assert.assertEquals(logoutText,"Logged out successfully");
		LogHandler.info("user successfully navigated back to");
	}
	

}

