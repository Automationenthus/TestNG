package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import driverFactory.DriverFactory;
import pageObject.HomePF;
import utilities.LogHandler;

public class HomeTest {
	WebDriver driver;
	HomePF hpf;
	
	public static final String expectedHomeMessage = "You are not logged in";
	
	@BeforeMethod
	@Parameters("browser")
	public void setup(@Optional("chrome")String browser) 
	{
			
		DriverFactory.setUp(browser);
		driver = DriverFactory.getDriver();
		hpf = new HomePF();
		hpf.launchurl();
		LogHandler.info("Launching URL");
		hpf.getstartedbuttonclick();
	}
	
	@Test(priority = 1)
	public void the_user_should_see_all_header_elements() {
		Assert.assertTrue(hpf.isNumpyNinjaVisible());
		Assert.assertTrue(hpf.isDropdownVisible());
		Assert.assertTrue(hpf.isRegisterVisible());
		Assert.assertTrue(hpf.isSignin());
		
		LogHandler.info("All the elements are visible in header");

}
}

