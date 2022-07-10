package pageObjects;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Temp {
	public WebDriver driver;

	public Temp() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//Drivers//chromedriver.exe");
	    driver = new ChromeDriver();
	    driver.get("https://www.google.co.in/");
	}
	
	@Test
	public void Test1() {
		System.out.println("In Main");
		
		Temp t = new Temp();
	}
	
}
