package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseTest;
import pageObject.DataStructurePF;
import pageObject.HomePF;
import pageObject.LoginPF;
import utilities.LogHandler;

public class HomeTest extends BaseTest {

	HomePF hp;
	SoftAssert softAssert;
	LoginPF lp;
	

	@BeforeMethod
	public void pageSetup() {
		lp= new LoginPF(driver);
		hp = new HomePF(driver);  
		hp.clickGetStartedButton();
		softAssert = new SoftAssert();       
	}


	@Test(dataProvider="registorTitle",dataProviderClass = DataProviders.class ,priority = 1)
	public void testClickRegistor(String expectedTitle)  {
		hp.clickRegister();
		String actualTitle=driver.getTitle();
		softAssert.assertEquals(actualTitle, expectedTitle);
		LogHandler.info("user is on: " +actualTitle);
	}


	@Test(dataProvider="loginTitle",dataProviderClass = DataProviders.class ,priority = 2)
	public void testClickLogin(String expectedTitle)  {
		hp.clickSignIn();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
		LogHandler.info("user is on: " +actualTitle);

	}

	@Test(dataProvider = "dropdownOptions", dataProviderClass = DataProviders.class,  priority = 3)
	public void testClickDropdownOption(String topic) {
		hp.clickDropdownOption(topic);
		hp.getWarningMessage();
	}


	@Test(dataProvider = "getStartedModules", dataProviderClass = DataProviders.class,  priority = 4)
	public void testClickGetStarted(String topic) {
		hp.clickGetStarted(topic);
		hp.getWarningMessage();

	}


	@Test(dataProvider = "getStartedModules" , dataProviderClass = DataProviders.class, priority = 5)
	public void testClickGetStartedlogin(String topic) {	
	lp.login();
		hp.clickGetStarted(topic);
		hp.isOnCorrectModulePage(topic);

	}


	@Test(dataProvider = "dropdownOptions", dataProviderClass = DataProviders.class,  priority = 7)
	public void testClickDropdownOptionLogin(String topic) {
		lp.login();
		hp.clickDropdownOption(topic);
		hp.isOnCorrectModulePage(topic);
	}

	@Test(priority=8)
	public void signoutValidation(@Optional("NumpyNinja") String expectd) {
		lp.login();
		hp.clickSignOut();
		String actualTitle=driver.getTitle();
		Assert.assertEquals(actualTitle, expectd);
		String logoutText=	hp.logOutMessage();	
		Assert.assertEquals(logoutText,"Logged out successfully");
		LogHandler.info("user successfully navigated back to: "+expectd);

	}

}

