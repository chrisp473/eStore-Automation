package coop.digital.eStores.testAutomation.helpers;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import coop.digital.eStores.testAutomation.actionsAndAssertions.CoreActions;
import coop.digital.eStores.testAutomation.constants.Constants;
import coop.digital.eStores.testAutomation.utilityAndFactories.TestCounter;
import coop.digital.eStores.testAutomation.utilityAndFactories.TestLogger;
import coop.digital.eStores.testAutomation.utilityAndFactories.XMLUtil;



public class TestHelper {
	
	protected static HashMap<String,String> testData;
	
	protected static String application;
	protected static String deviceName;
	protected static String deviceType;
	protected static String driverDirectory;
	protected static String driverType;
	protected static String externalScriptsDirectory;
	protected static String result;
	protected static String rootDirectory;
	protected static String testCaseResultsDirectory;
	protected static String testResultsDirectory;
	protected static String url;
	protected static String webBrowser;
	protected static String testName;
	public static boolean isTestLaunchSuccess = false;
	protected static Date start;
	protected static Date end;
	
	protected static int stepCount;
	
	public static String getDriverDirectory()
	{
		return driverDirectory;
	}
	
	public static String getSystemProperty(String propertyName)
	{
		return System.getProperty(propertyName);
	}
	
	public static void executeCmdFile(String scriptName) throws IOException
	{
		String scriptFilePath = String.format("%s\\%s.bat", externalScriptsDirectory, scriptName);
		String[] runCommand = { "cmd.exe", "/c", scriptFilePath };
		
		Runtime.getRuntime().exec(runCommand);
	}
	
	public static boolean  setSystemProperties(String rootDirectory, String application, String url, String deviceType, String deviceName, String driverType, String browser, String testCaseName, String Version_Path)
	{
		String resultsFolder = getResultsFolderName(application, testCaseName, browser,Version_Path);
		
		System.setProperty("rootDirectory", rootDirectory);
		System.setProperty("application", application);
		System.setProperty("url", url);
		System.setProperty("deviceType", deviceType);
		System.setProperty("deviceName", deviceName);
		System.setProperty("driverType", driverType);
		System.setProperty("browser", browser);
		System.setProperty("resultsFolder", resultsFolder);
		
		//If a different version of firefox is required, set the folder location to use. 
		if (!Version_Path.equals("")) {
			System.setProperty("VERSION_PATH", Version_Path);
		}
		return true;
	}
	
	private static String getResultsFolderName(String application, String testCaseName, String browser, String Version_Path)
	{
		String ResultsFolder = "";
		String CurrentDateTime = getDateAsString(Calendar.getInstance().getTime(), "ddMMyyyy-HHmmss");	
//		if (Version_Path.equals(""))
//		{
			ResultsFolder = String.format("%s_%s_%s", application, testCaseName, CurrentDateTime); 
//		}
//		else
//		{			
//			//If a firefox version has been requested - then identify the version number from folder location.
//			Pattern p = Pattern.compile("[0-9._]+$");
//			Matcher m = p.matcher(Version_Path);
//			String Version_Name = "";
////			if(m.find()){			
////				Version_Name = m.group(0);
////				ResultsFolder = String.format("%s_%s_%s_%s_%s", application, testCaseName, browser, Version_Name, CurrentDateTime);			
////			}else{
//				ResultsFolder = String.format("%s_%s_%s", application, testCaseName, CurrentDateTime);
////			}
//		}
		return ResultsFolder;
	}
	
