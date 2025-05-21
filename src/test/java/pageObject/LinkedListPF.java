package pageObject;




import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LinkedListPF {

    WebDriver driver;
    WebDriverWait wait;
    public LinkedListPF(WebDriver driver) {
    	this.driver=driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    @FindBy(linkText = "Sign in")
	WebElement signInLink;
    
	@FindBy(id = "id_username")
	WebElement inputUserName;
	
	@FindBy(id = "id_password")
	WebElement inputPassword;
	
	@FindBy(xpath = "//input[@type='submit']")
	WebElement loginBtn;
	
	
	@FindBy(xpath = "//div[contains(@class,'alert-primary')]")
    WebElement sucessMessage;

		
	public void clickLogin() {
		
		loginBtn.click();
	}
	
	public void clickSignIn() {
		 signInLink.click();
		}
	
	public String getMessage() {
		String loginMessage=sucessMessage.getText();
		return loginMessage;
	}
	
//	   @FindBy(xpath = "//a[@href='linked-list']") 
//        private WebElement getStartlinkedListLink;
	   
	   @FindBy(xpath = "//h5[text()='Linked List']/../a[text()='Get Started']")
		WebElement getStartlinkedListLink;
	    
    
	
	   @FindBy(linkText = "NumpyNinja")
		WebElement numpylink;
	    
	    @FindBy(xpath = "//a[@class='nav-link dropdown-toggle']")
		WebElement dropDown;

		
		@FindBy(xpath = "//div[contains(@class,'dropdown-menu')]//a")
		List <WebElement> options;
		
		@FindBy(xpath = "//div[@id='navbarCollapse']/div[2]/ul/a[2]")
		WebElement usernamelink;
		
		@FindBy(linkText = "Sign out")
	    WebElement signOutLink;
	  
	@FindBy(linkText = "Introduction")
    WebElement introductionLink;

	@FindBy(linkText = "Creating Linked LIst")
    WebElement clickCreatingLinkedLIst;
    
    @FindBy(linkText = "Types of Linked List")
     WebElement clickTypesOfLinkedList;
    
    @FindBy(linkText = "Implement Linked List in Python")
     WebElement clickImplementLinkedListInPython;

    @FindBy(linkText = "Traversal")
     WebElement clickTraversal;

    @FindBy(linkText = "Insertion")
     WebElement clickInsertionn;

    @FindBy(linkText = "Deletion")
     WebElement clickDeletion;
   
    @FindBy(linkText = "Try here>>>")
	WebElement tryHereBtn;

    @FindBy(xpath = "//button[text()='Run']")
	 WebElement runButton;

    @FindBy(id = "output")
	 WebElement outputArea;
    
    
    @FindBy(linkText = "Practice Questions")
    WebElement practiceQuestionsLink;
    
    @FindBy(xpath = "//div[@role='alert']")
	WebElement logOutMsg;
    
    
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
  
	public void clickIntroductionLink() {
		introductionLink.click();
  }

    
    public void clickLinkedlistTopic(String topic) {
        Map<String, WebElement> topicMap = new HashMap<>();
        topicMap.put("introduction", introductionLink);
        topicMap.put("creating linked list", clickCreatingLinkedLIst);
        topicMap.put("types of linked list", clickTypesOfLinkedList);
        topicMap.put("implement linked list in python", clickImplementLinkedListInPython);
        topicMap.put("traversal", clickTraversal);
        topicMap.put("insertion", clickInsertionn);
        topicMap.put("deletion", clickDeletion);

        WebElement element = topicMap.get(topic.trim().toLowerCase());
        if (element == null) {
            throw new IllegalArgumentException("Unknown topic: " + topic);
        }

        element.click();
    }

 
    private String getTitleFromTopic(String topic) {
        switch (topic.trim().toLowerCase()) {
            case "introduction":
                return "Introduction";
            case "creating linked list":
                return "Creating Linked LIst"; // Check exact title case
            case "types of linked list":
                return "Types of Linked List";
            case "implement linked list in python":
                return "Implement Linked List in Python";
            case "traversal":
                return "Traversal";
            case "insertion":
                return "Insertion";
            case "deletion":
                return "Deletion";
            default:
                throw new IllegalArgumentException("Unknown topic for title mapping: " + topic);
        }
    }

    public void verifyPageNavigation(String topic) {
        String expectedTitle = getTitleFromTopic(topic);
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, 
            "Page title mismatch");
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

    public void verifyPracticeQuestionsPage() {
        String url = driver.getCurrentUrl().toLowerCase();
        Assert.assertTrue(url.contains("practice"), "Not navigated to the Practice Questions page. URL: " + url);
    }
    
    public void clickLLgetStatBtn() {
    	getStartlinkedListLink.click();
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
    
    public String pageTitle() {
		String title=driver.getTitle();
		return title;
	}
    
    
public boolean runButtonDisplayed() {
		
		return runButton.isDisplayed();
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
  


