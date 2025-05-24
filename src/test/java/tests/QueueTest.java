package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseTest;

import pageObject.DataStructurePF;
import pageObject.LoginPF;
import pageObject.QueuePF;
import utilities.LogHandler;


public class QueueTest extends BaseTest{

	QueuePF qp;
	SoftAssert softAssert;
	LoginPF lp;

	@BeforeMethod
	public void pageSetup() {
		qp= new QueuePF(driver); 
		qp.clickMainGetStartedButton();
		lp= new LoginPF(driver);
		lp.login();
		softAssert = new SoftAssert(); 

	}


	@Test(dataProvider="queuePageTitle",dataProviderClass =DataProviders.class ,priority = 1)
	public void queuePageValidation(String expectedTitle)  {
		qp.clickQueueGetBtn();
		String actualTitle=driver.getTitle();
		softAssert=new SoftAssert();
		softAssert.assertEquals(actualTitle, expectedTitle);
		Assert.assertTrue(qp.headerElementsValidation());
		LogHandler.info("user is on: " +actualTitle);


	}



	@Test(dataProvider = "queueTopics", dataProviderClass =DataProviders.class,  priority = 2)
	public void testClickEachQueueTopic(String topic) {
		qp.clickQueueGetBtn();
		qp.clickQueueTopic(topic);
		qp.verifyPageNavigation(topic);

	}

	@Test(dataProvider = "practiceQuestTitle" , priority = 3,dataProviderClass = DataProviders.class)
	public void practiceQuestionsLinkValidation(String expectedTitle) {
		qp.clickQueueGetBtn();
		qp.clickImplementationOfQueueInPython();
		qp.clickPracticeQuestionsLink();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
		LogHandler.info("user is on: " +actualTitle);	

	}



	@Test(dataProvider = "queueTopics" , priority = 4, dataProviderClass = DataProviders.class)
	public void tryhereValidationForQueue(String topic) {
		qp.clickQueueGetBtn();
		qp.clickQueueTopic(topic);
		qp.clickTryHere();
		qp.verifyTryEditortPage();

	}

	@Test(priority = 5)
	public void tryEditorValidationWithoutCodeQueue() {
		qp.clickQueueGetBtn();
		qp.clickImplementationOfQueueInPython();
		qp.clickTryHere();
		softAssert.assertTrue(qp.runButtonDisplayed());
		qp.clickRunButton();
		boolean alertPresent = qp.isAlertIsPresent();
		softAssert.assertFalse(alertPresent, "Alert is not present after clicking Run");
		LogHandler.info("alert not found");
		softAssert.assertAll();

	}
	@Test(priority=6, dataProvider = "invalidpythonCode",dataProviderClass = DataProviders.class)
	public void incorrectPythonCodeValidationQueue(String codeToEnter) {
		qp.clickQueueGetBtn();
		qp.clickImplementationOfQueueInPython();
		qp.clickTryHere();
		qp.editor(codeToEnter);
		qp.clickRunButton();
		Assert.assertTrue(qp.isAlertIsPresent());
		String text=qp.getAlertText();
		LogHandler.info("alert text is: " +text);

	}


	@Test(priority=7, dataProvider = "validpythonCode",dataProviderClass = DataProviders.class)
	public void validPythonCodeValidationQueue(String codeToEnter,String result) {
		qp.clickQueueGetBtn();
		qp.clickImplementationOfQueueInPython();
		qp.clickTryHere();
		qp.editor(codeToEnter);
		qp.clickRunButton();
		String actualResult=qp.getOutputData();
		String expectedResult=result;
		Assert.assertEquals(actualResult, expectedResult);
		LogHandler.info("answer is: " +actualResult);	
	}

	@Test(priority=8)
	public void navigateBack(@Optional("Implementation of Queue in Python") String expectd) {
		qp.clickQueueGetBtn();
		qp.clickImplementationOfQueueInPython();
		qp.clickTryHere();
		driver.navigate().back();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectd);
		LogHandler.info("user successfully navigated back to: "+expectd);			
	}


	@Test(priority=9)
	public void signoutValidation(@Optional("NumpyNinja") String expectd) {
		qp.clickQueueGetBtn();
		qp.clickImplementationOfQueueInPython();
		qp.clickSignOut();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectd);
		LogHandler.info("user successfully navigated back to: "+expectd);	
		String logoutText=	qp.logOutMessage();	
		Assert.assertEquals(logoutText,"Logged out successfully");
		LogHandler.info("user successfully navigated back to");
	}


}

