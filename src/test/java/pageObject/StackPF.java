package pageObject;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import driverFactory.DriverFactory;
import utilities.LogHandler;

public class StackPF {
	WebDriver driver;
	WebDriverWait wait;

	// Constructor 

	public StackPF(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	@FindBy(xpath = "//button[text()='Get Started']")
	private WebElement mainGetStartedBtn;

	@FindBy(xpath = "//a[text()='Get Started' and @href='stack']")
	private WebElement gettingStartedStackBtn;

	@FindBy(xpath = "//a[@class='nav-link dropdown-toggle']")  // Dropdown button
	private WebElement dropdownMenu;

	@FindBy(xpath = "//a[text()='Stack']")  // Array option under the dropdown
	private WebElement StackOption;

	@FindBy(xpath = "//h4[contains(text(),'Stack')]")
	private WebElement StackPageHeader;

	@FindBy(css = "a[href='/tryEditor']")
	private WebElement tryhereLink;


	@FindBy(linkText = "Operations in Stack")
	private WebElement operationsInStackLink;

	@FindBy(linkText = "Implementation")
	private WebElement implementationLink;

	@FindBy(linkText = "Applications")
	private WebElement applicationsLink;

	@FindBy(linkText = "Practice Questions")
	private WebElement practiceQuestionsLink;

	@FindBy(linkText = "Sign out")
	private WebElement signOutLink;

	//  Try Editor Elements and Actions 

	@FindBy(xpath = "//form[@id='answer_form']/div/div/div/textarea")
	private WebElement codeEditor;

	@FindBy(xpath = "//button[text()='Run']")
	private WebElement runButton;

	@FindBy(id = "output")
	private WebElement outputArea;

	
	
	//Actions
	
	public void clickMainGetStartedButton() {
		mainGetStartedBtn.click();
	}

	public void clickDropdown() {
		dropdownMenu.click();
	}

	public void selectstackFromDropdown() {
		StackOption.click(); 
	}

	public void clickGettingStarted() {
		gettingStartedStackBtn.click();
	}

	public void scrollAndClickDataStructuresDropdown() {
		WebElement dataStructuresDropdown = driver.findElement(By.xpath("//a[text()='Data Structures']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(dataStructuresDropdown).perform();
		dataStructuresDropdown.click();
	}

	public void clickStackLink(String linkText) {
		WebDriver driver = DriverFactory.getDriver();
		try {
			WebElement link = driver.findElement(By.linkText(linkText));
			link.click();
		} catch (NoSuchElementException e) {
			LogHandler.info("Link not found: " + linkText);
		} catch (ElementNotInteractableException e) {
			LogHandler.info("Link not interactable: " + linkText);
		}
	}
	public boolean isStackPageDisplayed() {
		return StackPageHeader.isDisplayed(); 

	}

	public void scrollToTryHere() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tryhereLink);
			LogHandler.info("Scrolled to Try Here link successfully.");
		} catch (Exception e) {
			LogHandler.error("Failed to scroll to Try Here link.", e);
		}
	}

	public void signOut() {
		signOutLink.click();

	}

	public void clickOperationsInStack() {
		operationsInStackLink.click();
	}

	public void clickImplementation() {
		implementationLink.click();
	}

	public void clickApplications() {
		applicationsLink.click();
	}

	public void clickPracticeQuestions() {
		practiceQuestionsLink.click();
	}

	public void clickTryhere() {
		tryhereLink.click();
	}


	public void clickRunButton() {
		runButton.click();
	}

	public String getEditorOutput() {
		return outputArea.getText();
	}


	public boolean isRunButtonVisible() {
		try {
			return runButton.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;  // Return false if the element is not found
		}
	}


	public void navigateTostackPage() {
		gettingStartedStackBtn.click();
	}

	public void navigateToStackstryeditorPage() {
		gettingStartedStackBtn.click();
		operationsInStackLink.click();
		scrollToTryHere();   
		tryhereLink.click();
	}

	public void navigateTostackspracticequestion() {
		gettingStartedStackBtn.click();
		operationsInStackLink.click();
		practiceQuestionsLink.click();
	}

	public String getAlertTextAndAccept() {
		try {
			Alert alert = DriverFactory.getDriver().switchTo().alert();
			String text = alert.getText();
			alert.accept();
			return text;
		} catch (NoAlertPresentException e) {
			return null; 
		}
	}

	public void enterpythonCode(String code) {
		String cleanCode = code.replaceAll("^\"|\"$", "").replace("\\n", "\n");

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.editor.setValue(arguments[0]);", cleanCode);
	}

}

