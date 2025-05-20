package tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import hooks.Hooks;
import pageObject.DataStructureIntroPF;
import pageObject.TreePF;
import utilities.ExcelReader;
import utilities.LogHandler;

public class TreeTest extends Hooks{
	TreePF tp;
	DataStructureIntroPF dp;
	SoftAssert softAssert;
	
	@BeforeMethod
    public void pageSetup() {
		tp = new TreePF(driver); 
        dp=new 	DataStructureIntroPF(driver);
        dp.loginBackgroundForPages();
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
	
	@Test(priority=2,dataProvider="treePageLinks",dataProviderClass = DataSupply.class)
	public void treePageLinksValidation(String topic,String expectedTitle) {
		tp.clickOnGetStartBtn();
		tp.clickTopic(topic);
		String actualTitle=tp.getPageTitle();
		softAssert.assertEquals(actualTitle, expectedTitle);
		softAssert.assertAll();
		
	}

	@Test(priority=3,dataProvider="treeLinks",dataProviderClass = DataSupply.class)
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
	@Test(priority=6)
	public void incorrectPythonCodeValidation() {
		tp.clickOnGetStartBtn();
		tp.clickOnOverviewOfTree();
		tp.clickOnTryhere();
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
		tp.clickOnGetStartBtn();
		tp.clickOnOverviewOfTree();
		tp.clickOnTryhere();
		Map<String, String> data = ExcelReader.getDataByScenario("Pythoncode", "validCode");
		String codeToEnter=data.get("Pcode");
		dp.editor(codeToEnter);
		dp.clickOnRunBtn();
		String actualResult=dp.getOutputData();
		String expectedResult=data.get("Result");
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
	
	