	public static String getDateAsString(Date date, String pattern)
	{
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
	
	public static void handleExceptionNoRetry(String errorMessage) throws Exception
	{
		TestLogger.logException(formatErrorMessage(errorMessage));
		TestHelper.setTestResult("FAIL");
		//No screen shot as this is a error with no issue ont he page - e.g. test data error. 
		//saveScreenShot(true);
		fail(errorMessage);
						
	}
	
	public static String getTestCaseResultsDirectory()
	{
		return testCaseResultsDirectory;		
	}

	public static String getTestDataValue(String name)
	{
		return testData.get(name);
	}
	
	public static void setTestResult(String testResult)
	{
		result = testResult;
	}
	
	public static int getStepCount()
	{
		return stepCount;
	}
	
	public static String getTestCaseName(){
		return testName;
	}
	
	public static String getTestURL(){
		return url;
	}
	
	public static Date getTestExecutionDate(){
		return start;
	}
	
	// This function returns the script execution time in hh:MM:ss format
		public static String getScriptExecutionTimeIn_hhMMss(){
			int diff = (int) (end.getTime() - start.getTime());
			diff/=1000;
			int seconds =  (diff % 60);
			diff/=60;
			int minutes =  (diff % 60);
			diff/=60;
			int hours = diff;			
		
			String strHours = ("0"+String.valueOf(hours));
			strHours = strHours.substring(strHours.length()-2);
			
			String strMinutes = ("0"+String.valueOf(minutes));
			strMinutes = strMinutes.substring(strMinutes.length()-2);
			
			String strSeconds = ("0"+String.valueOf(seconds));
			strSeconds = strSeconds.substring(strSeconds.length()-2);
			
			return strHours+":"+strMinutes+":"+strSeconds;
		}
		
		public static String getLastRunStatus(){
			return result;
		}
		
		public static String formatErrorMessage(String errorMessage)
		{
			if (errorMessage.contains("Timed out after "))
			{
				errorMessage = errorMessage.substring(0,errorMessage.indexOf("seconds") + 7);
			}
			
			return errorMessage;
		}
		
		public static boolean setSystemProperties(String rootDirectory, String applicationName, String testCaseName) {
			boolean status = setSystemPropertyFromConfigurationFile(applicationName);
			if(status!=true ){
				return false;
			}
			System.setProperty("rootDirectory", rootDirectory);		
			SetTestHelperFields();
			return true;
		}
		
		public static boolean setSystemPropertyFromConfigurationFile(String applicationName){
			try{
				//open XML Document
				Document doc = XMLUtil.getXMLDoc(Constants.INITIAL_SET_UP_FILE_PREFIX+ applicationName +".xml");
				
				// Create XPathFactory object
				XPathFactory xpathFactory = XPathFactory.newInstance();
		
				// Create XPath object
				XPath xpath = xpathFactory.newXPath();
				XPathExpression expr=null; 
				String hostName = InetAddress.getLocalHost().getHostName();
				
				expr = xpath.compile("//Computer[@Name='"+ hostName +"']/Environment_To_Use");
				NodeList nodes = null; 
				nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
				
				
		
				if (nodes.getLength() ==0){
					expr = xpath.compile("//Computer[@Name='default']/Environment_To_Use");
					nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
					
				}            
				String environmentName= null;
				if(nodes.getLength()!=0){
					environmentName = nodes.item(0).getTextContent();
					System.out.println("ENVIRONMENT SELECTED FOR EXECUTION : '" + environmentName +"'");			
					// Reads Common configuration parameters
					expr = xpath.compile("./*");
					NodeList commonNodes = (NodeList) expr.evaluate(nodes.item(0).getParentNode(), XPathConstants.NODESET);
					iterateAndCreateSystemProperty(commonNodes);
					
					// reads Selenium specific parameter												
					expr = xpath.compile("//Environment[@Name='"+ environmentName +"']/SeleniumSystemProperties");
					nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		
					nodes = nodes.item(0).getChildNodes();	
					iterateAndCreateSystemProperty(nodes);

					// read QC parameter												
					expr = xpath.compile("//Environment[@Name='"+ environmentName +"']/QC_Details[@EnableQCUpdate='YES']");
					nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
					
					if(nodes.getLength()>0){
						nodes = nodes.item(0).getChildNodes();
					}
					
					if(System.getProperty("QC_TestSetID")==null && nodes.getLength()>0){
						iterateAndCreateSystemProperty(nodes);
					}else{
						expr = xpath.compile("//Environment[@Name='"+ environmentName +"']/QC_Details[@EnableQCUpdate='YES']/QC_UserName");
						nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
						iterateAndCreateSystemProperty(nodes);
						
						expr = xpath.compile("//Environment[@Name='"+ environmentName +"']/QC_Details[@EnableQCUpdate='YES']/QC_Password");
						nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
						iterateAndCreateSystemProperty(nodes);
					}

					
					//updateQCResult("sole_Basic_Check_Preamble_Page", "PASSED");
				} else{
					System.out.println("Please check that configuration file Y:\\InitialSetup-"+ applicationName +".xml values are set correctly for running system, no parameter found for current machine");
					return false;
				}
				return true;
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("An Exception is generated while reading the configuration file " + e.getMessage());
				return false;
			}
		}
		
		private static void iterateAndCreateSystemProperty(NodeList nodes){
			for(int i=0;i<nodes.getLength();i++){
				if(nodes.item(i).getNodeType()==1){	
					//System.out.println("Node name " + nodes.item(i).getNodeName().trim() + " Node value " + nodes.item(i).getTextContent().trim());
					System.setProperty(nodes.item(i).getNodeName().trim(), nodes.item(i).getTextContent().trim());
				}
			}
		}
			
		public static void checkSystemPropertyValueNotBlank(String systemPropertyName) throws Exception{
			if(System.getProperty(systemPropertyName)==null ||System.getProperty(systemPropertyName).trim().equals("")){
				throw new Exception("System property '"+ systemPropertyName +"' is not expected to be blank in XML configuration file");
			}
		}
		
		public static void SetTestHelperFields()
		{
			rootDirectory = System.getProperty("rootDirectory");
			rootDirectory = rootDirectory.replace("\\", "\\\\");		
			application = System.getProperty("application");
			url = System.getProperty("url");
			deviceType = System.getProperty("deviceType");
			deviceName = System.getProperty("deviceName");
			driverType = System.getProperty("driverType");
			webBrowser = System.getProperty("BrowserName");
		}
		
		public static void clearSystemProperty(String propertyName){
			System.clearProperty(propertyName.trim());
		}
		
		public static void initaliseTest(HashMap<String, String> data) throws Exception
		{
			setTestData(data);
			initaliseTest();	
		}
		
		public static void initaliseTest() throws Exception
		{
			//increment test counter
//			TestCounter.intcrementCount();		
			createTestDirectories();		
			result = "PASS";
			stepCount = 1;		
			start = new Date();		
			TestLogger.logTestStart();
		}
		
		public static void setupTest(String testCaseName, HashMap<String, String> data) throws Exception
		{	 testName =  testCaseName;	
//			 initaliseTest(data);
			 setTestData(data);
			 setupBroswer();		 
			 logTestSetupDetails();
		}
		
		private static void setTestData(HashMap<String, String> data)throws Exception{
			testData = data;
		}
		
		public static void createTestDirectories()
		{
			driverDirectory =  System.getProperty("rootDirectory")+ "/ProjectResources/drivers";
			externalScriptsDirectory = System.getProperty("rootDirectory")  + "/ProjectResources/external scripts";
			testResultsDirectory = System.getProperty("rootDirectory") + "/Results";
			testCaseResultsDirectory = getTestCaseResultsDirectory(testResultsDirectory);
			
			if (!new File(testResultsDirectory).exists())
			{
				createDirectory(testResultsDirectory);
			}

			createDirectory(testCaseResultsDirectory);
		}
		
		public static void logTestSetupDetails(){
			result = "PASS";
			stepCount = 0;
			TestLogger.logTestStep(TestHelper.getStepCount(), "BROWSER IN USE :- " + BrowserHelper.getBrowserName());
			TestLogger.logTestStep(TestHelper.getStepCount(), "BROWSER VERSION :- " + BrowserHelper.getBrowserVersion());
			TestLogger.logTestStep(TestHelper.getStepCount(), "URL IN USE :- " + url);
//			TestLogger.logTestStep(TestHelper.getStepCount(), "DEVICE IN USE :- " + deviceType);
			TestLogger.logTestStep(TestHelper.getStepCount(), "DEVICE IN USE:- " + System.getProperty("os.name")+"-" +System.getProperty("os.version"));//This may need adjusting for remote execution
			incrementStepCount();
		}
		
		public static void setupBroswer() throws Exception
		{
			BrowserHelper.initialiseDriver(deviceType, deviceName, driverType, webBrowser);	
			BrowserHelper.goToUrl(url);
			//Thread.sleep(30000);
		}
		
		protected static String getTestCaseResultsDirectory(String testResultsDirectory)
		{
			String FolderSuffix = "";
			if (TestCounter.multipleTestCases())
			{
				FolderSuffix = "-TC" + TestCounter.getCount();
			}
			String DirectoryPath = String.format("%s/%s%s", testResultsDirectory, System.getProperty("resultsFolder"), FolderSuffix);
			return DirectoryPath;
		}
			
			public static void createDirectory(String directory)
			{
				new File(directory).mkdir();
			}

			public static void incrementStepCount()
			{
				stepCount++;

			}
			public static String decodeBase64(String encodedString) {
				byte[] decodedBytes = Base64.decodeBase64(encodedString);
				return new String(decodedBytes);
			}
			
			public static void setResultFolderName(String testCaseName){
				testName = testCaseName;
				if(System.getProperty("resultsFolder")==null){
					System.setProperty("resultsFolder", getResultsFolderName(System.getProperty("application"), testCaseName, System.getProperty("BrowserName"), System.getProperty("BrowserVersionPath")));
				}
			}
			public static boolean checkForSystemProperties()
			{
				return (
//					System.getProperty("rootDirectory") != null &&
//					System.getProperty("application") != null &&
//					System.getProperty("deviceType") != null &&
//					System.getProperty("deviceName") != null &&
//					System.getProperty("driverType") != null &&
//					System.getProperty("browser") != null &&
					System.getProperty("url") != null
				);
				
			}
			
			// This method can be used as soft fail if exitTest is set to true
			public static void reportFail(String failMessage, boolean exitTest) throws Exception{
				setTestResult("FAIL");			
				TestLogger.logFail(failMessage);
				incrementStepCount();	
				if(exitTest){ 
					fail(failMessage);
				}
			}
			
			public static void teardownTest()
			{
				end = new Date();

				
				if(isTestLaunchSuccess){
					if (System.getProperty("TestRunner")== null ) {
						BrowserHelper.killDriver();		
					} 
					else { 
						if (!System.getProperty("TestRunner").equals("INTERNAL") && !System.getProperty("TestRunner").equals("VAPI")) {
							BrowserHelper.killDriver();
						}else{
							setupBrowserForNewTest();
						}
					}
					saveResult();	
				}	
				
				AppendStatusToDirectory();
						
				updateResultSummaryXML();
				TestLogger.clearHTMLLogger();
				clearSystemProperty("resultsFolder");		
				// ------ New Code ---------------		
			}
			
			private static void saveResult()
			{
				end = new Date();
				TestLogger.logTestEnd();
				
				String columnHeaders = "START,END,RESULT";
				String data = String.format("%s,%s,%s", getDateAsString(start, "dd/MM/yyyy HH:mm:ss"), getDateAsString(end, "dd/MM/yyyy HH:mm:ss"), result);
				
				for (Entry<String, String> dataItem : testData.entrySet())
				{
					columnHeaders += "," + dataItem.getKey();
					
					//Remove comma's to prevent erroneous Delimitations in output CSV file. 
					data += "," + dataItem.getValue().replace(",", "").replace("�", "").replace("\n","");
				}
				
				try
				{
					String filePath = String.format("%s/result.csv", testCaseResultsDirectory);
					
					FileWriter writer = new FileWriter(filePath);
				    writer.append(columnHeaders);
				    writer.append('\n');
				    writer.append(data);

				    writer.flush();
				    writer.close();
				    
				    createTestDataXMLFile(columnHeaders,data);
				}
				catch (IOException e)
				{
					// DO NOTHING...
				}
			}
			
			private static void setupBrowserForNewTest(){
				killExtraBrowserTab();
				try{
					BrowserHelper.getDriver().findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");	
					BrowserHelper.getDriver().findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.TAB);
					BrowserHelper.getDriver().findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"w");
					BrowserHelper.switchWindow();
				}catch(Exception e){
					// Do nothing
				}
			}
			
			public static void AppendStatusToDirectory()
			{
				TestLogger.completeHTMLReport();
				String NewFolderName = testCaseResultsDirectory + " - " + result;
				AmendResultsDirectoryName(NewFolderName);		
			}
			
			public static void updateResultSummaryXML() {

				try{
					//create reference to xml file and test if it is exists
					File f = new File(System.getProperty("rootDirectory")+ Constants.TESTRESULTS_LOCATION );
					if(!f.exists()) { 
					   
						//If file does not exist - then copy template to results folder. 
						FileUtils.copyFile(new File(System.getProperty("rootDirectory")+Constants.TESTRESULT_TEMPLATE_LOCATION), f);
					}
					
					//get reference to document and parse it as xml document
					Document doc = XMLUtil.getXMLDoc(System.getProperty("rootDirectory")+Constants.TESTRESULTS_LOCATION);
					
					//Inputs to new xml entries. 
					String testScriptName = testName;
					String resultFolderName = System.getProperty("resultsFolder")+ " - " + result;;
					String testStatus =result;
					
					//details about test
					String application = System.getProperty("application");
//					String browser = System.getProperty("BrowserName");
					String browser = BrowserHelper.getBrowserName();
//					String browserVersion = System.getProperty("BrowserVersionPath").replace("C:\\\\Program Files\\\\", ""); //assume files arelocated in program files
					String browserVersion = BrowserHelper.getBrowserVersion();
					String OSVersion = System.getProperty("os.name")+"-" +System.getProperty("os.version");  //this may need adjusting for remote exeuciton. 
					
					//build xpath
					String xpathstr = "/TestResults/TestScript[@name='"+testScriptName+"' and @application='"+application+"' "
							+ "and @browser='"+browser+"' and @OS='"+OSVersion+"'" ;
					//if browserVersionis not specified don't worry
					if (!browserVersion.equals("")) { xpathstr = xpathstr + "and @version='"+ browserVersion+"'";}
					xpathstr = xpathstr + "]";
					
					//locate existing node for this test if it exists 
					
					
					NodeList nl = XMLUtil.GetNodesThatMatchXPath(xpathstr, doc);
//					
					
					String executionTime = getScriptExecutionTimeIn_hhMMss();
					
					boolean isOverridePermitted = true; //Default set to TRUE 
					
					if(getSystemProperty("OverrideResultsSummary")!=null && getSystemProperty("OverrideResultsSummary").equalsIgnoreCase("N")){
						isOverridePermitted = false;
					}
					
					if(nl.getLength()>0 && isOverridePermitted){
						//if node exists overwrite with new details. 
						
						NodeList resultFolder = XMLUtil.GetNodesThatMatchXPath(xpathstr + "/ResultFolder", doc);
						resultFolder.item(0).setTextContent(resultFolderName);
						
						NodeList resultFile =XMLUtil.GetNodesThatMatchXPath(xpathstr + "/ResultFile", doc);
						resultFile.item(0).setTextContent(resultFolderName + "//log.xml");
						
						NodeList status = XMLUtil.GetNodesThatMatchXPath(xpathstr + "/Status", doc);
						status.item(0).setTextContent(testStatus);
						
//						NodeList scriptStatus = XMLUtil.GetNodesThatMatchXPath(xpathstr + "/TestStatus", doc);
//						scriptStatus.item(0).setTextContent("NA");
					
						NodeList exeTime = XMLUtil.GetNodesThatMatchXPath(xpathstr + "/ExecutionTime", doc); 
						exeTime.item(0).setTextContent(executionTime);
						
						NodeList startTime = XMLUtil.GetNodesThatMatchXPath(xpathstr + "/StartTime", doc); 
						startTime.item(0).setTextContent(start.toString());
						
						NodeList endTime = XMLUtil.GetNodesThatMatchXPath(xpathstr + "/EndTime", doc); 
						endTime.item(0).setTextContent(end.toString());
										
						NodeList runCount = XMLUtil.GetNodesThatMatchXPath(xpathstr + "/RunAttempt", doc);
						if(runCount.getLength()!=0){
							String preRunCount = runCount.item(0).getTextContent();				
							runCount.item(0).setTextContent(String.valueOf(Integer.parseInt(preRunCount)+1));
						}

					}else{
						//Otherwise create new node for this test. 
						Element testResultNode = doc.getDocumentElement();
						Element nodeEle = doc.createElement("TestScript");
						nodeEle.setAttribute("name", testScriptName);
						nodeEle.setAttribute("application", System.getProperty("application"));
						nodeEle.setAttribute("browser", browser);
						nodeEle.setAttribute("OS", OSVersion);
						if (!browserVersion.equals("")) {nodeEle.setAttribute("version", browserVersion);} 
						
						Element resulFolder = doc.createElement("ResultFolder");
						resulFolder.setTextContent(resultFolderName);
						nodeEle.appendChild(resulFolder);
						
						Element resulFile = doc.createElement("ResultFile");
						resulFile.setTextContent(resultFolderName + "/log.xml");
						nodeEle.appendChild(resulFile);
						
						Element status = doc.createElement("Status");
						status.setTextContent(testStatus);
						nodeEle.appendChild(status);
						
//						Element scriptStatus = doc.createElement("TestStatus");
//						scriptStatus.setTextContent("NA");
//						nodeEle.appendChild(scriptStatus);
						
						Element exeTime = doc.createElement("ExecutionTime");
						exeTime.setTextContent(executionTime);
						nodeEle.appendChild(exeTime);
						
						Element startTime = doc.createElement("StartTime");
						startTime.setTextContent(start.toString());
						nodeEle.appendChild(startTime);
						
						Element endTime = doc.createElement("EndTime");
						endTime.setTextContent(end.toString());
						nodeEle.appendChild(endTime);				
						
						Element runCount = doc.createElement("RunAttempt");
						runCount.setTextContent("1");
						nodeEle.appendChild(runCount);
						
						testResultNode.appendChild(nodeEle);				
					}
					
					//Save File. 
					String filePath = System.getProperty("rootDirectory")+Constants.TESTRESULTS_LOCATION;
					XMLUtil.UpdateXMLFile(doc, filePath);
				}
				catch(Exception e){
					System.out.println("An Exception was generated while writing to 'TestResults.xml' file" + e.getMessage());
				}		

			}
			
			// This function make sure that when the all extra browser are closed - Keep only the first browser session alive
			private static void killExtraBrowserTab() {
				Set<String> browsers = BrowserHelper.getDriver().getWindowHandles();
				if(browsers.size()>1){
				
					while(browsers.size()>1){
						try {
							BrowserHelper.closeCurrentTab();
							BrowserHelper.switchWindow();
						} catch (Exception e) {
							// Do Nothing
						}				
						browsers = BrowserHelper.getDriver().getWindowHandles();
					}
				}
			}
			
			private static void createTestDataXMLFile(String columnHeaders, String colvalues) {
				
//				System.out.println("Writing " + columnHeaders);
//				System.out.println("Writing " + colvalues);
				
				String[] arrColHeader = columnHeaders.split(",");
				String[] arrColValues = colvalues.split(",");
				
				try{
					//-------- Create a copy of the TestData file
					FileUtils.copyFile(new File(System.getProperty("rootDirectory")+Constants.XML_TESTDATA_TEMPLATE_LOCATION), new File(String.format("%s/TestData.xml", testCaseResultsDirectory)));
					
					//get reference to document and parse it as XML document
					Document doc = XMLUtil.getXMLDoc(System.getProperty("rootDirectory")+Constants.XML_TESTDATA_TEMPLATE_LOCATION);
					
					Element testResultNode = doc.getDocumentElement();
					for(int i=0;i<arrColValues.length;i++){		
						
						Element nodeEle = doc.createElement("col");
						nodeEle.setAttribute("name", arrColHeader[i]);
						nodeEle.setAttribute("value", arrColValues[i]);
						testResultNode.appendChild(nodeEle);
					}
					XMLUtil.UpdateXMLFile(doc, String.format("%s/TestData.xml", testCaseResultsDirectory));
				}catch(Exception e){
					System.out.println("An Exception was generated while writing test data to TestData.xml " + e.getMessage());
				}
			}
			
			private static void AmendResultsDirectoryName(String NewName)
			{
				File newFile = new  File(NewName); 
				boolean nameChanged = new File(testCaseResultsDirectory).renameTo(newFile);
				if(nameChanged){
					testCaseResultsDirectory = NewName;
				}
			}
			
			public static boolean doesTestDataContainKey(String KeyName)
			{
				return testData.containsKey(KeyName);
			}
			
			public static String getWebBrowserName()
			{
				return webBrowser;
			}
			
			public static void recordFailedTest(Throwable e) throws Exception
			{
				if (!result.equals("FAIL")){
					String errorMessage = "Test Failure Occured at unexecpted Location with Message: " + e.getMessage();
			
					TestHelper.setTestResult("FAIL");
					TestLogger.logFail(errorMessage);
				}
				try{
				CoreActions.saveScreenShot(true);
				}
				catch (Exception ex)
				{
					//do nothing
				}
				//fail("");
				TestHelper.reportFail("Test Completed With Fail", true);
			}
}
