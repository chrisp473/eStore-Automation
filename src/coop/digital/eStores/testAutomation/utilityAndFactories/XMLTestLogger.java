package coop.digital.eStores.testAutomation.utilityAndFactories;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import coop.digital.eStores.testAutomation.constants.Constants;
import coop.digital.eStores.testAutomation.helpers.TestHelper;
import coop.digital.eStores.testAutomation.utilityAndFactories.XMLUtil;

public class XMLTestLogger {

	private String resultFilePath;
	
	protected void setResultFilePath(String filePath) throws Exception{
		resultFilePath = filePath;
	}
	
	protected String getResultFilePath() throws Exception{
		return TestHelper.getTestCaseResultsDirectory()+ Constants.XML_LOG_FILE_LOCATION;
	}
	
	protected void createLogTemplate() throws Exception{
		FileUtils.copyFile(new File(System.getProperty("rootDirectory")+Constants.XML_lOG_TEMPLATE_LOCATION), new File(resultFilePath));
	}
	
	protected void addPassStep(String message){
		int stepCount = TestHelper.getStepCount();
		writeTestStepDetails(stepCount,"Pass",message);
		
	}
	
	protected void addWarningStep(String message){
		int stepCount = TestHelper.getStepCount();
		writeTestStepDetails(stepCount,"Warning",message);
	}
	
	protected void addFailStep(String message){
		int stepCount = TestHelper.getStepCount();
		writeTestStepDetails(stepCount,"Fail",message);
	}
	
	protected void addScreenShot(String screenShotFileName){
		int stepCount = TestHelper.getStepCount();
		writeTestStepDetails(stepCount,"SCREENSHOT",screenShotFileName);
	}
	
	private void writeTestStepDetails(int stepCount, String stepStatus, String stepDescription){
		try{

			Document doc = XMLUtil.getXMLDoc(getResultFilePath());
			
			
			//--------- Create Child Test Step
			NodeList nodes = doc.getElementsByTagName("Steps");
			
			//----------------- Add Step details
			Element p = doc.createElement("Step");			
			Element p1 = doc.createElement("StepNum"); 
			p1.setTextContent("Step : "+ stepCount);
			p.appendChild(p1);
			p1 = doc.createElement("StepStatus");
			p1.setTextContent(stepStatus);
			p.appendChild(p1);
			p1 = doc.createElement("Description");
//			stepDescription = stepDescription.replace("�", "&#39;");
//			stepDescription = stepDescription.replace("�", "&#163;");

//			stepDescription = StringEscapeUtils.escapeXml11(stepDescription);			
//			stepDescription = StringEscapeUtils.escapeHtml4(stepDescription);
//			stepDescription = stepDescription.replace("'", "&#39;");
			
						
			
			p1.setTextContent(stepDescription);
			p.appendChild(p1);
			nodes.item(0).appendChild(p);
			//----------------- Add Step details			
			
			XMLUtil.UpdateXMLFile(doc,getResultFilePath());
		
		}catch(Exception e){
			//TODO - Check if any thing to be added here
			System.out.println("Exception in writeTestStepDetails while wrting description " + stepDescription);
		}
	}
	

	
	protected void completeHTMLReport(){
		
		try{
			
			Document doc = XMLUtil.getXMLDoc(getResultFilePath());
			
			NodeList nodes = doc.getElementsByTagName("OtherDetails");
			
			//--------- Create Child Test Step						
			Element nodeEle = doc.createElement("TestStart");
			nodeEle.setAttribute("testName", TestHelper.getTestCaseName());
			nodeEle.setAttribute("executionStatus", TestHelper.getLastRunStatus());
			nodeEle.setAttribute("executionDate", TestHelper.getTestExecutionDate().toString());
			nodeEle.setAttribute("executionTime", TestHelper.getScriptExecutionTimeIn_hhMMss() + " [hh:MM:ss]");
			nodeEle.setAttribute("machineName", java.net.InetAddress.getLocalHost().getHostName());
			
			
			nodes.item(0).appendChild(nodeEle);
			

			XMLUtil.UpdateXMLFile(doc,getResultFilePath());
			
			
		}catch(Exception e){
			System.out.println("Exception in completeHTMLReport while writing final status");
		}
	}
}
