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
import utilities.ConfigReader;

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


	public String captureScreenShot(String testName) {
		String timestamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date(0));
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
