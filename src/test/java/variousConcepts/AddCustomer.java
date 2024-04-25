package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddCustomer {
	
	WebDriver driver;
	String browser;
	String url;
	String userName = "demo@codefios.com";
	String password = "abc123";

	// Test or Mock Data:

	String dashboardHeaderText = "Dashboard";
	String userNameAlertMsg = "Please enter your user name.";
	String passwordAlertMsg = "Please enter your password";
	String newCustomerHeaderText = "New Customer";
	String fullName = "Selenium";
	String company = "Techfios";
	String email = "abc23@techfios.com";
	String phoneNo = "1234567890";
	
	


	By USER_NAME_FIELD = By.xpath("//*[@id=\"user_name\"]");
	By PASSWORD_FIELD = By.xpath("//*[@id=\"password\"]");
	By SIGNIN_BUTTON_FIELD = By.xpath("//*[@id=\"login_submit\"]");
	By DASHBOARD_HEADER_FIELD = By.xpath("/html/body/div[1]/section/div/div[2]/div/div/header/div/strong");
	By CUSTOMER_MENU_FIELD = By.xpath("/html/body/div[1]/aside[1]/div/nav/ul[2]/li[2]/a/span");
	By ADD_CUSTOMER_MENU_FIELD = By.xpath("//*[@id=\"customers\"]/li[2]/a/span");
	By ADD_CUSTOMER_HEADER_FIELD = By.xpath("/html/body/div[1]/section/div/div[2]/div/div[1]/div[1]/div/div/header/div/strong");
	By FULL_NAME_FIELD = By.xpath("//*[@id=\"general_compnay\"]/div[1]/div/input");
	By COMPANY_DROPDOWN_FIELD = By.xpath("//select[@name='company_name']");
	By EMAIL_FIELD = By.xpath("//*[@id=\"general_compnay\"]/div[3]/div/input");
	By PHONE_FIELD = By.xpath("//*[@id=\"phone\"]");
	By Country_DROPDOWN_FIELD = By.xpath("//*[@id=\"general_compnay\"]/div[8]/div[1]/select");

		@BeforeClass
		public void readConfig() {
		
			//InputStream//BufferReader//FileReader//Scanner
			
			try {
				
				InputStream input = new FileInputStream("src\\test\\java\\config\\config.properties");
			
				Properties prop = new Properties();
				prop.load(input);
				browser = prop.getProperty("browser");
				System.out.println("Browser used: " + browser);
				url = prop.getProperty("url");
				
			} catch (IOException e) {
				e.printStackTrace();
				
			}
		}
		
		@BeforeMethod
		public void init() {

//			==================== Running multiple Browsers (Edge/Chrome) =============================
			
			if (browser.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
				driver = new ChromeDriver();
				
			}else if(browser.equalsIgnoreCase("Edge")) {
				System.setProperty("webdriver.edge.driver", "driver\\msedgedriver.exe");
				driver = new EdgeDriver();
			}else {
				System.out.println("Please insert a valid browser..");
			}
			
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.get(url);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		}
		
		@Test(priority=2)
		public void loginTest() {

			driver.findElement(USER_NAME_FIELD).sendKeys(userName);
			driver.findElement(PASSWORD_FIELD).sendKeys(password);
			driver.findElement(SIGNIN_BUTTON_FIELD).click();
			Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), "Dashboard", "Dashboard page not found!");
		}
		
//		@Test(priority=1)
		public void validateAlertMsgs() {
			
			driver.findElement(SIGNIN_BUTTON_FIELD).click();
			String actualUserNameAlertMsg = driver.switchTo().alert().getText();
			Assert.assertEquals(actualUserNameAlertMsg, userNameAlertMsg, "Alert doesn't match!!");
		
			driver.findElement(USER_NAME_FIELD).sendKeys(userName);
			driver.findElement(SIGNIN_BUTTON_FIELD).click();
			String actualPasswordAlertMsg = driver.switchTo().alert().getText();
			Assert.assertEquals(actualPasswordAlertMsg, passwordAlertMsg, "Alert doesn't match!!");
			
		}
		
		@Test
		public void addCustomer() {
			
			loginTest();
			driver.findElement(CUSTOMER_MENU_FIELD).click();
			driver.findElement(ADD_CUSTOMER_MENU_FIELD).click();
			
//			Assert.assertEquals(ADD_CUSTOMER_HEADER_FIELD, newCustomerHeaderText, "New Customer page not found!");
			
			//Converting By Type Element (ADD_CUSTOMER_HEADER_FIELD) to String text. by using .getText();
			Assert.assertEquals(driver.findElement(ADD_CUSTOMER_HEADER_FIELD).getText(), newCustomerHeaderText, "New Customer page not found!");
			driver.findElement(FULL_NAME_FIELD).sendKeys(fullName);

			Select sel = new Select(driver.findElement(COMPANY_DROPDOWN_FIELD));
			sel.selectByVisibleText(company);
			
			driver.findElement(EMAIL_FIELD).sendKeys(email);
			driver.findElement(PHONE_FIELD).sendKeys(phoneNo);
		}
		
		@AfterMethod
		public void tearDown() {
			driver.close();
			driver.quit();

		}
}
