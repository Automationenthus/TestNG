package pageObject;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.LogHandler;

public class TreePF {
	WebDriver driver;
	WebDriverWait wait;

	public TreePF(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	}
	@FindBy(xpath = "//button[text()='Get Started']")
	private WebElement mainGetStartedBtn;

	@FindBy(xpath = "//h5[text()='Tree']/../a[text()='Get Started']")
	private WebElement treeGetStart;
	@FindBy(linkText = "Try here>>>")
	private WebElement tryHereLink;
	@FindBy(linkText = "Practice Questions")
	private WebElement practiceQuestionsLink;
	@FindBy(xpath = "//form[@id='answer_form']/button")
	private WebElement runBtn;
	@FindBy(linkText = "Overview of Trees")
	private WebElement overViewOfTree;

	public void clickMainGetStartedButton() {
		mainGetStartedBtn.click();
	}
	public void clickOnGetStartBtn() {
		treeGetStart.click();
	}


	public boolean get_Current_Url(String text) {
		return driver.getCurrentUrl().contains(text);
	}

	public void clickOnOverviewOfTree() {
		overViewOfTree.click();
	}

	public void clickTopic(String topicName) {


		try {

			String xpath = "//a[contains(@class, 'list-group-item') and normalize-space(text())='" + topicName + "']";
			WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

			// Scroll the element into view
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", link);
			wait.until(ExpectedConditions.elementToBeClickable(link)).click();

		} catch (NoSuchElementException e) {
			LogHandler.info("Topic link not found: " + topicName);
			throw e; 

		} catch (Exception e) {
			LogHandler.error("❗ Unexpected error while clicking topic link: " + topicName, e);
			e.printStackTrace();
			throw e;
		}
	}



	public String  getPageTitle() {
		return driver.getTitle();

	}

	public void clickOnTreeLinks(String linkName) {
		try {			
			String xpath="//div[@id='content']/li/a[text()='"+linkName+"']";
			WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));                  	
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", link);
			wait.until(ExpectedConditions.elementToBeClickable(link)).click();
		}
		catch(NoSuchElementException e) {
			LogHandler.error("Unexpected error while clicking tree link: " + linkName, e);

		}
	}

	public void clickOnTryhere() {
		wait.until(ExpectedConditions.visibilityOf(tryHereLink));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", tryHereLink);
		tryHereLink.click();

	}

	public void clickOnPracticeQuestions() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", practiceQuestionsLink);
		wait.until(ExpectedConditions.elementToBeClickable(practiceQuestionsLink)).click();

	}

}
