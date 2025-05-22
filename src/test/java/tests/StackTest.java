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
import pageObject.StackPF;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LogHandler;



public class StackTest extends Hooks {
	StackPF stackpage;
    WebDriver driver;
    
    @BeforeMethod
    public void loginAndSetUp() {
        driver = DriverFactory.getDriver();
        stackpage = new StackPF(driver);
        driver.get(ConfigReader.getProperties("url"));
        driver.findElement(By.linkText("Sign in")).click();
        driver.findElement(By.id("id_username")).sendKeys(ConfigReader.getProperties("username"));
        driver.findElement(By.id("id_password")).sendKeys(ConfigReader.getProperties("password"));
        driver.findElement(By.xpath("//input[@value='Login']")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.titleContains("NumpyNinja"));

        Assert.assertTrue(driver.getTitle().contains("NumpyNinja"), "Login failed or incorrect page");
    }
    
    @Test(priority=1) 
    public void verifyUserIsInStackPage() {
    	stackpage.navigateTostackPage();
    	LogHandler.info("Navigated to Stack page.");
        Assert.assertTrue(driver.getTitle().contains("Stack"), "incorrect page");
    }
    

    @Test (dataProvider = "StackLinks",dataProviderClass =DataSupply.class,priority=2)
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
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "valid");
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
			Map<String, String> data = ExcelReader.getDataByScenario("PracticeQuestion", "invalid");
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

