

import static org.junit.Assert.fail;
import static com.centurylink.simon.glue.PagePramDefinitions.*;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * This is a Page Object, simply put it models an Application Page within the
 * test code. This reduces the amount of duplicated code and means that if the
 * page changes, the fix need only be applied in one place, here.
 * 
 * @see https://code.google.com/p/selenium/wiki/PageObjects
 * @see https://code.google.com/p/selenium/wiki/LoadableComponent
 * @author lsteele
 *
 */





public class VerifyPage extends
SimonBasePage<VerifyPage> {
	
	static final long PAGE_WAIT_SEC = 20; 


	@FindBy(xpath = "//div[contains(text(),'Account Information')]")    
	@CacheLookup 
	private WebElement accountHeader;
	
	@FindBy(xpath = "//*[@id='conversationRemarks']")
	@CacheLookup
	private WebElement converRemarksTexArea;
	
	@FindBy(xpath = "//*[@id='research-button']")
	@CacheLookup
	private WebElement researchButton;
	
	VerifyPage(WebDriver driver){
		super(driver);
		this.loadFrame(MAIN_FRM);
		// This call sets the WebElement fields.
		PageFactory.initElements(driver, this);
	}
	
	public VerifyPage(WebDriver driver, String url) {
		super(driver);
		driver.get(url);
		this.loadFrame(MAIN_FRM);
		// This call sets the WebElement fields.
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void load() {
		ExistingCustomerSearchPage ecsPage = new ExistingCustomerSearchPage(driver, this.pagePrams);
		ecsPage.sendtoVerifyPage();;
		this.loadFrame(MAIN_FRM);
		
	}

	@Override
	protected void isLoaded() throws Error {
		try{
			WebDriverWait dWait = new WebDriverWait(driver, PAGE_WAIT_SEC);
			WebElement accountHeader = dWait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//div[contains(text(),'Account Information')]"))));
		}catch (Exception e){
			fail("Not on the Existing Customer Search Page! " + e.getMessage());
		}
		
	}
	
	public void enterTextIntoConverRemarksTexArea(String text){
		bot.type(this.converRemarksTexArea, text);
	}
	
	public CustomerDashBoardPage pressResearchButton(){
		bot.click(this.researchButton);
		return new CustomerDashBoardPage(driver);
	}

	
}
