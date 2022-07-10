package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MSISNDLoginPage {

	public WebDriver driver;
	
	public MSISNDLoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="subscriberid")
	@CacheLookup
	WebElement msisdnId;
	
	@FindBy(xpath="/html/body/div[3]/div[3]/div/button/span")
	@CacheLookup
	WebElement createSession;
	
	@FindBy(xpath="//*[@id='listOfCells']/tbody/tr")
	@CacheLookup
	List<WebElement> tableRows;
	
	@FindBy(xpath="//*[@id='listOfCells']/tbody/tr/td")
	@CacheLookup
	List<WebElement> tableColumns;
	
	public void setMSISDN(String msisdn) {
		msisdnId.clear();
		msisdnId.sendKeys(msisdn);
	}
	
	public void createSession() {
		createSession.click();
	}
		
	public int getRowSize() {
		return tableRows.size();
	}
	
	public int getColumnSize() {
		return tableColumns.size();
	}
	public void listSerialNumbers() {
		////*[@id='listOfCells']/tbody/tr[1]/td[2]
		System.out.println("List of Serial Numbers");
		for (int i = 1; i<= getRowSize() ; i++) {
			 WebElement row = driver.findElement(By.xpath("//*[@id='listOfCells']/tbody/tr["+i+"]/td[2]"));
			 System.out.println(row.getText()); 
		}
	}
}
