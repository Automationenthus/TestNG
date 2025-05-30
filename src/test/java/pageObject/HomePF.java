package pageObject;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ConfigReader;
import utilities.LogHandler;

public class HomePF {
	WebDriver driver; 


	public HomePF(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[text()='Get Started']")
	private WebElement mainGetStartedBtn;

	@FindBy(linkText = "Sign in")
	private WebElement signInLink;

	@FindBy(linkText = "Register")
	private  WebElement registerLink;

	@FindBy(xpath = "//div[@class='alert alert-primary']")
	private WebElement warningMsg;

	@FindBy(xpath = "//h5[text()='Data Structures-Introduction']/../a[text()='Get Started']")
	private WebElement getStartedDataStructures;

	@FindBy(xpath = "//h5[text()='Array']/../a[text()='Get Started']")
	private WebElement getStartedArrays;

	@FindBy(xpath = "//h5[text()='Linked List']/../a[text()='Get Started']")
	private WebElement getStartedLinkedList;

	@FindBy(xpath = "//h5[text()='Stack']/../a[text()='Get Started']")
	private WebElement getStartedStack;

	@FindBy(xpath = "//h5[text()='Queue']/../a[text()='Get Started']")
	private WebElement getStartedQueue;

	@FindBy(xpath = "//h5[text()='Tree']/../a[text()='Get Started']")
	private WebElement getStartedTree;

	@FindBy(xpath = "//h5[text()='Graph']/../a[text()='Get Started']")
	private WebElement getStartedGraph;


	@FindBy(xpath = "//a[@class='nav-link dropdown-toggle']")
	private WebElement dropdownMenu;

	//    @FindBy(xpath = "//div[@class='dropdown-menu show']/a")
	//    List<WebElement> dropdownOptions;

	@FindBy(xpath = "//div[contains(@class,'dropdown-menu')]//a")
	private List<WebElement> dropdownOptions;

	@FindBy(linkText = "Sign out")
	private WebElement signOutLink;

	@FindBy(xpath = "//div[@role='alert']")
	private WebElement logOutMsg;

	public void clickGetStartedButton() {
		mainGetStartedBtn.click();
	}

	public void clickGetStarted(String option) {
		try {
			Map<String, WebElement> getStartedMap = Map.of(
					"Data Structures", getStartedDataStructures,
					"Arrays", getStartedArrays,
					"Linked List", getStartedLinkedList,
					"Stack", getStartedStack,
					"Queue", getStartedQueue,
					"Tree", getStartedTree,
					"Graph", getStartedGraph
					);

			WebElement button = getStartedMap.get(option.trim());
			if (button == null) {
				throw new IllegalArgumentException("Unknown module: " + option);
			}


			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
			LogHandler.info("Clicked 'Get Started' button for: " + option);

		} catch (Exception e) {
			LogHandler.error("Error while clicking 'Get Started' for: " + option, e);
			e.printStackTrace();
		}

		String Title=pageTitle();
		LogHandler.info("Title of the current page is : " + Title);
	}   



	public void clickDropdownOption(String option) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			// Click the dropdown menu
			wait.until(ExpectedConditions.elementToBeClickable(dropdownMenu)).click();

			// Re-fetch dropdown items after opening to avoid staleness
			List<WebElement> freshOptions = wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='dropdown-menu show']//a")));

			for (WebElement item : freshOptions) {
				if (item.getText().trim().equalsIgnoreCase(option)) {
					wait.until(ExpectedConditions.elementToBeClickable(item)).click();
					return;
				}
			}
			throw new NoSuchElementException("Dropdown option not found: " + option);
		} catch (StaleElementReferenceException e) {
			// Retry once in case of DOM refresh
			clickDropdownOption(option);
		}
	}

	public String getWarningMessage() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement warning = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='alert alert-primary']")));
		return warning.getText().trim();
	}


	public boolean isOnCorrectModulePage(String option) {
		// Map option names to actual page titles
		Map<String, String> titleMap = new HashMap<>();
		titleMap.put("Arrays", "Array");
		titleMap.put("Linked List", "Linked List");
		titleMap.put("Stack", "Stack");
		titleMap.put("Queue", "Queue");
		titleMap.put("Tree", "Tree");
		titleMap.put("Graph", "Graph");

		String expectedTitle = titleMap.getOrDefault(option, option); // fallback if key not found
		String actualTitle = driver.getTitle().trim().toLowerCase();
		return actualTitle.contains(expectedTitle.toLowerCase());
	}

	public void clickSignIn() {
		signInLink.click();

	}

	public void clickRegister() {
		registerLink.click();

	}

	public String pageTitle() {
		String title=driver.getTitle();
		return title;
	}

	public void clickSignOut() {
		signOutLink.click();

	}
	public String logOutMessage() {
		return logOutMsg.getText();
	}

}

