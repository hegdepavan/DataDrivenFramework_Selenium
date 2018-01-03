package com.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import com.page.xpaths.BSC_HomePage;
import com.page.xpaths.LoginPage;
import com.page.xpaths.ME_LandingPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class GenericActions {
	public WebDriver driver;
	 public Properties prop=null;
	 public ExtentReports rep=com.utilities.ExtentManager.getInstance();
		public ExtentTest test;
	
/************************Launch Browser and Open URL*****************************************************************************************/
	public void openUrl(String browserType){
		if(prop==null) 
		{prop= new Properties();
		FileInputStream file;
		try {
			file = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\config.properties");
			 prop.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	  
		}
		if( prop.getProperty(browserType).equals("Mozilla")){
			  System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\Drivers\\geckodriver.exe" );
				driver= new FirefoxDriver();
				//test.log(LogStatus.INFO, browserType+" browser launched successfully");
				
			} 
			else if(prop.getProperty(browserType).equals("IE")){
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\IEDriverServer.exe");
				driver= new InternetExplorerDriver();
				test.log(LogStatus.INFO, browserType+" browser launched successfully");
			} 
			else if(prop.getProperty(browserType).equals("Chrome")){
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\chromedriver.exe");
				driver= new ChromeDriver();
				test.log(LogStatus.INFO, browserType+" browser launched successfully");
			}
		driver.manage().deleteAllCookies();
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
	}
	
/*********To verify Title*********************************************************************************************************************************/	
public boolean verifyTitle(String expectedTitle){
	if(driver.getTitle().trim().equals(expectedTitle))
		return true;
	else 
		return false;
}

/********To check element is present on the page******************************************************************************************************/
public boolean isElementPresent(String elementXpath){
	WebDriverWait wait = new WebDriverWait(driver, 80);
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementXpath)));
	List<WebElement> elmt=null;
	elmt=driver.findElements(By.xpath(elementXpath));
	if (elmt.size()>0){
	return true; 
	}
	else 
		Assert.assertFalse(false, "Element NOT found "+elementXpath);
		return false;
	
}


/********Login to the application *******************************************************************************************************************/
public void doLogin(String userIdKey,String passwordKey){
	click(LoginPage.MY_ACCOUNT_Xpath);
	click(LoginPage.LOGIN_BTN_Xpath);
	type(LoginPage.EMAIL_ID_Xpath, prop.getProperty(userIdKey));
    type(LoginPage.PASSWORD_Xpath, prop.getProperty(passwordKey));
    click(LoginPage.SIGN_IN_BTN_Xpath);
    
}

/******** To enter value to textbox***************************************************************************************************************************/
public void type(String inputXpath,String value){
	if(isElementPresent(inputXpath))
		driver.findElement(By.xpath(inputXpath)).clear();
		driver.findElement(By.xpath(inputXpath)).sendKeys(value);
}

/******** To click on link or button***************************************************************************************************************************/
public void click(String xpath){
	if(isElementPresent(xpath))
		driver.findElement(By.xpath(xpath)).click();
	
}
/********* Access BSC application********************************************************************************************************/ 
public String accessBSCApp() throws InterruptedException{
	click(ME_LandingPage.CLIENT_Xpath);
    click(ME_LandingPage.ACCOUNT_Xpath);
    click(ME_LandingPage.CONTINUE_BTN_Xpath);
    click(ME_LandingPage.BSC_LINK_Xpath);
    //BSC is opened in different tab
  String parentWindow =driver.getWindowHandle();
  Set<String> allWindows =driver.getWindowHandles();
  for(String bscWindow:allWindows){
	  if(!parentWindow.equals(bscWindow))
		  driver.switchTo().window(bscWindow);
	  }
  if(isElementPresent(BSC_HomePage.RUN_CALC_BTN_Xpath))
	  return driver.getTitle();
  else
	  return null;
  
  }

/***********To set Dropdown Value***********************************************************************************************************************/
public void setDropdownValue(String dropdownXpath,String dropdownValue) throws InterruptedException{
	if(isElementPresent(dropdownXpath)){
		Select dp= new Select(driver.findElement(By.xpath(dropdownXpath)));
	    dp.selectByVisibleText(dropdownValue);
	}
	
}

/*********** To get web element ****************************************************************************************************************/
public WebElement getElement(String xpath){
	if(isElementPresent(xpath))
	{
		WebElement el= driver.findElement(By.xpath(xpath));
		return el;
	}
	return null;
	
}

/*********** To scroll till the element is visible****************************************************************************************/
public void scrollToElement(String xpath ){
	WebElement we=getElement(xpath);
	((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", we);
}

public void closeBrowser(){
	driver.quit();
}
}
