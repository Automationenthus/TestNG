package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import driverFactory.DriverFactory;
import utilities.ConfigReader;

public class HomePF {

	WebDriver driver = DriverFactory.getDriver();
	ConfigReader configFileReader = DriverFactory.configReader();

	@FindBy(linkText = "Sign in")
	WebElement signIn;
	@FindBy(linkText = "NumpyNinja")
	WebElement numpylink;
	@FindBy(linkText = "Login")
	WebElement loginLink;
	@FindBy(xpath = "//div[@class='navbar-nav']/div/a[text()='Data Structures']")
	WebElement dropDown;
	@FindBy(linkText = "Register")
	WebElement registerLink;
	@FindBy(xpath = "//button[normalize-space()='Get Started']")
	WebElement getStartedHomeBtn;
	@FindBy(xpath = "//a[normalize-space()='Data Structures']")
	WebElement dataStructureDropDown;
	@FindBy(xpath = "//a[normalize-space()='Arrays']")
	WebElement arraysBtn;
	@FindBy(xpath = "//a[normalize-space()='Linked List']")
	WebElement linkedListBtn;
	@FindBy(xpath = "//a[normalize-space()='Stack']")
	WebElement stackBtn;
	@FindBy(xpath = "//a[normalize-space()='Queue']")
	WebElement queueBtn;
	@FindBy(xpath = "//a[normalize-space()='Tree']")
	WebElement treeBtn;
	@FindBy(xpath = "//a[normalize-space()='Graph']")
	WebElement graphBtn;
	@FindBy(xpath = "//div[@role='alert']")
	WebElement homeLogMessage;

	@FindBy(xpath = "//div[@role='alert']")
	WebElement logInMessage;

	@FindBy(xpath = "//a[@href='data-structures-introduction']")
	WebElement dataStructureGetStartedBtn;
	@FindBy(xpath = "//a[@href='array']")
	WebElement arrayGetStartedBtn;
	@FindBy(xpath = "//a[@href='linked-list']")
	WebElement linkedListGetStartedBtn;
	@FindBy(xpath = "//a[@href='stack']")
	WebElement stackGetStartedBtn;
	@FindBy(xpath = "//a[@href='queue']")
	WebElement queueGetStartedBtn;
	@FindBy(xpath = "//a[@href='tree']")
	WebElement treeGetStartedBtn;
	@FindBy(xpath = "//a[@href='graph']")
	WebElement graphGetStartedBtn;

	public HomePF() {
		PageFactory.initElements(driver, this);

	}

	public void launchurl() {

		if (driver != null) {

			driver.get(configFileReader.getProperties("url"));
		} else {

			throw new RuntimeException("Driver is not initialized in Home_pageFactory");
		}

	}

	public void getstartedbuttonclick() {
		getStartedHomeBtn.click();

	}
	
	public boolean isNumpyNinjaVisible() {
		return numpylink.isDisplayed();
	}

	public boolean isDropdownVisible() {
		return dropDown.isDisplayed();
	}
	public boolean isRegisterVisible() {
		return registerLink.isDisplayed();
	}
	public boolean isSignin() {
		return signIn.isDisplayed();
	}

}
