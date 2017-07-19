package coop.digital.eStores.testAutomation.superTest;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import coop.digital.eStores.testAutomation.frameworkConfiguration.FrameworkConfiguration;
import coop.digital.eStores.testAutomation.helpers.BrowserHelper;
import coop.digital.eStores.testAutomation.helpers.TestHelper;
import coop.digital.eStores.testAutomation.utilityAndFactories.ExcelUtils;


public class eStoresBaseTest {
	
	protected HashMap<String, String> testData;
	protected static String testCaseName;
	protected static String excelDataSheetName="TestCases";
	protected static ExcelUtils testDataWorkbook;
	protected static String filePath;
	private static Properties properties;
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		TestHelper.isTestLaunchSuccess = false;
		TestHelper.clearSystemProperty("resultsFolder");	
		TestHelper.setTestResult("FAIL");//ensure pass is not reported before set up complete

		testCaseName = this.getClass().getSimpleName();
		
		if (!TestHelper.checkForSystemProperties()) {
			setSystemProperties();
			setTestDataFilePath();
			TestHelper.setResultFolderName(testCaseName);
		}else{
			TestHelper.setResultFolderName(testCaseName);
		}
		
		/******************* Creates the TestResult Folder *******************/
		TestHelper.initaliseTest();
		TestHelper.setTestResult("FAIL");//ensure pass is not reported before set up complete
		
		/******************* Test Data*******************/		
		//get the reference to the relevant Data TAble (Excel work sheet) 
		if (testDataWorkbook == null)
			testDataWorkbook = new ExcelUtils(filePath);

//		//Excel Data sheet name specified in the properties file.
//		try{
//			excelDataSheetName = properties.getProperty(actualTestCaseName).trim();
//		}catch(NullPointerException e){
//			DigitalTestHelper.reportFail("No entry found for test script '"+ actualTestCaseName +"' in '"+ System.getProperty("rootDirectory") + "\"DataSheetName.properties file' ",true);
//		}
//		if (excelDataSheetName == null || excelDataSheetName.trim().equals("")) {
//			DigitalTestHelper.reportFail("Test Data sheet name not specified in '"+ actualTestCaseName +"' in '"+ System.getProperty("rootDirectory") + "\"DataSheetName.properties file'",true);
//		}
		
		testData = loadTestData( testCaseName ).iterator().next();
		
		try{
			/******************** Test Set up*********************/
			TestHelper.SetTestHelperFields();			
			TestHelper.setupTest(testCaseName, testData);

			TestHelper.isTestLaunchSuccess = true;
		}catch(Exception e){
			TestHelper.reportFail("An Exception was generated while trying to initialise test, error message : '"+e.getMessage()+"'", true);
		}
		TestHelper.setTestResult("PASS");//set to pass when set up complete
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown() throws Exception {
			TestHelper.teardownTest();

			// testDataWorkbook.saveWorkBook();
//			testDataWorkbook.CloseExcel();
		BrowserHelper.killDriver();
	}

	protected static void setSystemProperties() {

		String rootDirectory = null;

		if (System.getProperty("rootDirectory") != null
				&& !System.getProperty("rootDirectory").trim().equals("")) {
			rootDirectory = System.getProperty("rootDirectory");
		} else {
			rootDirectory = FrameworkConfiguration.rootDirectory;
		}
		String application = FrameworkConfiguration.applicationName;

		TestHelper.setSystemProperties(rootDirectory, application,
				testCaseName);
	}


	protected static Collection<HashMap<String, String>> loadTestData()
			throws Exception {
		testDataWorkbook.setWorksheet(excelDataSheetName);
		Collection<HashMap<String, String>> data = testDataWorkbook
				.loadTestData(testCaseName);
		return data;
	}

	protected static Collection<HashMap<String, String>> loadTestData(
			String testCaseName) throws Exception {
		testDataWorkbook.setWorksheet(excelDataSheetName);
		Collection<HashMap<String, String>> data = testDataWorkbook
				.loadTestData(testCaseName);
		return data;
	}

	protected static void setTestDataFilePath() throws Exception {
		if (System.getProperty("TestDataSheetName")== null ){
		filePath = String.format("%s"+File.separator+"Resources"+File.separator+"TestData"+File.separator+"%s%s.xlsx",
				System.getProperty("rootDirectory"),
				System.getProperty("Environment_To_Use"), "_eStoresTestData");
		} else {
			filePath = String.format("%s"+File.separator+"Resources"+File.separator+"TestData"+File.separator+"%s",
					System.getProperty("rootDirectory"),
					System.getProperty("TestDataSheetName"));
		}
	}

}
