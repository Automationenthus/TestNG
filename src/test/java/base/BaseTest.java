package base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import driverFactory.DriverFactory;
import utilities.ConfigReader;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BaseTest {
	public static WebDriver driver;

	@BeforeMethod
	@Parameters("browser")
	public void setUp(@Optional("chrome")String browser) {
		
		 driver= DriverFactory.initDriver(browser);
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
	
	
	public String captureScreenShot(String testName) {
		String timestamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takeScreenShot=(TakesScreenshot)driver;
		File sourceFile=takeScreenShot.getScreenshotAs(OutputType.FILE);
		String targetFilePath = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + testName + "-" + timestamp + ".png";

		File targetFile=new File(targetFilePath);
		try {
	        
	        FileUtils.copyFile(sourceFile, targetFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		return targetFilePath;
		
	}
	
}
