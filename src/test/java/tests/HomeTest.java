package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import driverFactory.DriverFactory;
import hooks.Hooks;
import pageObject.HomePF;
import utilities.LogHandler;

public class HomeTest extends Hooks {
//	WebDriver driver;
//	HomePF hpf = new HomePF();
	

	public static final String expectedHomeMessage = "You are not logged in";

	@Test(priority = 1)
	public void the_user_should_see_all_header_elements() {
		Assert.assertTrue(hmpf.isNumpyNinjaVisible());
		Assert.assertTrue(hmpf.isDropdownVisible());
		Assert.assertTrue(hmpf.isRegisterVisible());
		Assert.assertTrue(hmpf.isSignin());

		LogHandler.info("All the elements are visible in header");

	}

	@Test(priority = 2)
	public void the_user_clicks_getstarted_without_login() {

	}

}
