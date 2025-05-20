package tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;



import hooks.Hooks;
import pageObject.DataStructureIntroPF;
import pageObject.RegisterPF;
import utilities.ExcelReader;
import utilities.LogHandler;

public class RegisterTest extends Hooks {
	RegisterPF rp;
	SoftAssert softAssert;
	
	@BeforeMethod
    public void pageSetup() {
		
		rp = new RegisterPF(driver); 
		//rp.clickOnGetStaredBtn();
		rp.clickOnRegisterLink();
        softAssert = new SoftAssert(); 
        
    }
	
	@Test(priority = 1)
	public void registerPageValidation() {
	Assert.assertTrue(rp.headerElementsValidation());
		
	}
	
	@Test(priority=2)
	public void registerValidationWithoutUserInfo(@Optional("Please fill out this field.") String expectd) {
		rp.clickOnregisterBtn();
		String actualErrorMsg=rp.alertMessageForFirstInvalidField();
		Assert.assertEquals(actualErrorMsg, expectd);
		LogHandler.info("user should see " +actualErrorMsg);
				
	}
	
	@Test(dependsOnMethods = {"registerValidationWithoutUserInfo"})
	public void registerValidationWithInvalidConfirmPwd(@Optional("password_mismatch:The two password fields didn’t match.") String expected) {
		Map<String, String> data = ExcelReader.getDataByScenario("Register", "invalidConfirmPwd");	
		String uName=data.get("username");
		String pWd=data.get("password");
		String confirmPwd=data.get("confirmpassword");
		rp.sendUserDetails(uName, pWd, confirmPwd);
		rp.clickOnregisterBtn();
		String actualMsg=rp.getErrorMsg();
		Assert.assertEquals(actualMsg, expected);
		LogHandler.info("user should see " +actualMsg);
		
	}
	
	@Test(dataProvider = "registerPageData" , priority = 4,dataProviderClass = DataSupply.class)
	public void registerValidationWithInvalidData(String sheetname,String scenariotype, String expected) {
		Map<String, String> data = ExcelReader.getDataByScenario(sheetname, scenariotype);	
		String uName=data.get("username");
		String pWd=data.get("password");
		String confirmPwd=data.get("confirmpassword");
		rp.sendUserDetails(uName, pWd, confirmPwd);
		rp.clickOnregisterBtn();
		String actualErrorMsg=rp.alertMessageForFirstInvalidField();		
		Assert.assertEquals(actualErrorMsg,expected);
		LogHandler.info("user should see " +actualErrorMsg);
		
		
	}
	
	@Test(dataProvider = "registerPageDataValid" , priority = 5,dataProviderClass = DataSupply.class)
	public void registerWithValidData(String sheetname,String scenariotype, String expected) {
		Map<String, String> data = ExcelReader.getDataByScenario(sheetname, scenariotype);	
		String uName=data.get("username");
		String pWd=data.get("password");
		String confirmPwd=data.get("confirmpassword");
		rp.sendUserDetails(uName, pWd, confirmPwd);
		rp.clickOnregisterBtn();
		String actualTitle=driver.getTitle();
		softAssert.assertEquals(actualTitle,"NumpyNinja");
		String actualErrorMsg=rp.getErrorMsg();		
		softAssert.assertTrue(actualErrorMsg.contains(expected));
		LogHandler.info("user should see " +actualErrorMsg);
		softAssert.assertAll();
	}
	
	
	@Test(priority = 6)
	public void loginButtonValidationOnRegisterPage(@Optional("Login") String expectedTitle ) {
		rp.clickOnLogin();
		String actualTitle=rp.getPageTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
		LogHandler.info("user is on " +actualTitle);
		
		
	}

}
