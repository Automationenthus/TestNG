package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import hooks.Hooks;
import pageObject.HomePF;
import pageObject.LoginPF;
import utilities.DataProvider_Class;
import utilities.LogHandler;

public class LoginTest extends Hooks {

	HomePF hpf;
	LoginPF lpf;

	@BeforeMethod
	public void inintPageObjects() {
		hpf = new HomePF();
		lpf = new LoginPF(driver);
		hpf.launchurl();
		hpf.getstartedbuttonclick();
		lpf.click_homesignin();
	}

	@Test(priority = 1)
	public void the_user_leave_the_username_field_empty_and_click_login() {
		lpf.clickLoginwithoutusername();
		String expected_message = "Please fill out this field.";
		String actual_message = lpf.isUsernameFieldEmpty();
		Assert.assertEquals(actual_message, expected_message);
		LogHandler.info(actual_message);

	}

	@Test(priority = 2)
	public void the_user_leave_the_password_field_empty_and_click_login() {
		lpf.clickLoginwithoutusername();
		String expected_message = "Please fill out this field.";
		String actual_message = lpf.isUsernameFieldEmpty();
		Assert.assertEquals(actual_message, expected_message);
		LogHandler.info(actual_message);

	}

	@Test( priority = 3,dataProvider = "inValidLoginData", dataProviderClass = DataProvider_Class.class)
	public void testblankLogin(String Username, String Password) {
		LoginPF loginPage = new LoginPF(driver);
		lpf.click_homesignin();
		if (Username == null)
			throw new IllegalArgumentException("Username is null");
		loginPage.login_user(Username);
		if (Password == null)
			throw new IllegalArgumentException("Password is null");
		loginPage.login_passowrd(Password);
		loginPage.login_button();

		Assert.assertTrue(loginPage.isinvalidcredvalidatio_message_displayed(), "Invalid Username and Password");
		LogHandler.info("Invalid Username and Password");
	}

	@Test(priority = 4,dataProvider = "ValidLoginData", dataProviderClass = DataProvider_Class.class)
	public void testValidLogin(String Username, String Password) {
		LoginPF loginPage = new LoginPF(driver);
		lpf.click_homesignin();
		if (Username == null)
			throw new IllegalArgumentException("Username is null");
		loginPage.login_user(Username);
		if (Password == null)
			throw new IllegalArgumentException("Password is null");
		loginPage.login_passowrd(Password);
		loginPage.login_button();

		Assert.assertTrue(loginPage.isyourlogedinmessagevisible(), "You are logged in");
		LogHandler.info("You are logged in");
	}
}