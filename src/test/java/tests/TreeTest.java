package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseTest;
import pageObject.DataStructurePF;
import pageObject.LoginPF;
import pageObject.TreePF;
import utilities.LogHandler;

public class TreeTest extends BaseTest{
	TreePF tp;
	DataStructurePF dp;
	LoginPF lp;
	SoftAssert softAssert;

	@BeforeMethod
	public void pageSetup() {
		tp = new TreePF(driver);
		tp.clickMainGetStartedButton();
		lp=new LoginPF(driver);
		lp.login();
		dp=new 	DataStructurePF(driver);
		softAssert = new SoftAssert(); 
	}

	@Test(priority=1)
	public void treePageValidation(@Optional("Tree") String expectd) {
		tp.clickOnGetStartBtn();
		Assert.assertEquals(tp.getPageTitle(),expectd);
		LogHandler.info("user is on: " +tp.getPageTitle());
	}

	@Test(dependsOnMethods = {"treePageValidation"})
	public void headrElementsValidation() {
		Assert.assertTrue(dp.headerElementsValidation());

	}

	@Test(priority=2,dataProvider="treePageLinks",dataProviderClass = DataProviders.class)
	public void treePageLinksValidation(String topic,String expectedTitle) {
		tp.clickOnGetStartBtn();
		tp.clickTopic(topic);
		String actualTitle=tp.getPageTitle();
		softAssert.assertEquals(actualTitle, expectedTitle);
		softAssert.assertAll();

	}

	@Test(priority=3,dataProvider="treeLinks",dataProviderClass = DataProviders.class)
	public void tryHereValidation(String topic,String tryHeretitle){
		tp.clickOnGetStartBtn();
		tp.clickOnOverviewOfTree();
		tp.clickOnTreeLinks(topic);
		tp.clickOnTryhere();
		String actualTitle=tp.getPageTitle();
		softAssert.assertEquals(actualTitle, tryHeretitle);
		softAssert.assertAll();

	}

	@Test(priority=4)
	public void practiceQuestionValidation(@Optional("Practice Questions") String expectedTitle) {
		tp.clickOnGetStartBtn();
		tp.clickOnOverviewOfTree();
		tp.clickOnPracticeQuestions();
		String actualTitle=tp.getPageTitle();
		Assert.assertEquals(actualTitle, expectedTitle);

	}

	@Test(priority = 5)
	public void tryEditorValidationWithoutCode() {
		tp.clickOnGetStartBtn();
		tp.clickOnOverviewOfTree();
		tp.clickOnTryhere();
		softAssert.assertTrue(dp.runButtonDisplayed());
		dp.clickOnRunBtn();
		boolean alertPresent = dp.isAlertIsPresent();
		softAssert.assertFalse(alertPresent, "Alert is not present after clicking Run");
		LogHandler.info("alert not found");
		softAssert.assertAll();

	}
	@Test(priority=6,dataProvider = "invalidpythonCode",dataProviderClass = DataProviders.class)
	public void incorrectPythonCodeValidation(String codeToEnter) {
		tp.clickOnGetStartBtn();
		tp.clickOnOverviewOfTree();
		tp.clickOnTryhere();
		dp.editor(codeToEnter);
		dp.clickOnRunBtn();
		Assert.assertTrue(dp.isAlertIsPresent());
		String text=dp.getAlertText();
		LogHandler.info("alert text is: " +text);




	}
	@Test(priority=7,dataProvider = "validpythonCode",dataProviderClass = DataProviders.class)
	public void validPythonCodeValidation(String codeToEnter,String result) {
		tp.clickOnGetStartBtn();
		tp.clickOnOverviewOfTree();
		tp.clickOnTryhere();
		dp.editor(codeToEnter);
		dp.clickOnRunBtn();
		String actualResult=dp.getOutputData();
		String expectedResult=result;
		Assert.assertEquals(actualResult, expectedResult);
		LogHandler.info("answer is: " +actualResult);

	}
	@Test(priority=8)
	public void navigateBack(@Optional("Overview of Trees") String expectd) {
		tp.clickOnGetStartBtn();
		tp.clickOnOverviewOfTree();
		tp.clickOnTryhere();
		driver.navigate().back();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectd);
		LogHandler.info("user successfully navigated back to: "+expectd);


	}

	@Test(priority=9)
	public void signoutValidation(@Optional("NumpyNinja") String expectd) {
		tp.clickOnGetStartBtn();
		tp.clickOnOverviewOfTree();
		dp.signOut();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectd);
		LogHandler.info("user successfully navigated back to: "+expectd);

	}

}




