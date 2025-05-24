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

import base.BaseTest;
import driverFactory.DriverFactory;
import pageObject.DataStructurePF;
import pageObject.LoginPF;
import pageObject.StackPF;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LogHandler;



public class StackTest extends BaseTest {
	StackPF stackpage;
	LoginPF lp;
	DataStructurePF dp;
   
    
    @BeforeMethod
    public void loginAndSetUp() {
        
        stackpage = new StackPF(driver);
        lp=new LoginPF(driver);
       // lp.login();
        dp=new 	DataStructurePF(driver);
        dp.loginBackgroundForPages();
    
    }
    
    @Test(priority=1) 
    public void verifyUserIsInStackPage() {
    	stackpage.navigateTostackPage();
    	LogHandler.info("Navigated to Stack page.");
        Assert.assertTrue(driver.getTitle().contains("Stack"), "incorrect page");
    }
    

    @Test (dataProvider = "StackLinks",dataProviderClass =DataProviders.class,priority=2)
	 public void verifyStacksNavigation(String linktext) {
	 
    	 stackpage.clickGettingStarted();
		 Assert.assertTrue(driver.getTitle().contains("Stack"), "Navigation to Stack failed");
		 stackpage.clickStackLink(linktext);
		 Assert.assertTrue(driver.getTitle().contains(linktext), "Navigation to " + linktext + " failed");
}
    
    @Test(priority=3)
	 public void VerifyTryHere() {
		 
    	stackpage.navigateToStackstryeditorPage();
    	LogHandler.info("Navigated to Try Editor page.");
		 Assert.assertTrue(stackpage.isRunButtonVisible(), "Run button not visible");
	 }
    
    @Test(priority=4)
    public void verifyRunButtonWithoutEnteringCode() {
        
    	stackpage.navigateToStackstryeditorPage();
        Assert.assertTrue(stackpage.isRunButtonVisible(), "Run button not visible");
        stackpage.clickRunButton();
        String alertText = stackpage.getAlertTextAndAccept();
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
		stackpage.navigateToStackstryeditorPage();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "valid");
			String Codetoenter=data.get("Code");
		   stackpage.enterpythonCode(Codetoenter);
			stackpage.clickRunButton();
			String output = stackpage.getEditorOutput();
	         Assert.assertNotNull(output, "Output is null");
	         Assert.assertFalse(output.trim().isEmpty(), "Output is empty");
	         LogHandler.info("answer is: " +output);
	 }

	 @Test(priority=6)
		public void InvalidPythonCodeforTryEditor() {
		 stackpage.navigateToStackstryeditorPage();
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestions", "invalid");
			String Codetoenter=data.get("Code");
			stackpage.enterpythonCode(Codetoenter);
			stackpage.clickRunButton();
			String alertText = stackpage.getAlertTextAndAccept(); 
		    Assert.assertNotNull(alertText, "Expected alert was not present.");
		    Assert.assertTrue(alertText.toLowerCase().contains("nameerror"),
		            "Expected alert to contain 'NameError', but got: " + alertText);
		}


@Test(priority=7) //(description = "Verify navigation to Practice Questions page and mark BUG)
public void test_Application_Practice_Questions_Link_Stack_Bug() {
 stackpage.navigateTostackspracticequestion();
 String actualTitle = driver.getTitle();
 String expectedTitle = "Practice Questions"; 
 Assert.assertEquals(actualTitle.trim(), expectedTitle, 
     "BUG: Practice Questions page is not loading correctly.");
}

@Test(priority=8) 
public void Signout(@Optional("NumpyNinja") String expectd) {
 
 stackpage.navigateToStackstryeditorPage();
 stackpage.signOut();
 String actualTitle=driver.getTitle();
	Assert.assertEquals(actualTitle, expectd);
	LogHandler.info("user successfully navigated back to: "+expectd);
	
} 
}

