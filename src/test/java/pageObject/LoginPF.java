package pageObject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Map;

import org.openqa.selenium.ElementClickInterceptedException;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.support.FindBy;
	import org.openqa.selenium.support.PageFactory;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ExcelReader;

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
		@FindBy(xpath = "//input[@type='submit']")
		WebElement Login_Button;
		@FindBy(xpath = "//a[text()='Login ']")
		WebElement Login_link;
		@FindBy(xpath = "//div[contains(@class,'alert-primary')]")
		WebElement errorMessage;
		@FindBy(linkText = "Register!")
		WebElement register;

		public void click_homesignin() {
			signIn.click();

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

		public void clickLoginwithoutusername() {

			Login_Username.clear();
			Login_Password.sendKeys("dummyusername");
			try {
				Login_Button.click();
			}
			catch(ElementClickInterceptedException e) {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				wait.until(ExpectedConditions.elementToBeClickable(Login_Button)).click();
				
			}
			
		}
		 public String alertMessageForFirstInvalidField() {
			 ArrayList<WebElement> inputFields = new ArrayList<>();
			 inputFields.add(Login_Username);
			 inputFields.add(Login_Password);
			  JavascriptExecutor js = (JavascriptExecutor) driver;

			 for (WebElement field : inputFields) {
				 String msg = (String) js.executeScript("return arguments[0].validationMessage;", field);
				 if (msg != null && !msg.isEmpty()) {
					 return msg;
				 }
			 }

			 return null; 
		 }

		
		public void clickLoginwithoutpassword() {

			Login_Password.clear();
			Login_Username.sendKeys("dummypassword");
			Login_Button.click();
		}

	
		public void sendUserdetails(String uname,String pwd) {
			
			Login_Username.sendKeys(uname);
			 Login_Password.sendKeys(pwd);
			 Login_Button.click();
						
		}
		
		//login method to all pages
		
		public void login() {
			signIn.click();
			Map<String, String> dataMap = ExcelReader.getDataByScenario("Login", "valid");
			String uname=dataMap.get("Username");
			String pwd=dataMap.get("Password");
			Login_Username.sendKeys(uname);
			 Login_Password.sendKeys(pwd);
			 Login_Button.click();
					
		}
		
		public String getErrorMessage() {
			return errorMessage.getText();
		}
		
		public String pageTitle() {
			return driver.getTitle();
		}
		
		public void clickOnRegister() {
			register.click();
		}
			
		
	}


