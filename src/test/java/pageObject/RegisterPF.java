package pageObject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ConfigReader;

public class RegisterPF {
	WebDriver driver;
	
	
	
	 public RegisterPF(WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	 @FindBy(linkText = "Get Started")
	 WebElement getStartedBtn;
	 @FindBy(linkText = "Register")
	 WebElement registerLink;
	 @FindBy(xpath = "//div[@class='navbar-nav']/div/a[text()='Data Structures']")
	 WebElement dropDown;
	 @FindBy(linkText = "NumpyNinja")
	 WebElement numpylink;
	 @FindBy(linkText = "Sign in")
	 WebElement signIn;
	 @FindBy(id = "id_username")
	 WebElement userNameField;
	 @FindBy(id = "id_password1")
	 WebElement pwd;
	 @FindBy(id = "id_password2")
	 WebElement confirmPwd;
	 @FindBy(xpath = "//input[@type='submit']")
	 WebElement registerBtn;
	 @FindBy(linkText = "Login")
	 WebElement loginLink;
	 @FindBy(xpath = "//div[contains(@class,'alert-primary')]")
	 WebElement errorMsg;

	 WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));

	 
	 public void gotoHomePage() {
		 driver.get(ConfigReader.getProperty("homePageUrl"));
	 }
	 public void clickOnGetStaredBtn() {
		 getStartedBtn.click();
	 }

	 public boolean headerElementsValidation() {
		 List<WebElement> headerElements = new ArrayList<>();
		 headerElements.add(registerLink);
		 headerElements.add(dropDown);
		 headerElements.add(numpylink);
		 headerElements.add(signIn);

		 for (WebElement element : headerElements) {
			 if (!element.isDisplayed()) {
				 return false;
			 }
		 }
		 return true;
	 }
	 public void clickOnRegisterLink() {
		 registerLink.click();
	 }


	 public String getTitleOfPage() {
		 return driver.getTitle();

	 }

	 public void clickOnregisterBtn() {

		 registerBtn.click();

	 }

	 public void clickOnLogin() {
		 loginLink.click();
	 }


	 public String alertMessageForFirstInvalidField() {
		 ArrayList<WebElement> inputFields = new ArrayList<>();
		 inputFields.add(userNameField);
		 inputFields.add(pwd);
		 inputFields.add(confirmPwd);

		 JavascriptExecutor js = (JavascriptExecutor) driver;

		 for (WebElement field : inputFields) {
			 String msg = (String) js.executeScript("return arguments[0].validationMessage;", field);
			 if (msg != null && !msg.isEmpty()) {
				 return msg;
			 }
		 }

		 return null; 
	 }




	 public void sendUserDetails(String username,String password,String confirmpwd) {
		 userNameField.sendKeys(username);
		 pwd.sendKeys(password);
		 confirmPwd.sendKeys(confirmpwd);

	 }
	 public String getErrorMsg() {
		 return errorMsg.getText();
	 }

	 public String getPageTitle() {
		 return driver.getTitle();
	 }





}





