package com.testcases;

import java.util.Hashtable;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.base.GenericActions;

import com.page.xpaths.BSC_HomePage;
import com.page.xpaths.BSC_InputPage;
import com.page.xpaths.BSC_PreviewPage;
import com.page.xpaths.LoginPage;
import com.utilities.TestDataProviderUtil;
import com.utilities.Xls_Reader;

public class VerifyPreviewNetIncomeSum extends GenericActions{
public String testcaseName="VerifyPreviewNetIncomeSum";
	
	@BeforeTest
	public void accessBSC() throws InterruptedException{
		openUrl("browserType");
		doLogin("userEmail", "password");
		String BSC_title=accessBSCApp();
		System.out.println("BSC tittle= "+BSC_title);

	}
	
	@Test(dataProvider="getData")
	public void verifyPreviewNetIncomeSum(Hashtable<String, String> data) throws InterruptedException{
		
		//write logic to verify BSC title 
	
	
		setDropdownValue(BSC_HomePage.HOME_DROPDOWN_Xpath, data.get("Home"));
		setDropdownValue(BSC_HomePage.HOST_DROPDOWN_Xpath, data.get("Host"));
		click(BSC_HomePage.RUN_CALC_BTN_Xpath);
		System.out.println(isElementPresent(BSC_InputPage.INPUT_TITLE_Xpath));
		
		setDropdownValue(BSC_InputPage.COMP_CURR_Xpath, data.get("CompensationCurrency").trim());
		scrollToElement(BSC_InputPage.BASE_PAY_AMOUNT_Xpath);
		
		System.out.println("BAse"+data.get("AnnualBasePayAmount").trim());
		scrollToElement(BSC_InputPage.BASE_PAY_AMOUNT_Xpath);
		type(BSC_InputPage.BASE_PAY_AMOUNT_Xpath, data.get("AnnualBasePayAmount").trim());
		setDropdownValue(BSC_InputPage.BASE_PAY_TYPE_Xpath, data.get("BasePayIs").trim());
		//add atc rows 
		scrollToElement(BSC_InputPage.ATC_LABEL_Xpath+"First']");
		type(BSC_InputPage.ATC_LABEL_Xpath+"First']", data.get("AtcLabel1").trim());
		setDropdownValue(BSC_InputPage.ATC_TYPE_Xpath+"1']", data.get("AtcType1").trim());
		type(BSC_InputPage.ATC_VALUE_Xpath+"First']", data.get("AtcValue1").trim());
        //enter TDC rows
		type(BSC_InputPage.TDC_LABEL_Xpath+"First']", data.get("AtcLabel1"));
		setDropdownValue(BSC_InputPage.TDC_TYPE_Xpath+"1']", data.get("AtcType1"));
		type(BSC_InputPage.TDC_VALUE_Xpath+"First']", data.get("AtcValue1"));
     
      click(BSC_InputPage.INPUT_CONTINUE_BTN_Xpath);
      
      System.out.println("hhhhhhhhhhhhhhhhhhhhhhh"+getElement(BSC_PreviewPage.atcs).getText());
      
      //to verify preview summation 
      
      
	}
	
	@AfterTest
	public void afterTest(){
		//closeBrowser();
	}
	
	@DataProvider
	  public Object[][] getData(){
	     String testdataPath=System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\TestDataV1.xlsx";
		 Xls_Reader xl= new Xls_Reader(testdataPath);
	return TestDataProviderUtil.getTestData(testcaseName, xl);  
	}
	
}
