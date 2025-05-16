package driverFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ConfigReader;
import utilities.LogHandler;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class DriverFactory {
	private static ThreadLocal<WebDriver> tldriver = new ThreadLocal<>();

	String browser = ConfigReader.getProperties("browser");

	public static ConfigReader configFileReader = new ConfigReader();

	@BeforeClass
	@Parameters("browser")
	public static WebDriver setUp(@Optional("chrome") String browser) {
		if (browser.equalsIgnoreCase("firefox")) {
			LogHandler.info("Testing on firefox");
			tldriver.set(new FirefoxDriver());
		} else if (browser.equalsIgnoreCase("chrome")) {
			LogHandler.info("Testing on chrome");
			tldriver.set(new ChromeDriver());
		} else if (browser.equalsIgnoreCase("edge")) {
			LogHandler.info("Testing on Edge");
			tldriver.set(new EdgeDriver());
		} else {
			throw new RuntimeException("Unsupported browser: " + browser);
		}

		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return getDriver();
	}

	public static WebDriver getDriver() {
		return tldriver.get();
	}

	
		
		@AfterMethod
		public void tearDown() {
			if (getDriver() != null) {
				getDriver().quit();
				tldriver.remove();
			}
		}

		public static ConfigReader configReader() {
			return configFileReader;
		
	}
}
