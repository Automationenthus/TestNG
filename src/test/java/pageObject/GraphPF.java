package pageObject;





import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ConfigReader;

public class GraphPF {

	
WebDriver driver;
   public GraphPF(WebDriver driver)
  { 
	this.driver=driver;
    PageFactory.initElements(driver, this);
  }
   @FindBy(linkText = "Sign in")
	WebElement signIn;
  
//  @FindBy(xpath = "//a[@href='graph']") 
//   WebElement getStartGraph;
  
  @FindBy(xpath = "//h5[text()='Graph']/../a[text()='Get Started']")
	WebElement getStartGraph;
  
  @FindBy(linkText = "NumpyNinja")
	WebElement numpylink;
  
  @FindBy(xpath = "//div[contains(@class,'alert-primary')]")
  WebElement sucessMessage;

  
  @FindBy(xpath = "//a[@class='nav-link dropdown-toggle']")
	WebElement dropDown;

	
	@FindBy(xpath = "//div[contains(@class,'dropdown-menu')]//a")
	List <WebElement> options;
	
	@FindBy(xpath = "//div[@id='navbarCollapse']/div[2]/ul/a[2]")
	WebElement usernamelink;
	
	@FindBy(linkText = "Sign out")
    WebElement signOutLink;
  
	@FindBy(linkText = "Graph") 
	  WebElement graphLink;
	  
	  @FindBy(linkText = "Graph Representations") 
	   WebElement graphRepresentationsLink;
	  
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
	    
	    @FindBy(xpath = "//div[@class='input']/div") 
		  WebElement codeEditor;
	    
	    
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
			
		
			public void getStartbtnclick() {
				getStartGraph.click();
			}
			
		public void clickGraph() { 
			  graphLink.click(); 
			  }
		  
		  public void clickTryHereLink() { 
			  tryHereBtn.click(); 
			  }
		  
		  public void clickGraphRepresentations() { 
			  graphRepresentationsLink.click(); 
			  }
		  
		  public void clickTryHere() {
		        tryHereBtn.click();
		    }
		
		public void clickRunButton() {
	        runButton.click();
	    }
		
		public boolean runButtonDisplayed() {
			
			return runButton.isDisplayed();
		}
		
	    public String getOutputData() { 
			  return outputArea.getText();
	     }
	    
	    public void clickPracticeQuestionsLink() {
	        practiceQuestionsLink.click();
	    }

	    
	    public void clickSignOut() {
			signOutLink.click();

		}
		
	    public String logOutMessage() {
			return logOutMsg.getText();
		}
	    
	   
	    public String pageTitle() {
			String title=driver.getTitle();
			return title;
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
	    
	    public String getMessage() {
			String loginMessage=sucessMessage.getText();
			return loginMessage;
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

