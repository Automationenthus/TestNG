package pageObject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.ConfigReader;

public class DataStructureIntroPF {
	WebDriver driver;
	WebDriverWait wait;

	public DataStructureIntroPF(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	@FindBy(xpath = "//h5[text()='Data Structures-Introduction']/../a[text()='Get Started']")
	WebElement getStartedBtn; //getstaredbutton
	
	@FindBy(xpath = "//div[contains(@class,'alert-primary')]")
    WebElement sucessMessage;
	
	@FindBy(linkText = "NumpyNinja")
	WebElement numpylink;

	@FindBy(linkText = "Sign out")
	WebElement signOutLink;

	@FindBy(xpath = "//div[@role='alert']")
	WebElement logOutMsg;

	@FindBy(xpath = "//div[@id='navbarCollapse']/div[2]/ul/a[2]")
	WebElement usernamelink;	
	
	@FindBy(xpath = "//div[@class='navbar-nav']/div/a[text()='Data Structures']")
	WebElement dropDown;
	
	@FindBy(linkText = "Time Complexity")
	WebElement timeComplexityLink;
	
	@FindBy(linkText = "Practice Questions")
	WebElement practiceQuestLink;
	
	@FindBy(linkText = "Try here>>>")
	WebElement tryHereLink;
	
	@FindBy(xpath = "//form[@id='answer_form']/button")
	WebElement runBtn;
	
	@FindBy(xpath = "//div[@class='input']/div")
	WebElement codeEditor;
	
	@FindBy(id = "output")
	WebElement outputText;
	@FindBy(linkText = "Sign in")WebElement signIn;
	@FindBy(id = "id_username")WebElement Login_Username;
	@FindBy(id = "id_password")WebElement Login_Password;
	@FindBy(xpath = "//input[@value='Login']")WebElement Login_Button;
	
	public String getMessage() {
		String loginMessage=sucessMessage.getText();
		return loginMessage;
	}
	
		
	public String pageTitle() {
		String title=driver.getTitle();
		return title;
	}

	public void getStartbtnclick() {
		getStartedBtn.click();
	}
	
	 public boolean headerElementsValidation() {
		    List<WebElement> headerElements = new ArrayList<>();
		    headerElements.add(numpylink);
		    headerElements.add(dropDown);
		    headerElements.add(usernamelink);
		    headerElements.add(signOutLink);

		    for (WebElement element : headerElements) {
		        if (!element.isDisplayed()) {
		            return false;
		        }
		    }
		    return true;
		}
	

	public void timeComplexity() {
		timeComplexityLink.click();

	}
	public void clickOnPracticeQuest() {
		practiceQuestLink.click();
			}
	
	public String getPageURL() {
        return driver.getCurrentUrl();
    }

	public void clickOnTryHere() {
		tryHereLink.click();

     }
	
	public boolean runButtonDisplayed() {
		
		return runBtn.isDisplayed();
	}
	public void clickOnRunBtn() {
		
		try {
			WebElement run=wait.until(ExpectedConditions.elementToBeClickable(runBtn));
			run.click();
	}
		catch(InvalidElementStateException e) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", runBtn);

		}
			
		}
		
	public boolean isAlertIsPresent() 
	{
		try {
			    driver.switchTo().alert();
			        return true;
			    } catch (NoAlertPresentException e) {
			        return false;
			    }
	}		
	
	public void loginBackgroundForPages() {
		signIn.click();
		String uname=ConfigReader.getProperty("username");
		String pwd=ConfigReader.getProperty("password");
		Login_Username.sendKeys(uname);
		Login_Password.sendKeys(pwd);
		Login_Button.click();
	
		
	}
	
		
	public void clearEditor() {
		codeEditor.clear();
	}
	public void editor(String code1) {
						
		if (code1!= null && !code1.trim().isEmpty()) {
		   
			JavascriptExecutor js = (JavascriptExecutor) driver;
		    js.executeScript("document.querySelector('.CodeMirror').CodeMirror.setValue(arguments[0]);", code1);
					
		}
	}
	
	public String getAlertText() {
		Alert alert=driver.switchTo().alert();
		return alert.getText();
	}
		
	public String getOutputData() {
		String consoleOutput=outputText.getText();
		return consoleOutput;
	}
	
	public String navigateBack() {
		driver.navigate().back();
		driver.navigate().refresh();
		String title=driver.getTitle();
		return title;
	}
	
	public void signOut() {
		signOutLink.click();

	}
	public String logOutMessage() {
		return logOutMsg.getText();
	}
	public String laningPageValidation() {
		 
		String title=driver.getTitle();
		return title;
	}
	
	public boolean runButtonValidation() {
		if(runBtn.isEnabled()==true) {
			return true;
			
		}
		return false;
	}
	

}
