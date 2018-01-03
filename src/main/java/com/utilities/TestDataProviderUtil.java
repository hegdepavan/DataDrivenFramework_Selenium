package com.utilities;

import java.util.Hashtable;



public class TestDataProviderUtil {

	public static Object[][] getTestData(String testcaseName, Xls_Reader xl){
		
		String sheetName="Data";
		int testcaseStartRowNum=1;
		  while(!xl.getCellData(sheetName, 0, testcaseStartRowNum).equals(testcaseName)){
		  	testcaseStartRowNum++;
		  }
		 
		  int testDataStartRowNum=0;
		  testDataStartRowNum=testcaseStartRowNum+2;
		  int testDataColStartNum=0;
		  testDataColStartNum=testcaseStartRowNum+1;
		  // to find number of rows of data in a testcase 
		  int row=0;
		  while (!xl.getCellData(sheetName, 0, testDataStartRowNum+row).equals("")){
		  	row++;
		  	
		  }
		 
		  // to find num of colms in a testcase 
		  int col=0;
		  while(!xl.getCellData(sheetName, col, testDataColStartNum).equals("")){
		  	col++;
		  } 
		 

		  // to read data in a test case 
		  System.out.println("number of rows"+row);
		  Object[][] data= new Object[row][1];
		  int dataRow=0;
		  Hashtable<String, String> hashTable= null;
		  for(int rNum=testDataStartRowNum;rNum<testDataStartRowNum+row;rNum++ ){
			  hashTable= new  Hashtable<String, String>();
		  	for(int cNum=0;cNum<col;cNum++){
		  		String key=xl.getCellData(sheetName, cNum, testDataColStartNum);
				String val=xl.getCellData(sheetName, cNum, rNum);
				hashTable.put(key, val);
		  	    } 
		  	data[dataRow][0]=hashTable;
		  	
		  	 dataRow++;
		  }
		   return data;
		 
			}
	
	
}
