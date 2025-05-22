package pageObject;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import driverFactory.DriverFactory;
import utilities.ConfigReader;

public class ArrayPF {
	WebDriver driver;
	ConfigReader configFileReader = DriverFactory.configReader();
	WebDriverWait wait;

	// Constructor

	public ArrayPF(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	@FindBy(xpath = "//a[text()='Get Started' and @href='array']")
	private WebElement gettingStartedArrayBtn;

	@FindBy(xpath = "//a[@href='arrays-in-python' and text()='Arrays in Python']")
	private WebElement arraysInPythonLink;

	@FindBy(linkText = "Arrays Using List") // @FindBy(xpath = "//a[text()='Arrays Using List']")
	private WebElement arraysUsingListLink;

	@FindBy(linkText = "Basic Operations in Lists") // @FindBy(xpath = "//a[text()='Basic Operations in Lists']")
	private WebElement basicOperationsInListLink;

	@FindBy(linkText = "Applications of Array") // @FindBy(xpath = "//a[text()='Applications of Array']")
	private WebElement applicationsOfArrayLink;

	@FindBy(linkText = "Practice Questions") // @FindBy(xpath = "//a[text()='Practice Questions']")
	private WebElement practiceQuestionsLink;

	@FindBy(linkText = "Sign out")
	WebElement signOutLink;

	// @FindBy(xpath = "//a[text()='Try here']")
	@FindBy(css = "a[href='/tryEditor']")
	private WebElement tryhereLink;

	@FindBy(xpath = "//form[@id='answer_form']/div/div/div/textarea")
	private WebElement codeEditor;

	@FindBy(xpath = "//button[text()='Run']")
	private WebElement runButton;

	@FindBy(id = "output")
	private WebElement outputArea;

	// Question Page

	@FindBy(xpath = "//*[contains(text(),'Search the array')]")
	private WebElement searchTheArrayLink;

	@FindBy(linkText = "Max Consecutive Ones")
	private WebElement maxConsecutiveOnesLink;

	@FindBy(linkText = "Find Numbers with Even Number of Digits")
	private WebElement evenNumberDigitsLink;

	@FindBy(linkText = "Squares of a Sorted Array")
	private WebElement squaresOfSortedArrayLink;

	// QUESTION EDITOR

	@FindBy(xpath = "//strong[text()='QUESTION']")
	private WebElement questionText;

	@FindBy(xpath = "//div[@class='input']//textarea")
	private WebElement codeEditor1;

	@FindBy(xpath = "//button[text()='Run']")
	private WebElement runButton1;

	@FindBy(xpath = "//input[@value='Submit']")
	private WebElement submitButton;

	@FindBy(xpath = "//pre[@id='output']")
	private WebElement outputConsole;

	public void clickGettingStarted() {
		gettingStartedArrayBtn.click();
	}

	public void clickArraysInPython() {
		arraysInPythonLink.click();
	}

	public void clickArraysUsingList() {
		arraysUsingListLink.click();
	}

	public void clickBasicOperationsInList() {
		basicOperationsInListLink.click();
	}

	public void clickApplicationsOfArray() {
		applicationsOfArrayLink.click();
	}

	public void clickPracticeQuestions() {
		practiceQuestionsLink.click();
	}

	public void clickTryHereButton() {
		tryhereLink.click();
	}

	public void scrollToTryHere() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tryhereLink);
			System.out.println("Scrolled to Try Here link successfully.");
		} catch (Exception e) {
			System.out.println("Failed to scroll to Try Here link: " + e.getMessage());
		}
	}

	public void clickArraysLink(String linkText) {
		WebDriver driver = DriverFactory.getDriver();
		try {
			WebElement link = driver.findElement(By.linkText(linkText));
			link.click();
		} catch (NoSuchElementException e) {
			System.out.println("Link not found: " + linkText);
		} catch (ElementNotInteractableException e) {
			System.out.println("Link not interactable: " + linkText);
		}
	}

	public void signOut() {
		signOutLink.click();

	}
	// TryEditor //

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
			return false; // Return false if the element is not found
		}
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

	// Navigate methods inside ArraysPage //

	public void navigateToArraysPage() {
		gettingStartedArrayBtn.click();
	}

	public void navigateToTryEditorPage() {
		gettingStartedArrayBtn.click();
		arraysInPythonLink.click();
		scrollToTryHere();
		tryhereLink.click();
	}

	public void navigateToArraysPracticeQuestions() {

		gettingStartedArrayBtn.click();
		arraysInPythonLink.click();
		practiceQuestionsLink.click();
	}

	public void navigateToSearchthearray() {
		gettingStartedArrayBtn.click();
		arraysInPythonLink.click();
		practiceQuestionsLink.click();
		searchTheArrayLink.click();
	}

	public void navigateToMaxConsecutiveOnes() {
		gettingStartedArrayBtn.click();
		arraysInPythonLink.click();
		practiceQuestionsLink.click();
		maxConsecutiveOnesLink.click();
	}

	public void navigateToFindnumberswithevennumberofdigits() {
		gettingStartedArrayBtn.click();
		arraysInPythonLink.click();
		practiceQuestionsLink.click();
		evenNumberDigitsLink.click();
	}

	public void navigateToSquaresofasortedArray() {
		gettingStartedArrayBtn.click();
		arraysInPythonLink.click();
		practiceQuestionsLink.click();
		squaresOfSortedArrayLink.click();
	}
	// PracticeQuestion

	public void clickPracticeQuestion(String linkText) {
		WebDriver driver = DriverFactory.getDriver();
		try {
			WebElement link = driver.findElement(By.linkText(linkText));
			link.click();
		} catch (NoSuchElementException e) {
			System.out.println("Link not found: " + linkText);
		} catch (ElementNotInteractableException e) {
			System.out.println("Link not interactable: " + linkText);
		}
	}

	public boolean isSearchTheArrayLinkVisible() {
		return isElementVisible(searchTheArrayLink);
	}

	public boolean isElementVisible(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			return false;
		}
	}

	public boolean isQuestionDisplayed() {
		try {
			return questionText.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean isRunButton1Visible() {
		try {
			return runButton1.isDisplayed();
		} catch (NoSuchElementException e) {
			return false; // Return false if the element is not found
		}
	}

	public boolean isSubmitButtonVisible() {
		try {
			return submitButton.isDisplayed();
		} catch (NoSuchElementException e) {
			return false; // Return false if the element is not found
		}
	}

	public void clickRun() {
		runButton1.click();
	}

	public void clickSubmit() {
		submitButton.click();
	}

	public String getConsoleOutput() {
		try {
			WebElement outputElement = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.visibilityOf(outputConsole));
			return outputElement.getText().trim();
		} catch (Exception e) {
			System.out.println("Console output not found or empty.");
			return "";
		}
	}

}