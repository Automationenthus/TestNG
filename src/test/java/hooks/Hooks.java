package hooks;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import driverFactory.DriverFactory;
import utilities.ConfigReader;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

import java.util.HashSet;
import java.util.Set;

public class Hooks {
	public static WebDriver driver;

	@BeforeMethod
	public void setUp() {
		
		driver = DriverFactory.initDriver();
		String url = ConfigReader.getProperty("url");
		if (url == null || url.isEmpty()) {
			throw new RuntimeException("URL not specified in config.properties");
		}
		driver.get(url);
	}
	
		

	@AfterMethod
	public void tearDown() {
		DriverFactory.quitDriver(); // Close browser
	}
	
}
