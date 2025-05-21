package pageObject;

import java.time.Duration;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPF {
	WebDriver driver;

	public LoginPF(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

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

	@FindBy(xpath = "//input[@name ='username']")
	WebElement Login_Username;
	@FindBy(xpath = "//input[@name ='password']")
	WebElement Login_Password;
	@FindBy(xpath = "//input[@value='Login']")
	WebElement Login_Button;
	@FindBy(xpath = "//a[text()='Login ']")
	WebElement Login_link;

	@FindBy(xpath = "//div[contains(text(),'You are logged in')]")
	WebElement LogedIn_message;
	@FindBy(xpath = "//div[contains(text(), 'not logged in')]") WebElement Not_LogedIn_message;
	@FindBy(xpath = "//div[contains(text(),'Logged out')]")
	WebElement LogedOut_message;
	@FindBy(xpath = "//div[contains(text(), 'Invalid Username and Password')]")
	WebElement Invalid_Crendentials_Validation_Message;
	@FindBy(linkText = "Sign out")
	WebElement Signout;

	public void click_homesignin() {
		signIn.click();

	}

	public String page_title() {

		return driver.getTitle();
	}

	public void click_loginlink() {

		Login_link.click();
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

	public boolean isnotLogimessagevisible() {
		
		return  Not_LogedIn_message.isDisplayed();
	}
	public boolean isyourlogedinmessagevisible() {
		return LogedIn_message.isDisplayed();
	}

	public void clickLoginwithoutusername() {

		Login_Username.clear();
		Login_Password.sendKeys("dummyusername");
		login_button();
	}

	public String isUsernameFieldEmpty() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String msg = (String) js.executeScript("return arguments[0].validationMessage;", Login_Username);
		return msg;
	}

	public void clickLoginwithoutpassword() {

		Login_Password.clear();
		Login_Username.sendKeys("dummypassword");
		Login_Button.click();
	}

	public String isPasswordFieldEmpty() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String Passworfield_msg = (String) js.executeScript("return arguments[0].validationMessage;", Login_Password);
		return Passworfield_msg;
	}

	public void login_user(String Username1) {

		Login_Username.clear();
		Login_Username.sendKeys(Username1);

	}

	public void login_passowrd(String Password1) {

		Login_Password.sendKeys(Password1);
	}

	public void login_button() {

		try {
			Login_Button.click();
		} catch (ElementClickInterceptedException e) {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(Login_Button));
			Login_Button.click();
		}
	}

	public boolean  isinvalidcredvalidatio_message_displayed() {
		return Invalid_Crendentials_Validation_Message.isDisplayed();

	}
	public String getvalidlogin_message() {
		return  LogedIn_message.getText();

	}

	public void logout_button() {
		Signout.click();

	}
}
