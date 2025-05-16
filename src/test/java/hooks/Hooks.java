package hooks;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import driverFactory.DriverFactory;
import utilities.ConfigReader;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

import java.util.HashSet;
import java.util.Set;

public class Hooks {

	public WebDriver driver;
	
	ConfigReader config = new ConfigReader();

	@BeforeMethod
	@Parameters("browser")
	public void setup(@Optional("chrome") String browser) {
		DriverFactory.setUp(browser);
		driver = DriverFactory.getDriver();
		
	}

	@AfterMethod
	public void tearDown() {
		DriverFactory driverFactory = new DriverFactory();
		driverFactory.tearDown();
	}
	
}
