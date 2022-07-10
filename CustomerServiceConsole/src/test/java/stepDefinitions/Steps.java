package stepDefinitions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import pageObjects.LoginPage;
import pageObjects.MSISNDLoginPage;
import utilities.Utils;


public class Steps extends BaseClass{
	
	@Before
	public void setUp() throws FileNotFoundException, IOException {
		
		
		//Load the config.properties file		
		//configProp = new Properties();		
		//configProp.load(new FileInputStream(new File("config.properties")));
		
		configProp = Utils.readPropFile("config.properties");
		
		logger = Logger.getLogger("CustomerServiceConsole");
		PropertyConfigurator.configure("log4j.properties");
		
		//Initially
		//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//Drivers//chromedriver.exe");
		//driver = new ChromeDriver();
		logger.info("********************Config Properties******************");
		Utils.getFileContents("config.properties");
		
		
		
		logger.info("********************Opening Browser********************");
		String browser = configProp.getProperty("browser");
		
		if(browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", configProp.getProperty("chromepath"));
			driver = new ChromeDriver();
		}else if(browser.equals("ie")) {
			System.setProperty("webdriver.ie.driver", configProp.getProperty("iepath"));
			driver = new InternetExplorerDriver();
		}else if(browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", configProp.getProperty("firefoxpath"));
			driver = new FirefoxDriver();
		}else if(browser.equals("edge")) {
			System.setProperty("webdriver.edge.driver", configProp.getProperty("msedgepath"));
			driver = new EdgeDriver();
		}
		
	    driver.manage().window().maximize();
	}
	
	@Given("User Launch Chrome browser")
	public void user_launch_chrome_browser() {
		
		//Adding Logger - log4j
		//Moving below content to setup()
		/*logger = Logger.getLogger("CustomerServiceConsole");
		PropertyConfigurator.configure("log4j.properties");
		
	    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//Drivers//chromedriver.exe");
	    driver = new ChromeDriver();
	    driver.manage().window().maximize();*/
	    loginPage = new LoginPage(driver);
	    msisdnLoginPage = new MSISNDLoginPage(driver);
	}

	@When("User Opens URL {string}")
	public void user_opens_url(String url) {
		//logger.info("***********Opening URL****************");
	    driver.get(url);
	    
	}

	@When("User enters Username as {string} and Password as {string}")
	public void user_enters_username_as_and_password_as(String uname, String pwd) {
	    loginPage.setUserName(uname);
	    loginPage.setPassword(pwd);
	    
	}

	@When("Click on SIGNIN")
	public void click_on_signin() {
	    loginPage.login();
	    
	}

	@Then("Page Title should be {string}")
	public void page_title_should_be(String pageTitle) {
		
		if(driver.getPageSource().contains("The username or password you entered is incorrect.")) {
			System.out.println("Not landed in expected page");
			driver.close();
			Assert.assertTrue(false);
		}else {
			String title = driver.getTitle();
			System.out.println("landed in expected page");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Assert.assertEquals(pageTitle, title);
		}	    
	}

	@Then("Close Browser")
	public void close_browser() {
		
		driver.quit();
	}
	
	//Adding MSISDN login methods
	
	@When("Subscriber enters MSISDN {string}")
	public void subscriber_enters_msisdn(String msisdn) {
		msisdnLoginPage.setMSISDN(msisdn);
	}
	@When("click on Create Session button")
	public void click_on_create_session_button() {
		msisdnLoginPage.createSession();
	}
	@Then("List all the Serial Numbers")
	public void list_all_the_serial_numbers() {
	    System.out.println("Successful Logged in to SerialNumber list: "+driver.getTitle());
	    msisdnLoginPage.listSerialNumbers();
	}

}
