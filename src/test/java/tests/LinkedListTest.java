package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseTest;

import pageObject.DataStructurePF;
import pageObject.LinkedListPF;
import pageObject.LoginPF;
import utilities.LogHandler;


public class LinkedListTest extends BaseTest {

	LinkedListPF llp;
	SoftAssert softAssert;
	LoginPF lp;

	@BeforeMethod
	public void pageSetup() {
		llp = new LinkedListPF(driver);  
		llp.clickMainGetStartedButton();
		lp= new LoginPF(driver);
		lp.login();
		softAssert = new SoftAssert(); 

	}


	@Test(dataProvider="linkedListPageTitle",dataProviderClass =DataProviders.class ,priority = 1)
	public void linkedListValidation(String expectedTitle)  {
		llp.clickLLgetStatBtn();
		String actualTitle=driver.getTitle();
		softAssert=new SoftAssert();
		softAssert.assertEquals(actualTitle, expectedTitle);
		Assert.assertTrue(llp.headerElementsValidation());
		LogHandler.info("user is on: " +actualTitle);


	}

	@Test(dataProvider = "linkedListTopics", dataProviderClass =DataProviders.class,  priority = 2)
	public void testClickEachLLTopic(String topic) {
		llp.clickLLgetStatBtn();
		llp.clickLinkedlistTopic(topic);
		llp.verifyPageNavigation(topic);

	}

	@Test(dataProvider = "practiceQuestTitle" , priority = 3,dataProviderClass =DataProviders.class)
	public void practiceQuestionsLinkValidation(String expectedTitle) {
		llp.clickLLgetStatBtn();
		llp.clickIntroductionLink();
		llp.clickPracticeQuestionsLink();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
		LogHandler.info("user is on: " +actualTitle);

	}

	@Test(dataProvider = "linkedListTopics" , priority = 4,dataProviderClass = DataProviders.class)
	public void tryhereValidationForLL(String topic) {
		llp.clickLLgetStatBtn();
		llp.clickLinkedlistTopic(topic);
		llp.clickTryHere();
		llp.verifyTryEditortPage();

	}

	@Test(priority = 5)
	public void tryEditorValidationWithoutCodeLL() {
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
	@Test(priority=6, dataProvider = "invalidpythonCode",dataProviderClass = DataProviders.class)
	public void incorrectPythonCodeValidationLL(String codeToEnter) {
		llp.clickLLgetStatBtn();
		llp.clickIntroductionLink();
		llp.clickTryHere();
		llp.editor(codeToEnter);
		llp.clickRunButton();
		Assert.assertTrue(llp.isAlertIsPresent());
		String text=llp.getAlertText();
		LogHandler.info("alert text is: " +text);

	}


	@Test(priority=7, dataProvider = "validpythonCode",dataProviderClass = DataProviders.class)
	public void validPythonCodeValidationLL(String codeToEnter,String result) {
		llp.clickLLgetStatBtn();
		llp.clickIntroductionLink();
		llp.clickTryHere();
		llp.editor(codeToEnter);
		llp.clickRunButton();
		String actualResult=llp.getOutputData();
		String expectedResult=result;
		Assert.assertEquals(actualResult, expectedResult);
		LogHandler.info("answer is: " +actualResult);

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

