package hooks;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import driverFactory.DriverFactory;
import pageObject.HomePF;
import utilities.ConfigReader;

public class Hooks {

	public WebDriver driver;
	public HomePF hmpf;

	ConfigReader config = new ConfigReader();
	
	@BeforeMethod

	public void setUp() {

		driver = DriverFactory.initDriver();
		String url = ConfigReader.getProperty("url");
		if (url == null || url.isEmpty()) {
			throw new RuntimeException("URL not specified in config.properties");
		}
		driver.get(url);
		hmpf = new HomePF(driver);
		hmpf.getstartedbuttonclick();
		
	}

//	@Parameters("browser")
//	public void setup(@Optional("chrome") String browser) {
//		DriverFactory.setUp(browser);
//		driver = DriverFactory.getDriver();
//
//	}

	@AfterMethod
	public void tearDown() {
		DriverFactory driverFactory = new DriverFactory();
		driverFactory.quitDriver();

	}

	public String captureScreenShot(String testName) {
		String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date(0));
		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File sourceFile = takeScreenShot.getScreenshotAs(OutputType.FILE);
		String targetFilePath = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator
				+ testName + "-" + timestamp + ".png";

		File targetFile = new File(targetFilePath);
		try {

			FileUtils.copyFile(sourceFile, targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return targetFilePath;

	}

}
