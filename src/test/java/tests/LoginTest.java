package tests;


	import org.testng.Assert;
	import org.testng.annotations.BeforeMethod;
	import org.testng.annotations.Test;

import base.BaseTest;
import pageObject.HomePF;
	import pageObject.LoginPF;
	import utilities.LogHandler;

	public class LoginTest extends BaseTest {

		HomePF hpf;
		LoginPF lpf;

		@BeforeMethod
		public void inintPageObjects() {
			lpf = new LoginPF(driver);
			lpf.click_homesignin();
		}

		@Test(priority = 1)
		public void the_user_leave_the_username_field_empty_and_click_login() {
			
			lpf.clickLoginwithoutusername();
			String actual_message = lpf.alertMessageForFirstInvalidField();
			String expected_message = "Please fill out this field.";
			Assert.assertEquals(actual_message, expected_message);
			LogHandler.info(actual_message);

		}

		@Test(priority = 2)
		public void the_user_leave_the_password_field_empty_and_click_login() {
			lpf.clickLoginwithoutpassword();
			String expected_message = "Please fill out this field.";
			String actual_message = lpf.alertMessageForFirstInvalidField();
			Assert.assertEquals(actual_message, expected_message);
			LogHandler.info(actual_message);

		}

		@Test( priority = 3,dataProvider = "inValidLoginData", dataProviderClass = DataProviders.class)
		public void testblankLogin(String Username, String Password) {
			lpf.sendUserdetails(Username, Password);
			String actualText=lpf.getErrorMessage();
			String expectedText="Invalid Username and Password";
			Assert.assertEquals(actualText, expectedText);
			LogHandler.info("Invalid Username and Password");
		}

		@Test(priority = 4,dataProvider = "ValidLoginData", dataProviderClass = DataProviders.class)
		public void testValidLogin(String Username, String Password) {
			lpf.sendUserdetails(Username, Password);
			String actualTitle=lpf.pageTitle();
			String expectedTitle="NumpyNinja";
			Assert.assertEquals(actualTitle, expectedTitle);
			String actualText=lpf.getErrorMessage();
			String expectedText="You are logged in";
			Assert.assertEquals(actualText, expectedText);
			
			}
		
		@Test(priority=5)
		public void registerLinkValidation() {
			lpf.clickOnRegister();
			String actualTitle=lpf.pageTitle();
			String expectedtitle="Registration";
			Assert.assertEquals(actualTitle, expectedtitle);
		}
	}

