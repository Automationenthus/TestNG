package pageObject;





import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueuePF {

	WebDriver driver;

	public QueuePF(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//button[text()='Get Started']")
	private WebElement mainGetStartedBtn;

	@FindBy(linkText = "Sign in")
	private	WebElement signIn;

	@FindBy(xpath = "//h5[text()='Queue']/../a[text()='Get Started']")
	private WebElement getStartQueue;

	@FindBy(linkText = "NumpyNinja")
	private WebElement numpylink;

	@FindBy(xpath = "//a[@class='nav-link dropdown-toggle']")
	private WebElement dropDown;


	@FindBy(xpath = "//div[contains(@class,'dropdown-menu')]//a")
	private List <WebElement> options;

	@FindBy(xpath = "//div[@id='navbarCollapse']/div[2]/ul/a[2]")
	private WebElement usernamelink;

	@FindBy(linkText = "Sign out")
	private WebElement signOutLink;

	@FindBy(linkText = "Implementation of Queue in Python")
	private WebElement implementationOfQueueInPython;

	@FindBy(linkText = "Implementation using collections.deque")
	private WebElement implementationUsingCollectionsDeque;

	@FindBy(linkText = "Implementation using array")
	private WebElement implementationUsingArray;

	@FindBy(linkText = "Queue Operations")
	private WebElement queueOperations;

	@FindBy(linkText = "Try here>>>")
	private WebElement tryHereBtn;

	@FindBy(xpath = "//button[text()='Run']")
	private WebElement runButton;

	@FindBy(id = "output") 
	private WebElement outputArea;

	@FindBy(linkText = "Practice Questions")
	private WebElement practiceQuestionsLink;

	@FindBy(xpath = "//div[@role='alert']")
	private WebElement logOutMsg;

	public void clickMainGetStartedButton() {
		mainGetStartedBtn.click();
	}
	
	public boolean isNumpyNinjaVisible() {
		return numpylink.isDisplayed();
	}

	public boolean isDropdownVisible() {
		return dropDown.isDisplayed();
	}

	public boolean isUsernameVisible() {
		return usernamelink.isDisplayed();
	}

	public boolean isSignOutVisible() {
		return signOutLink.isDisplayed();
	}


	public void clickSignIn() {
		signIn.click();
	}

	public void clickQueueTopic(String topic) {
		Map<String, WebElement> topicMap = new HashMap<>();
		topicMap.put("Implementation of Queue in Python", implementationOfQueueInPython);
		topicMap.put("Implementation using collections.deque", implementationUsingCollectionsDeque);
		topicMap.put("Implementation using array", implementationUsingArray);
		topicMap.put("Queue Operations", queueOperations);

		WebElement element = topicMap.get(topic);
		if (element == null) {
			throw new IllegalArgumentException("Unknown topic: " + topic);
		}

		element.click();
	}

	private String getTitleFromTopic(String topic) {
		switch (topic.toLowerCase()) {
		case "implementation of queue in python":
			return "Implementation of Queue in Python";
		case "implementation using collections.deque":
			return "Implementation using collections.deque";
		case "implementation using array":
			return "Implementation using array";
		case "queue operations":
			return "Queue Operations";
		default:
			throw new IllegalArgumentException("Unknown topic for title mapping: " + topic);
		}
	}


	public void verifyPageNavigation(String topic) {
		String expectedTitle = getTitleFromTopic(topic);
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle, 
				"Expected title: \"" + expectedTitle + "\" but got: \"" + actualTitle + "\"");
	}

	public void verifyTryEditortPage() {
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, "Assessment", 
				"Expected to be on Assessment page but was on: " + actualTitle);
	}


	public void clickRunButton() {
		runButton.click();
	}
	public String getOutputData() { 
		return outputArea.getText();
	}

	public void clickPracticeQuestionsLink() {
		practiceQuestionsLink.click();
	}


	public void clickImplementationOfQueueInPython() {
		implementationOfQueueInPython.click();
	}

	public void clickQueueGetBtn() {
		getStartQueue.click();
	}

	public void clickSignOut() {
		signOutLink.click();

	}
	public String logOutMessage() {
		return logOutMsg.getText();
	}

	public void clickTryHere() {
		tryHereBtn.click();
	}

	public boolean runButtonDisplayed() {

		return runButton.isDisplayed();
	}

	public String pageTitle() {
		String title=driver.getTitle();
		return title;
	}

	public void editor(String code1) {

		if (code1!= null && !code1.trim().isEmpty()) {

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.querySelector('.CodeMirror').CodeMirror.setValue(arguments[0]);", code1);

		}
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

	public boolean isAlertIsPresent() 
	{
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}	


	public String getAlertText() {
		Alert alert=driver.switchTo().alert();
		return alert.getText();
	}	


}
