package stepDefinitions;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pageObjects.LoginPage;
import pageObjects.MSISNDLoginPage;

public class BaseClass {

	public WebDriver driver;
	public LoginPage loginPage;
	public MSISNDLoginPage 	msisdnLoginPage;
	public static Logger logger;
	public Properties configProp;
}
